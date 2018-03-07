/**
 * 
 */
package com.ocarmon.util;

import java.io.IOException;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSONObject;

/** 
* @author 李浩铭 
* @since 2018年2月27日 下午5:31:22
*/
public class Test {

	public static void main(String[] args) {
		String urlToken="ou-ba-81-97";
//		String urlToken="qing-ke-85-7";
//		String urlToken="liu-yi-yao-93-70";
		ddd(urlToken);
	}
	
	
	private static void ddd(String name) {
		String url="https://www.zhihu.com/people/"+name+"/following";
//		String url="https://www.zhihu.com/people/liu-yi-yao-93-70/posts";
		HttpClientUtil httpClientUtil=new HttpClientUtil();
		try {
			String content=(httpClientUtil.getWebPage(url));
			Document doc = Jsoup.parse(content);
			String jsonurl = doc.select("[data-state]").first().attr("data-state");
			JSONObject jsonObject=JSONObject.parseObject(jsonurl);
			System.out.println(jsonObject);
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
