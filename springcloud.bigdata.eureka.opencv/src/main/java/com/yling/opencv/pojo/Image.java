package com.yling.opencv.pojo;

import java.io.Serializable;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;

public class Image implements Comparable<Image>,Serializable {
	/**
	 * 生产序列化ID
	 */
	private static final long serialVersionUID = -8789659802868001306L;

	public int matchesPointCount;
	
	public double simliarPercent;

	public String imagePath;

	public MatOfKeyPoint matOfKeyPoint;

	public Mat mat;
	
	public String matJson;
	
	public String imageName;

	public double getSimliarPercent() {
		return simliarPercent;
	}

	public void setSimliarPercent(double simliarPercent) {
		this.simliarPercent = simliarPercent;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getMatJson() {
		return matJson;
	}

	public void setMatJson(String matJson) {
		this.matJson = matJson;
	}

	public Mat getMat() {
		return mat;
	}

	public void setMat(Mat mat) {
		this.mat = mat;
	}

	public int getMatchesPointCount() {
		return matchesPointCount;
	}

	public void setMatchesPointCount(int matchesPointCount) {
		this.matchesPointCount = matchesPointCount;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public MatOfKeyPoint getMatOfKeyPoint() {
		return matOfKeyPoint;
	}

	public void setMatOfKeyPoint(MatOfKeyPoint matOfKeyPoint) {
		this.matOfKeyPoint = matOfKeyPoint;
	}

	@Override
	public int compareTo(Image otherImage) {
		double i = otherImage.getSimliarPercent() - this.getSimliarPercent();
		int rersult = (new Double(i)).intValue();
		return rersult;
	}

}

