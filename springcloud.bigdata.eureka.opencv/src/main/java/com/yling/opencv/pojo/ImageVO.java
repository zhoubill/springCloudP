package com.yling.opencv.pojo;

import java.util.Date;

public class ImageVO {

	public String imageId;

	public Date createAt;

	public Integer sort;

	public Integer status;

	public Date updateAt;

	public Integer version;

	public String imageName;

	public String imageDesc;

	public String imageClassification;

	public String imagePath;

	public int matchesPointCount;

	public double simliarPercent;

	public double getSimliarPercent() {
		return simliarPercent;
	}

	public void setSimliarPercent(double simliarPercent) {
		this.simliarPercent = simliarPercent;
	}

	public int getMatchesPointCount() {
		return matchesPointCount;
	}

	public void setMatchesPointCount(int matchesPointCount) {
		this.matchesPointCount = matchesPointCount;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageDesc() {
		return imageDesc;
	}

	public void setImageDesc(String imageDesc) {
		this.imageDesc = imageDesc;
	}

	public String getImageClassification() {
		return imageClassification;
	}

	public void setImageClassification(String imageClassification) {
		this.imageClassification = imageClassification;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
