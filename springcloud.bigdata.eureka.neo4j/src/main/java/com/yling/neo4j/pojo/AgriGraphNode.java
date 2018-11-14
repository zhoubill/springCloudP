package com.yling.neo4j.pojo;

public class AgriGraphNode {

	public String parentId;

	public String name;

	public String selfId;
	
	public String deepLevel;

	public String getDeepLevel() {
		return deepLevel;
	}

	public void setDeepLevel(String deepLevel) {
		this.deepLevel = deepLevel;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String isleaf;
	
	public String parentName;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSelfId() {
		return selfId;
	}

	public void setSelfId(String selfId) {
		this.selfId = selfId;
	}

	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

}
