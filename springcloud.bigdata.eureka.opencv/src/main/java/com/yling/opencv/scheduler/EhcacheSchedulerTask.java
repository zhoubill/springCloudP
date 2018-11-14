package com.yling.opencv.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yling.opencv.pojo.Image;
import com.yling.opencv.utils.ConfigUtil;
import com.yling.opencv.utils.EhCacheUtils;
import com.yling.opencv.utils.OpencvUtils;

@Component
@Lazy(value = false)
public class EhcacheSchedulerTask {

	/**
	 * 每4个小时定时的清楚缓存，再执行缓存的加入，执行缓存的更新
	 * @author zhouzhou
	 * */
	@Scheduled(cron = "0 0 */4 * * ?")
	public void updateImageEhcache() {
	  String orignalImageDirPath = ConfigUtil.get("opencv.properties", "origanlImageDirPath");
      if( EhCacheUtils.getInstance().get("ehcache001","orignalMatList")!= null) {
    	  EhCacheUtils.getInstance().remove("ehcache001","orignalMatList");
      }else {
    	    List<Image> orignalImageList =  new ArrayList<Image>();
    	    orignalImageList =  OpencvUtils.getOrignalImageMatList(orignalImageDirPath);
			EhCacheUtils.getInstance().put("ehcache001","orignalMatList",orignalImageList);
      }
	}

}
