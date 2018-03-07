/**
 * 
 */
package com.ocarmon.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ocarmon.config.Constants;
import com.ocarmon.entity.Articles;
import com.ocarmon.entity.UrlToken;
import com.ocarmon.service.RedisService;
import com.ocarmon.service.SpliderService;
import com.ocarmon.util.HttpClientUtil;

/**
 * @author 李浩铭
 * @since 2018年3月7日 上午10:28:44
 */
@Service
public class SpliderServiceImpl implements SpliderService {

	private final static Logger logger = (Logger) Logger.getInstance(SpliderServiceImpl.class);
	@Autowired
	private RedisService redisService;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void start() {
		Queue<String> userTokenQueue = (Queue<String>) redisService.get(Constants.USERTTOKENQUEUE);
		String userToken = userTokenQueue.poll();
		redisService.set(Constants.USERTTOKENQUEUE, userTokenQueue);
		// 开始爬取用户数据
		String url = "https://www.zhihu.com/people/" + userToken + "/following";
		HttpClientUtil clientUtil = new HttpClientUtil();
		try {
			String content = (clientUtil.getWebPage(url));
			Document doc = Jsoup.parse(content);
			String jsonurl = doc.select("[data-state]").first().attr("data-state");
			JSONObject jsonObject = JSONObject.parseObject(jsonurl);
			JSONObject followUser = jsonObject.getJSONObject("entities").getJSONObject("users");
			Set<String> set = followUser.keySet();
			int articlesCount = 0;
			JSONObject user = new JSONObject();
			String urlToken = null;
			for (String key : set) {
				user = followUser.getJSONObject(key);
				articlesCount = user.getIntValue("articlesCount");
				if (articlesCount > 0) {// 判断用户写的文章数是否大于，若大于1则提取用户
					urlToken = user.getString("urlToken");
					
					UrlToken token= mongoTemplate.findOne(new Query(Criteria.where("urlToken").is(urlToken)), UrlToken.class);
					if(token!=null) {
						continue;
					}
					JSONObject tokenJson=new JSONObject();
					tokenJson.put("urlToken", urlToken);
					mongoTemplate.insert(tokenJson.toString(), "splidered_users");
					
					if (!userTokenQueue.contains(urlToken)) {
						userTokenQueue = (Queue<String>) redisService.get(Constants.USERTTOKENQUEUE);
						userTokenQueue.add(urlToken);
						redisService.set(Constants.USERTTOKENQUEUE, userTokenQueue);
					}
				}
			}
			
			
			// 开始爬取文章
			url = "https://www.zhihu.com/people/" + urlToken + "/posts";
			content = (clientUtil.getWebPage(url));
			doc = Jsoup.parse(content);
			jsonurl = doc.select("[data-state]").first().attr("data-state");
			jsonObject = JSONObject.parseObject(jsonurl);
			JSONObject articlesJSON = jsonObject.getJSONObject("entities").getJSONObject("articles");
			set = articlesJSON.keySet();
			JSONObject articles = new JSONObject();
			for (String key : set) {
				articles = articlesJSON.getJSONObject(key);
				Articles qArticles = mongoTemplate
						.findOne(new Query(Criteria.where("id").is(articles.getIntValue("id"))), Articles.class);
				if(qArticles==null) {
					logger.info("爬取文章:" + articles.getString("title"));
					mongoTemplate.insert(articles, "articles");// 保存冷数据到mongodb
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ocarmon.service.SpliderService#test(int)
	 */
	@Override
	public void test(int index) {
		try {
			System.out.println("线程:" + index);
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
