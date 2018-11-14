package com.yling.opencv.service;

import com.yling.opencv.pojo.ReturnMsgResult;

/**
 * 图片比较的服务类
 * @author zhouzhou
 *
 */
public interface ImageCompareI {
	
	/**
	 * 根据传进来的图片和图库里面的图片比较获取最大的
	 * @param destImagePath
	 * @param orignalImageDirPath
	 * @return
	 */
	public ReturnMsgResult getMaxSimilartilyImage(String destImagePath);

}
