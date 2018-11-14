package com.yling.opencv.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Size;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import com.yling.opencv.pojo.Image;


public class OpencvUtils {
	
	
	/**
	 * 将文件夹中的图片转换成矩阵Mat
	 * 
	 * @param directoryPath
	 * @return
	 */
	public static List<Image> getMatFromImage(String directoryPath) {
		System.load("E:\\opencv-2413\\opencv\\build\\java\\x64\\opencv_java2413.dll");
//      ======================linux环境下加载的库文件===================================
//		String opencvsoPath = ConfigUtil.get("opencv.properties", "opencvsoPath");
//		String nativeLibraryPath = System.getProperty("user.dir")+opencvsoPath+"libopencv_java2413.so";
//		System.load(nativeLibraryPath);
		File baseFile = new File(directoryPath);
		List<Image> orignalImageList = new ArrayList<Image>();
		if (baseFile.isFile() || !baseFile.exists()) {

		}
		File[] files = baseFile.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				System.out.println("暂时先不处理子文件夹的文件");
			} else {
				Image image = new Image();
				Mat originalMat = Highgui.imread(file.getAbsolutePath(), Highgui.CV_LOAD_IMAGE_COLOR);
				image.setImagePath(file.getAbsolutePath());
				image.setMat(originalMat);
//				image.setMatJson(MatUtils.matToJson(originalMat));
				image.setImageName(file.getName());
				orignalImageList.add(image);
			}
		}
		return orignalImageList;
	}
	
	
	/**
	 * 根据图片矩阵Mat提取图片特征点
	 * 
	 * @param imageMat
	 * @return
	 */
	public static MatOfKeyPoint getImageDescitor(Mat imageMat) {
		// 指定特征点算法SIFT
		FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.SIFT);
		DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.SIFT);
		// 目标图的特征点提取
		MatOfKeyPoint templateKeyPoints = new MatOfKeyPoint();
		Mat cutNoiseTemplateImage = imageMat.clone();
		// 高斯滤波去噪
		Imgproc.GaussianBlur(imageMat, cutNoiseTemplateImage, new Size(9, 9), 0, 0);
		// 获取目标图的特征点
		featureDetector.detect(cutNoiseTemplateImage, templateKeyPoints);
		// 提取模板图的特征点
		MatOfKeyPoint templateDescriptors = new MatOfKeyPoint();
		descriptorExtractor.compute(cutNoiseTemplateImage, templateKeyPoints, templateDescriptors);
		return templateDescriptors;
	}
	
	/**
	 * 根据图库存放目录路径获取图片的矩阵向量集合list
	 * @param orignalImageDirPath
	 * @return
	 */
	public static List<Image> getOrignalImageMatList(String orignalImageDirPath) {
		List<Image> orignalImageList = OpencvUtils.getMatFromImage(orignalImageDirPath);
		for (Image orignalImage : orignalImageList) {
			// 提取每张图片的特征点
			MatOfKeyPoint matofKeyPoint = OpencvUtils.getImageDescitor(orignalImage.getMat());
			orignalImage.setMatOfKeyPoint(matofKeyPoint);
		}
		return orignalImageList;
	}

	

}
