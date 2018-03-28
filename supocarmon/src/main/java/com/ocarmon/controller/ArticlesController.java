
package com.ocarmon.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.ocarmon.entity.Articles;

/** 
* @author 李浩铭 
* @since 2018年3月9日 下午3:31:50
*/
@RestController()
@RequestMapping("/articles")
@ResponseBody
public class ArticlesController extends BaseController{
	private static final Logger LOGGER = Logger.getLogger(ArticlesController.class);
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 *  查询热点话题
	 *  @param 页数
	 * */
	@RequestMapping("queryTopic")
	public String queryTopic(int pageIndex) {
		LOGGER.info("刷新页面");
		DBObject dbObject = new BasicDBObject();  
		BasicDBObject fieldsObject=new BasicDBObject();  
		//指定返回的字段  
		fieldsObject.put("title", true);    
		fieldsObject.put("voteupCount", true);    
		fieldsObject.put("author", true);    
		fieldsObject.put("updated", true);    
		fieldsObject.put("commentCount", true);    
		fieldsObject.put("id", true);    
		fieldsObject.put("_id", false);    
		Query query = new BasicQuery(dbObject,fieldsObject);  
		query.with(new Sort(new Order(Direction.DESC,"voteupCount")));
		query.skip((pageIndex*10));
		query.limit(10);
		List<Articles> list=mongoTemplate.find(query, Articles.class, "articles");
		return retunSuccess(JSONObject.toJSONString(list));
	}
	
	/**
	 * 换一批看看
	 * */
	@RequestMapping("barterArticles")
	public String barterArticles() {
		LOGGER.info("刷新页面");
		DBObject dbObject = new BasicDBObject();  
		BasicDBObject fieldsObject=new BasicDBObject();  
		//指定返回的字段  
		fieldsObject.put("title", true);    
		fieldsObject.put("voteupCount", true);    
		fieldsObject.put("author", true);    
		fieldsObject.put("updated", true);    
		fieldsObject.put("commentCount", true);    
		fieldsObject.put("id", true);    
		fieldsObject.put("_id", false);    
		Query query = new BasicQuery(dbObject,fieldsObject);  
		query.addCriteria(Criteria.where("voteupCount").gt(200));
//		query.with(new Sort(new Order(Direction.DESC,"voteupCount")));
		int skip=(int)(Math.random()*3000);
		query.skip(skip);
		query.limit(10);
		List<Articles> list=mongoTemplate.find(query, Articles.class, "articles");
		return retunSuccess(JSONObject.toJSONString(list));
	}
	
	
	
	
	@RequestMapping("queryById")
	public String queryById(int id) {
		LOGGER.info("查看文章 id="+id);
		Articles articles= mongoTemplate.findOne(new Query().addCriteria(Criteria.where("id").is(id)), Articles.class);
		if(articles==null) {
			return returnInfo("null", 1, "未找到文章");
		}
		return retunSuccess(JSONObject.toJSONString(articles));
	}
}
