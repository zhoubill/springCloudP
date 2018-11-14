package com.yling.opencv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.highgui.Highgui;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yling.opencv.dao.ImageDao;
import com.yling.opencv.pojo.Image;
import com.yling.opencv.pojo.ImageVO;
import com.yling.opencv.pojo.ReturnMsgResult;
import com.yling.opencv.service.ImageCompareI;
import com.yling.opencv.utils.ConfigUtil;
import com.yling.opencv.utils.EhCacheUtils;
import com.yling.opencv.utils.ListUtils;
import com.yling.opencv.utils.OpencvUtils;

@Service
public class ImageCompareImpl implements ImageCompareI {

	@Autowired
	private ImageDao imageDao;

	public ReturnMsgResult getMaxSimilartilyImage(String destImagePath) {
		ReturnMsgResult returnMsgResult = new ReturnMsgResult();
		Map map = new HashMap();
		String code = "000";
		try {
			List<ImageVO> sortImageResult = new ArrayList<ImageVO>();
			Image destImage = new Image();
			Mat destImageMat = Highgui.imread(destImagePath, Highgui.CV_LOAD_IMAGE_COLOR);
			// destImage.setMatJson(MatUtils.matToJson(destImageMat));
			destImage.setMat(destImageMat);
			MatOfKeyPoint destPoint = OpencvUtils.getImageDescitor(destImageMat);
			destImage.setMatOfKeyPoint(destPoint);

			String orignalImageDirPath = ConfigUtil.get("opencv.properties", "origanlImageDirPath");
			List<Image> orignalImageList = new ArrayList<Image>();
			int taskSize = Integer.parseInt(ConfigUtil.get("opencv.properties", "numThreads"));
			int imageSize = Integer.parseInt(ConfigUtil.get("opencv.properties", "numImages"));
			// 创建一个线程池
			ExecutorService pool = Executors.newFixedThreadPool(taskSize);
			// 获取图片的矩阵Mat
			if (null == EhCacheUtils.getInstance().get("ehcache001", "orignalMatList")) {
				orignalImageList = OpencvUtils.getOrignalImageMatList(orignalImageDirPath);
				EhCacheUtils.getInstance().put("ehcache001", "orignalMatList", orignalImageList);
			} else {
				orignalImageList = (List<Image>) EhCacheUtils.getInstance().get("ehcache001", "orignalMatList");
			}
			// 将原始图库里所有的图片按照线程的个数分为均等分的list集合
			List<List<Image>> orignalListnums = ListUtils.averageAssign(orignalImageList, taskSize);
			// 开始记多线程开始的时间
			Date begindate = new Date();
			// 创建多个有返回值的任务
			List<Future> list = new ArrayList<Future>();
			for (int i = 0; i < taskSize; i++) {
				Callable c = new MyImageCompare(orignalListnums.get(i), destImage);
				Future f = pool.submit(c);
				list.add(f);
			}
			//关闭线程池
			pool.shutdown();
			//获取所有并发任务的运行结果
			List<Image> allImagePointList = new ArrayList<Image>();
			for (Future f : list) {
				// 从Future对象上获取任务的返回值，并输出到控制台
				allImagePointList.addAll((List) f.get());
			}
			Collections.sort(allImagePointList);
			Date enddate = new Date();
			System.out.println("----程序结束运行----，程序运行时间【" + (enddate.getTime() - begindate.getTime()) + "毫秒】");
			for (int i = 0; i < imageSize; i++) {
				Image resultImage = allImagePointList.get(i);
				ImageVO imageVO = imageDao.getOneImageByName(resultImage.getImageName()).get(0);
				imageVO.setSimliarPercent(resultImage.getSimliarPercent());
				sortImageResult.add(imageVO);
			}
			String msg = "The rest is success!";
			map.put("imageSimliarResult", sortImageResult);
			returnMsgResult.setCode(code);
			returnMsgResult.setMsg(msg);
			returnMsgResult.setResult(map);
			return returnMsgResult;
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "The has a exception,Failed!";
			code = "999";
			returnMsgResult.setMsg(msg);
		}
		return returnMsgResult;
	}

}

/**
 * 多线程并发框架中带有返回值的实现类
 * 
 * @author zhouzhou
 *
 */
class MyImageCompare implements Callable<Object> {

	private List<Image> orignalImages;

	private Image destImage;

	private float nndrRatio = 0.7f;

	MyImageCompare(List<Image> orignalImages, Image destImage) {
		this.orignalImages = orignalImages;
		this.destImage = destImage;
	}

	@Override
	public Object call() throws Exception {
		for (Image orignalImage : orignalImages) {
			double similarPercent = matchImage(destImage.getMatOfKeyPoint(), orignalImage.getMatOfKeyPoint());
			orignalImage.setSimliarPercent(similarPercent);
		}

		return orignalImages;
	}

	public double matchImage(MatOfKeyPoint templateDescriptors, MatOfKeyPoint originalDescriptors) {
		// 获取图片匹配最佳像素点
		int matchesPointCount = 0;
		int allPointCount = 0;
		List<MatOfDMatch> matches = new LinkedList<MatOfDMatch>();
		long beginMatch = System.currentTimeMillis();
		DescriptorMatcher descriptorMatcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
		/**
		 * knnMatch方法的作用就是在给定特征描述集合中寻找最佳匹配
		 * 使用KNN-matching算法，令K=2，则每个match得到两个最接近的descriptor，然后计算最接近距离和次接近距离之间的比值，当比值大于既定值时，才作为最终match。
		 */
		descriptorMatcher.knnMatch(templateDescriptors, originalDescriptors, matches, 2);
		LinkedList<DMatch> goodMatchesList = new LinkedList<DMatch>();
		// 对匹配结果进行筛选，依据distance进行筛选
		matches.forEach(match -> {
			DMatch[] dmatcharray = match.toArray();
			DMatch m1 = dmatcharray[0];
			DMatch m2 = dmatcharray[1];
			if (m1.distance <= m2.distance * nndrRatio) {
				goodMatchesList.addLast(m1);
			}
		});
		// 获取两幅图像相似匹配的点的数量
		matchesPointCount = goodMatchesList.size();
		// 获取总匹配的像素点数
		allPointCount = matches.size();
		// 计算图片的相似度
		double result = new BigDecimal((float) matchesPointCount / allPointCount).setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue() * 100;
		return result;
	}

}
