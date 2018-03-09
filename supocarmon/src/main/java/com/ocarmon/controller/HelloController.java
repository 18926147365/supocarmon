/**
 * 
 */
package com.ocarmon.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.ocarmon.entity.Articles;
import com.ocarmon.entity.UrlToken;
import com.ocarmon.service.RedisService;
import com.ocarmon.service.SpliderService;
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
	@RequestMapping("save")
	public void save() {
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String url = "https://www.zhihu.com/people/" + "ou-ba-81-97" + "/posts";
		String content;
		try {
			content = (httpClientUtil.getWebPage(url));
			Document doc = Jsoup.parse(content);
			String jsonurl = doc.select("[data-state]").first().attr("data-state");
			JSONObject jsonObject = JSONObject.parseObject(jsonurl);
			JSONObject articlesJSON = jsonObject.getJSONObject("entities").getJSONObject("articles");
			Set<String> set = articlesJSON.keySet();
			JSONObject articles = new JSONObject();
			articles.put("urlToken", "4564654");
				mongoTemplate.insert(articles, "splidered_users");
				
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("stop")
	public void stop() {
		scheduledThreadPool.shutdown();
	}

	@RequestMapping("start")
	public void strat() {
		scheduledThreadPool=Executors.newScheduledThreadPool(3);

		for (int i = 0; i < 3; i++) {
			scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					count++;
				
					//若爬取数大于500 ,暂停线程池
					if(count>500) {
						LOGGER.info("线程暂停");
						try {
							scheduledThreadPool.awaitTermination(5, TimeUnit.MINUTES);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						count=0;
						
					}
					spliderService.start();
				}
			}, 0, 3, TimeUnit.SECONDS);
		}
	}

	@RequestMapping("hello")
	public void hello() {
		Queue<String> queue = new LinkedList<String>();
		
		queue.add("kentchou");
		queue.add("tata-wong-83");
		queue.add("xia-tian-20-85-95");
		queue.add("neal-sheng");
		queue.add("yang-zhi-64-93");
		queue.add("lin-yong-kang-65");
		redisService.set("userTokenQueue", queue);
	}

	@RequestMapping("getHello")
	public void getHello() {
		Queue<String> queue = (Queue<String>) redisService.get("userTokenQueue");
		// queue.add("45646");
		// redisService.set("mytest", queue);
		for (String key : queue) {
			System.out.println(key);
		}
	}
}
