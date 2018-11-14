package com.yling.recommend.pojo;



public class RecommendVO {

	public String sourceID;

	public String recommendID;

	public String type;

	public Double match_degree;

	public Double getMatch_degree() {
		return match_degree;
	}

	public void setMatch_degree(Double match_degree) {
		this.match_degree = match_degree;
	}

	public String getSourceID() {
		return sourceID;
	}

	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}

	public String getRecommendID() {
		return recommendID;
	}

	public void setRecommendID(String recommendID) {
		this.recommendID = recommendID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
