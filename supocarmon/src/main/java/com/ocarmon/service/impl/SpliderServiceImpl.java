/**
 * 
 */
package com.ocarmon.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ocarmon.config.Constants;
import com.ocarmon.entity.Articles;
import com.ocarmon.entity.UrlToken;
import com.ocarmon.service.RedisService;
import com.ocarmon.service.SpliderService;
import com.ocarmon.util.CommonHttpClientUtil;
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
		HttpClientUtil clientUtil = new HttpClientUtil(Constants.host, Constants.post);
		try {

			String content = (clientUtil.getWebPage(url));
			Document doc = Jsoup.parse(content);
			String jsonurl = doc.select("[data-state]").first().attr("data-state");
			JSONObject jsonObject = JSONObject.parseObject(jsonurl);
			JSONObject followUser = jsonObject.getJSONObject("entities").getJSONObject("users");
			if (followUser.size() == 0) {
				logger.info("爬取数据异常");
			}
			Set<String> set = followUser.keySet();
			int articlesCount = 0;
			JSONObject user = new JSONObject();
			String urlToken = null;
			for (String key : set) {

				user = followUser.getJSONObject(key);
				articlesCount = user.getIntValue("articlesCount");
				if (articlesCount > 0 || userTokenQueue.size() < 10) {// 判断用户写的文章数是否大于，若大于1则提取用户
					urlToken = user.getString("urlToken");

					UrlToken token = mongoTemplate.findOne(new Query(Criteria.where("urlToken").is(urlToken)),
							UrlToken.class);
					if (token != null || StringUtils.isEmpty(urlToken) || urlToken.equals("null")) {
						continue;
					}

					if (!userTokenQueue.contains(urlToken)) {
						userTokenQueue = (Queue<String>) redisService.get(Constants.USERTTOKENQUEUE);
						userTokenQueue.add(urlToken);
						redisService.set(Constants.USERTTOKENQUEUE, userTokenQueue);
						JSONObject tokenJson = new JSONObject();
						tokenJson.put("urlToken", urlToken);
						mongoTemplate.insert(tokenJson.toString(), "splidered_users");
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
				if (StringUtils.isEmpty(articles.getString("title"))) {
					continue;
				}
				Articles qArticles = mongoTemplate
						.findOne(new Query(Criteria.where("id").is(articles.getIntValue("id"))), Articles.class);

				if (qArticles == null) {
					logger.info("爬取文章:" + articles.getString("title"));
					mongoTemplate.insert(articles, "articles");// 保存冷数据到mongodb

				} else {
					logger.info("文章已存在：(" + urlToken + ")" + articles.getString("title"));
					return;
				}
			}
		} catch (Exception e) {
			if (e.getMessage().indexOf("404") == -1) {
				Constants.errorRqCount++;
				if (Constants.errorRqCount > 5) {
					Constants.errorRqCount = 0;

					// JSONObject jsonObject = new JSONObject();
					// String ipString = CommonHttpClientUtil.getIp(
					// "http://www.jinglingdaili.com/Index-generate_api_url.html?packid=1&qty=1&time=1&pro=%E5%B9%BF%E4%B8%9C%E7%9C%81&city=%E5%B9%BF%E5%B7%9E%E5%B8%82&port=1&format=json&ss=1&css=&dt=1");
					// jsonObject = JSONObject.parseObject(ipString);
					// JSONArray arrays = jsonObject.getJSONArray("data");
					// JSONObject ipJSON = arrays.getJSONObject(0);
					// Constants.host =ipJSON.getString("IP");
					// Constants.post = ipJSON.getIntValue("Port");
					// System.out.println(Constants.host + ":" + Constants.post);
					
					String ipString=CommonHttpClientUtil.getIp("http://120.25.150.39:8081/index.php/api/entry?method=proxyServer.ipinfolist&quantity=&province=%E5%B9%BF%E4%B8%9C%E7%9C%81&city=%E5%B9%BF%E5%B7%9E%E5%B8%82&anonymous=1&ms=1&service=0&protocol=1&wdsy=on&distinct=true&format=json&separator=1&separator_txt=");
					JSONObject data=JSONObject.parseObject(ipString).getJSONObject("data");
					JSONArray ipArray=data.getJSONObject("list").getJSONArray("ProxyIpInfoList");
					JSONObject ipJson=ipArray.getJSONObject(0);
					 Constants.host =ipJson.getString("IP");
					 Constants.post = ipJson.getIntValue("Port");
					 System.out.println(Constants.host + ":" + Constants.post);
				}
			}
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
