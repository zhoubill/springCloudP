package com.yling.neo4j.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.springframework.stereotype.Service;


import com.yling.neo4j.pojo.AgriGraphNode;
import com.yling.neo4j.pojo.AgriGraphRealtionships;
import com.yling.neo4j.pojo.DiseasesGraphNode;
import com.yling.neo4j.pojo.ReturnMsgResult;
import com.yling.neo4j.service.AgriDiseasesGraphI;
import com.yling.neo4j.utils.Neo4jClinet;

@Service
public class AgriDiseasesGraphImpl implements AgriDiseasesGraphI {

	@Override
	public ReturnMsgResult getDataClassificationGraph(String deeplevel) {
		ReturnMsgResult returnMsgResult = new ReturnMsgResult();
		List<AgriGraphNode> agriGraphNodeList;
		Map map = new HashMap();
		String code = "000";
		List<AgriGraphNode> agriAllGraphNodeList = new ArrayList<AgriGraphNode>();
		List<AgriGraphRealtionships> agriAllGraphRealtionshipsList = new ArrayList<AgriGraphRealtionships>();
		AgriGraphNode rootGraphNode = new AgriGraphNode();
		rootGraphNode.setName("杨凌农业云");
		rootGraphNode.setParentId("None");
		rootGraphNode.setSelfId("1");
		rootGraphNode.setIsleaf("0");
		rootGraphNode.setDeepLevel("0");
		agriAllGraphNodeList.add(rootGraphNode);
		if(deeplevel.equalsIgnoreCase("4")) {
			 agriGraphNodeList = getArgiGraphNodes();
		}else {
			 agriGraphNodeList = getThreeLevelArgiGraphNodes();
		}
		agriAllGraphNodeList.addAll(agriGraphNodeList);
		
		List<AgriGraphRealtionships> oneLevelRelationships = getOneLevelAgriRelationship();
		List<AgriGraphRealtionships> towLevelRelationships = getTwoLevelAgriRelationship();
		List<AgriGraphRealtionships> threeLevelRelationships = getThreeLevelAgriRelationship();
		List<AgriGraphRealtionships> fourLevelRelationships = getFourLevelAgriRelationship();
		
		agriAllGraphRealtionshipsList.addAll(oneLevelRelationships);
		agriAllGraphRealtionshipsList.addAll(towLevelRelationships);
		agriAllGraphRealtionshipsList.addAll(threeLevelRelationships);
		if(deeplevel.equalsIgnoreCase("4")) {
		agriAllGraphRealtionshipsList.addAll(fourLevelRelationships);
		}
		
		String msg = "The rest is success!";
		map.put("agriGraphNode", agriAllGraphNodeList);
		map.put("agriGraphRealtionship", agriAllGraphRealtionshipsList);
		returnMsgResult.setCode(code);
		returnMsgResult.setMsg(msg);
		returnMsgResult.setResult(map);
		return returnMsgResult;

	}

	@Override
	public ReturnMsgResult getAgriDiseasesAssociateFactor(String diseaseName) {
		ReturnMsgResult returnMsgResult = new ReturnMsgResult();
		Map map = new HashMap();
		String code = "000";
		List<DiseasesGraphNode> associateExpertList = getAssociateExpert();
		List<DiseasesGraphNode> associateVideoList = getAssociateVideo();
		List<DiseasesGraphNode> associateArticleList = getAssociateArticle(diseaseName);
		List<DiseasesGraphNode> associatePesticideList = getAssociatePesticide(diseaseName);

		String msg = "The rest is success!";
		map.put("expertList", associateExpertList);
		map.put("videoList", associateVideoList);
		map.put("articleList", associateArticleList);
		map.put("pesticideList", associatePesticideList);
		returnMsgResult.setCode(code);
		returnMsgResult.setMsg(msg);
		returnMsgResult.setResult(map);
		return returnMsgResult;
	}
	
	
	/**
	 * 获取neo4j图数据库农业数据分类一到三级的节点
	 * 
	 * @return
	 */
	public List<AgriGraphNode> getThreeLevelArgiGraphNodes() {
		List<AgriGraphNode> agriGraphList = new ArrayList<AgriGraphNode>();
		String neo4jSql = "MATCH (p:classification)-[r:classification*1..3]->(x:classification) where p.deeplevel='0' return x.name as name,x.parentname as parentname,x.deeplevel as deeplevel,x.selfid as selfid,x.parentid as parentid ";
		try (Session session = Neo4jClinet.getInstance().getNeo4JDriver().session()) {
			session.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {
					StatementResult result = tx.run(neo4jSql);
					while (result.hasNext()) {
						Record record = result.next();
						AgriGraphNode node = new AgriGraphNode();
						node.setName(record.get("name").toString());
						node.setParentName(record.get("parentname").toString());
						node.setDeepLevel(record.get("deeplevel").toString());
						node.setSelfId(record.get("selfid").toString());
						node.setParentId(record.get("parentid").toString());
						agriGraphList.add(node);
					}
					return "success";
				}
			});
		}
		return agriGraphList;
	}

	/**
	 * 获取neo4j图数据库农业数据分类所有的节点
	 * 
	 * @return
	 */
	public List<AgriGraphNode> getArgiGraphNodes() {
		List<AgriGraphNode> agriGraphList = new ArrayList<AgriGraphNode>();
		String neo4jSql = "MATCH (p:classification)-[r:classification*1..4]->(x:classification) where p.deeplevel='0' return x.name as name,x.parentname as parentname,x.deeplevel as deeplevel,x.selfid as selfid,x.parentid as parentid";
		try (Session session = Neo4jClinet.getInstance().getNeo4JDriver().session()) {
			session.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {
					StatementResult result = tx.run(neo4jSql);
					while (result.hasNext()) {
						Record record = result.next();
						AgriGraphNode node = new AgriGraphNode();
						node.setName(record.get("name").toString());
						node.setParentName(record.get("parentname").toString());
						node.setDeepLevel(record.get("deeplevel").toString());
						node.setSelfId(record.get("selfid").toString());
						node.setParentId(record.get("parentid").toString());
						agriGraphList.add(node);
					}
					return "success";
				}
			});
		}
		return agriGraphList;
	}

	/***
	 * 获取农业数据分类的一层级的关系
	 * 
	 * @return
	 */
	public List<AgriGraphRealtionships> getOneLevelAgriRelationship() {
		List<AgriGraphRealtionships> relationshipList = new ArrayList<AgriGraphRealtionships>();
		String neo4jSql = "MATCH (p:classification {deeplevel:'0'})-[r:classification]->(x:classification{deeplevel:'1'}) RETURN r.chainOutName as chainOutName ,r.chainEntryName as chainEntryName";
		try (Session session = Neo4jClinet.getInstance().getNeo4JDriver().session()) {
			session.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {
					StatementResult result = tx.run(neo4jSql);
					while (result.hasNext()) {
						Record record = result.next();
						AgriGraphRealtionships relationship = new AgriGraphRealtionships();
						relationship.setChainEntryName(record.get("chainOutName").toString());
						relationship.setChainOutName(record.get("chainEntryName").toString());
						relationshipList.add(relationship);
					}
					return "success";
				}
			});
		}
		return relationshipList;
	}

	/***
	 * 获取农业数据分类的二层级的关系
	 * 
	 * @return
	 */
	public List<AgriGraphRealtionships> getTwoLevelAgriRelationship() {
		List<AgriGraphRealtionships> relationshipList = new ArrayList<AgriGraphRealtionships>();
		String neo4jSql = "MATCH (p:classification {deeplevel:'1'})-[r:classification]->(x:classification{deeplevel:'2'}) RETURN r.chainOutName as chainOutName ,r.chainEntryName as chainEntryName";
		try (Session session = Neo4jClinet.getInstance().getNeo4JDriver().session()) {
			session.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {
					StatementResult result = tx.run(neo4jSql);
					while (result.hasNext()) {
						Record record = result.next();
						AgriGraphRealtionships relationship = new AgriGraphRealtionships();
						relationship.setChainEntryName(record.get("chainOutName").toString());
						relationship.setChainOutName(record.get("chainEntryName").toString());
						relationshipList.add(relationship);
					}
					return "success";
				}
			});
		}
		return relationshipList;
	}

	/***
	 * 获取农业数据分类的三层级的关系
	 * 
	 * @return
	 */
	public List<AgriGraphRealtionships> getThreeLevelAgriRelationship() {
		List<AgriGraphRealtionships> relationshipList = new ArrayList<AgriGraphRealtionships>();
		String neo4jSql = "MATCH (p:classification {deeplevel:'2'})-[r:classification]->(x:classification{deeplevel:'3'}) RETURN r.chainOutName as chainOutName ,r.chainEntryName as chainEntryName";
		try (Session session = Neo4jClinet.getInstance().getNeo4JDriver().session()) {
			session.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {
					StatementResult result = tx.run(neo4jSql);
					while (result.hasNext()) {
						Record record = result.next();
						AgriGraphRealtionships relationship = new AgriGraphRealtionships();
						relationship.setChainEntryName(record.get("chainOutName").toString());
						relationship.setChainOutName(record.get("chainEntryName").toString());
						relationshipList.add(relationship);
					}
					return "success";
				}
			});
		}
		return relationshipList;
	}

	/***
	 * 获取农业数据分类的四层级的关系
	 * 
	 * @return
	 */
	public List<AgriGraphRealtionships> getFourLevelAgriRelationship() {
		List<AgriGraphRealtionships> relationshipList = new ArrayList<AgriGraphRealtionships>();
		String neo4jSql = "MATCH (p:classification {deeplevel:'3'})-[r:classification]->(x:classification{deeplevel:'4'}) RETURN r.chainOutName as chainOutName ,r.chainEntryName as chainEntryName";
		try (Session session = Neo4jClinet.getInstance().getNeo4JDriver().session()) {
			session.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {
					StatementResult result = tx.run(neo4jSql);
					while (result.hasNext()) {
						Record record = result.next();
						AgriGraphRealtionships relationship = new AgriGraphRealtionships();
						relationship.setChainEntryName(record.get("chainOutName").toString());
						relationship.setChainOutName(record.get("chainEntryName").toString());
						relationshipList.add(relationship);
					}
					return "success";
				}
			});
		}
		return relationshipList;
	}

	/***
	 * 获取与病虫害相关联的专家
	 * 
	 * @return
	 */
	public List<DiseasesGraphNode> getAssociateExpert() {
		List<DiseasesGraphNode> associateExpertList = new ArrayList<DiseasesGraphNode>();
		String neo4jSql = "match (p:diseases)-[r:disexpert]->(p2) return p2.name as name,p2.id as id,p2.label as lable limit 5";
		try (Session session = Neo4jClinet.getInstance().getNeo4JDriver().session()) {
			session.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {
					StatementResult result = tx.run(neo4jSql);
					while (result.hasNext()) {
						Record record = result.next();
						DiseasesGraphNode node = new DiseasesGraphNode();
						node.setNodeId(record.get("id").toString());
						node.setNodeName(record.get("name").toString());
						node.setLable(record.get("lable").toString());
						associateExpertList.add(node);
					}
					return "success";
				}
			});
		}
		return associateExpertList;
	}

	/***
	 * 获得与病虫害相关联的视频
	 * 
	 * @return
	 */
	public List<DiseasesGraphNode> getAssociateVideo() {
		List<DiseasesGraphNode> associateVideoList = new ArrayList<DiseasesGraphNode>();
		String neo4jSql = "match (p:diseases)-[r:disvideo]->(p2) return p2.name as name,p2.id as id,p2.label as lable limit 5";
		try (Session session = Neo4jClinet.getInstance().getNeo4JDriver().session()) {
			session.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {
					StatementResult result = tx.run(neo4jSql);
					while (result.hasNext()) {
						Record record = result.next();
						DiseasesGraphNode node = new DiseasesGraphNode();
						node.setNodeId(record.get("id").toString());
						node.setNodeName(record.get("name").toString());
						node.setLable(record.get("lable").toString());
						associateVideoList.add(node);
					}
					return "success";
				}
			});
		}
		return associateVideoList;
	}

	/***
	 * 根据病虫害名字获取相关联的技术文章
	 * 
	 * @param diseaseName
	 * @return
	 */
	public List<DiseasesGraphNode> getAssociateArticle(String diseaseName) {
		List<DiseasesGraphNode> associateArticleList = new ArrayList<DiseasesGraphNode>();
		StringBuffer neo4jSql = new StringBuffer();
		neo4jSql.append("match (p:diseases)-[r:disarticle]->(p2) where r.typename = ").append("'" + diseaseName + "'")
				.append(" return p2.name as name,p2.id as id,p2.label as lable limit 5");
		try (Session session = Neo4jClinet.getInstance().getNeo4JDriver().session()) {
			session.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {
					StatementResult result = tx.run(neo4jSql.toString());
					while (result.hasNext()) {
						Record record = result.next();
						DiseasesGraphNode node = new DiseasesGraphNode();
						node.setNodeId(record.get("id").toString());
						node.setNodeName(record.get("name").toString());
						node.setLable(record.get("lable").toString());
						associateArticleList.add(node);
					}
					return "success";
				}
			});
		}
		return associateArticleList;
	}

	/***
	 * 根据病虫害名字获取相关联的农药
	 * 
	 * @param diseaseName
	 * @return
	 */
	public List<DiseasesGraphNode> getAssociatePesticide(String diseaseName) {
		List<DiseasesGraphNode> associatePesticideList = new ArrayList<DiseasesGraphNode>();
		StringBuffer neo4jSql = new StringBuffer();
		neo4jSql.append("match (p:diseases)-[r:dispesticide]->(p2) where r.typename = ").append("'" + diseaseName + "'")
				.append(" return p2.name as name,p2.id as id,p2.label as lable limit 5");
		try (Session session = Neo4jClinet.getInstance().getNeo4JDriver().session()) {
			session.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {
					StatementResult result = tx.run(neo4jSql.toString());
					while (result.hasNext()) {
						Record record = result.next();
						DiseasesGraphNode node = new DiseasesGraphNode();
						node.setNodeId(record.get("id").toString());
						node.setNodeName(record.get("name").toString());
						node.setLable(record.get("lable").toString());
						associatePesticideList.add(node);
					}
					return "success";
				}
			});
		}
		return associatePesticideList;
	}

}
