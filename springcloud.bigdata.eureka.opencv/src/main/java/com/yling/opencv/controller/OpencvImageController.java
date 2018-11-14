package com.yling.opencv.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yling.opencv.pojo.ReturnMsgResult;
import com.yling.opencv.service.ImageCompareI;
import com.yling.opencv.utils.ConfigUtil;

@RestController
@RequestMapping("/opencv")
public class OpencvImageController {
	
	@Autowired
	private ImageCompareI imageCompareI;
	
	
	@RequestMapping(value = "/simlarImage", method = RequestMethod.POST)
	@ResponseBody  
	public ReturnMsgResult getMaxSimliartyImage(@RequestParam("destImage") MultipartFile destImage) {
		try {
			String fileName=destImage.getOriginalFilename();// 文件原名称
			String trueFileName=String.valueOf(System.currentTimeMillis())+fileName;
			String destImageTmpPath = ConfigUtil.get("opencv.properties", "destImageTmpPath");
			// 设置存放图片文件的路径
			String destImagePath=destImageTmpPath+trueFileName;
			 // 转存文件到指定的路径
			destImage.transferTo(new File(destImagePath));
			ReturnMsgResult returnMsgResult = imageCompareI.getMaxSimilartilyImage(destImagePath);
			return returnMsgResult;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	} 

}
