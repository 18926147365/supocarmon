/**
 * 
 */
package com.ocarmon.util;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Set;

import org.bson.BSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

/** 
* @author 李浩铭 
* @since 2018年2月27日 下午5:31:22
*/
public class Test {

	public static void main(String[] args) {
		String urlToken="ou-ba-81-97";
//		String urlToken="qing-ke-85-7";
//		String urlToken="liu-yi-yao-93-70";
//		connDB();
		ddd(urlToken);
	}
	
	public static void connDB() {
		   try {
			MongoClient client = new MongoClient("39.108.115.133",27017);
			DB db=client.getDB("ocarmon");
			db.authenticate("root", "a972188163;".toCharArray());
			
			DBObject dbObject= new BasicDBObject();  
			dbObject.put("name", "123");
			
			DBCollection course = db.getCollection("splidered_users");//
			System.out.println(course.save(dbObject));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		   
	}
	
	
	
	private static void ddd(String name) {
		String url="https://www.zhihu.com/people/"+name+"/following";
//		String url="https://www.zhihu.com/people/liu-yi-yao-93-70/posts";
		HttpClientUtil httpClientUtil=new HttpClientUtil();
		try {
			String content=(httpClientUtil.getWebPage(url));
			System.out.println(content);
			Document doc = Jsoup.parse(content);
			String jsonurl = doc.select("[data-state]").first().attr("data-state");
			JSONObject jsonObject=JSONObject.parseObject(jsonurl);
			JSONObject followUser= jsonObject.getJSONObject("entities").getJSONObject("users");
			Set<String> set= followUser.keySet();
			int articlesCount=0;
			JSONObject user=new JSONObject();
			String urlToken=null;
			for (String key : set) {
				user=followUser.getJSONObject(key);
				articlesCount=user.getIntValue("articlesCount");
				if(articlesCount>0) {//判断用户写的文章数是否大于，若大于1则提取用户
					urlToken=user.getString("urlToken");
					System.out.println(urlToken);
				}
				
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//liu-yi-yao-93-70
	private static void kkk(String urlToken) {
		HttpClientUtil httpClientUtil=new HttpClientUtil();
		String url="https://www.zhihu.com/people/"+urlToken+"/posts";
		String content;
		try {
			content = (httpClientUtil.getWebPage(url));
			Document doc = Jsoup.parse(content);
			String jsonurl = doc.select("[data-state]").first().attr("data-state");
			JSONObject jsonObject=JSONObject.parseObject(jsonurl);
			JSONObject articlesJSON= jsonObject.getJSONObject("entities").getJSONObject("articles");
			Set<String> set=articlesJSON.keySet();
			JSONObject articles=new JSONObject();
			for (String key : set) {
				articles=articlesJSON.getJSONObject(key);
				System.out.println(articles);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
}
