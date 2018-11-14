package com.yling.opencv.initialize;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.yling.opencv.pojo.Image;
import com.yling.opencv.utils.ConfigUtil;
import com.yling.opencv.utils.EhCacheUtils;
import com.yling.opencv.utils.OpencvUtils;

@Component
public class InitOpencvEhcache implements CommandLineRunner{

	@Override
	public void run(String... arg0) throws Exception {
		String orignalImageDirPath = ConfigUtil.get("opencv.properties", "origanlImageDirPath");
		System.out.println("缓存 初始化成功");
		if (EhCacheUtils.getInstance().get("ehcache001", "orignalMatList") != null) {
			EhCacheUtils.getInstance().remove("ehcache001", "orignalMatList");
		} else {
			List<Image> orignalImageList = new ArrayList<Image>();
			orignalImageList = OpencvUtils.getOrignalImageMatList(orignalImageDirPath);
			EhCacheUtils.getInstance().put("ehcache001", "orignalMatList", orignalImageList);
		}
		
	}

}
