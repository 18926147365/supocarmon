/**
 * 
 */
package com.ocarmon.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.ocarmon.config.Constants;
import com.ocarmon.entity.Articles;
import com.ocarmon.entity.UrlToken;
import com.ocarmon.service.RedisService;
import com.ocarmon.service.SpliderService;
import com.ocarmon.util.CommonHttpClientUtil;
import com.ocarmon.util.HttpClientUtil;

/**
 * @author 李浩铭
 * @since 2018年3月6日 上午9:50:57
 */
@RestController
public class HelloController {
	private static int count=0;

	private static final Logger LOGGER = Logger.getLogger(HelloController.class);
	private ScheduledExecutorService scheduledThreadPool =null;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private RedisService redisService;
	@Autowired
	private SpliderService spliderService;


	@RequestMapping("find")
	public JSONArray find() {
		JSONArray array = new JSONArray();
		List<Articles> list = mongoTemplate.findAll(Articles.class);
		for (Articles articles : list) {
			JSONObject json = new JSONObject();
			json.put("title", articles.getTitle());
			json.put("count", articles.getVoteupCount());
			array.add(json);
		}

		return array;
	}
	@RequestMapping("findAll")
	public void findAll() {
		try {
			MongoClient client = new MongoClient("39.108.115.133",27017);
			DB db=client.getDB("ocarmon");
			db.authenticate("root", "a972188163;".toCharArray());
			
			DBObject dbObject= new BasicDBObject();  
			
//			List<UrlToken> list=( mongoTemplate.findAll(UrlToken.class));
//			List<DBObject> dbList=new ArrayList<>();
//			DBCollection course = db.getCollection("splidered_users");//
//			
//			for(int i=0;i<list.size();i++) {
//				System.out.println(i+"/"+list.size());
//				dbObject.putAll(ConvertObjToMap(list.get(i)));
//				dbList.add(dbObject);
//				course.save(dbObject);
//			}
			
			
//			System.out.println(course.insert(dbList));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		
		
	}
	private  Map ConvertObjToMap(Object obj){
		  Map<String,Object> reMap = new HashMap<String,Object>();
		  if (obj == null) 
		   return null;
		  Field[] fields = obj.getClass().getDeclaredFields();
		  try {
		   for(int i=0;i<fields.length;i++){
		    try {
		     Field f = obj.getClass().getDeclaredField(fields[i].getName());
		     f.setAccessible(true);
		           Object o = f.get(obj);
		           reMap.put(fields[i].getName(), o);
		    } catch (NoSuchFieldException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    } catch (IllegalArgumentException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    } catch (IllegalAccessException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    }
		   }
		  } catch (SecurityException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  } 
		  return reMap;
		 }
	@RequestMapping("test")
	public String test() {
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String url = "https://www.zhihu.com/people/" + "ou-ba-81-97" + "/posts";
		String content;
		try {
			content = (httpClientUtil.getWebPage(url));
			return content;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("stop")
	public void stop() {
		scheduledThreadPool.shutdown();
	}

	@RequestMapping("start")
	public void strat() {
//		 JSONObject jsonObject = new JSONObject();
//		 String ipString = CommonHttpClientUtil.getIp(
//		 "http://www.jinglingdaili.com/Index-generate_api_url.html?packid=1&qty=1&time=1&pro=&city=&port=1&format=json&ss=1&css=&dt=1");
//		 System.out.println(ipString);
//		 jsonObject = JSONObject.parseObject(ipString);
//		 JSONArray arrays = jsonObject.getJSONArray("data");
//		 JSONObject ipJSON = arrays.getJSONObject(0);
//		 Constants.host =ipJSON.getString("IP");
//		 Constants.post = ipJSON.getIntValue("Port");
		 
		//查询列队中是否存在urlToken
		Queue<String> queue= (Queue<String>) redisService.get(Constants.USERTTOKENQUEUE);
		if(queue==null || queue.size()==0) {
			Query query = new Query();  
			query.with(new Sort(new Order(Direction.DESC,"_id")));
			query.limit(10);
			List<UrlToken> list=mongoTemplate.find(query,UrlToken.class);
			queue=new  LinkedList<>();
			for (UrlToken urlToken : list) {
				queue.add(urlToken.getUrlToken());
				System.out.println(urlToken.getUrlToken());
			}
			redisService.set(Constants.USERTTOKENQUEUE, queue);
		}
		
		final List<String> dateList=new ArrayList<>();
		dateList.add("2");
		dateList.add("5");
		dateList.add("8");
		dateList.add("11");
		dateList.add("14");
		dateList.add("17");
		dateList.add("20");
		dateList.add("23");
		scheduledThreadPool=Executors.newScheduledThreadPool(5);
		for (int i = 0; i < 10; i++) {
			scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					SimpleDateFormat sdf=new SimpleDateFormat("HH");
					try {
						String HH= sdf.format(new Date());
						if(!dateList.contains(HH)) {
							scheduledThreadPool.awaitTermination(60, TimeUnit.MINUTES);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
						spliderService.start();
				}
			}, 0, 300, TimeUnit.MILLISECONDS);
		}
	}

	@RequestMapping("hello")
	public void hello() {
		Queue<String> queue = new LinkedList<String>();
		queue.add("zhang-ya-wei-66");
		queue.add("cherry-kris");
		queue.add("theqiong");
		queue.add("lin-zi-yu-13");
		queue.add("zhu-xu-yue-64");
		queue.add("hushendong");
		
		redisService.set(Constants.USERTTOKENQUEUE, queue);
	}

	@RequestMapping("getHello")
	public String getHello() {
//		UrlToken token= mongoTemplate.findOne(new Query(Criteria.where("urlToken").is("")), UrlToken.class);
		Queue<String> queue = (Queue<String>) redisService.get(Constants.USERTTOKENQUEUE);
		for (String key : queue) {
			System.out.println(key);
		}
		return queue.toString();
	}
}
