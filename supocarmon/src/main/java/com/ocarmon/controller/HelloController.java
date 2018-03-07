/**
 * 
 */
package com.ocarmon.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
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
import com.ocarmon.entity.Articles;
import com.ocarmon.service.RedisService;
import com.ocarmon.service.SpliderService;
import com.ocarmon.util.HttpClientUtil;

/**
 * @author 李浩铭
 * @since 2018年3月6日 上午9:50:57
 */
@RestController
public class HelloController {

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

	@RequestMapping("save")
	public void save() {
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String url = "https://www.zhihu.com/people/" + "qing-ke-85-7" + "/posts";
		String content;
		try {
			content = (httpClientUtil.getWebPage(url));
			Document doc = Jsoup.parse(content);
			String jsonurl = doc.select("[data-state]").first().attr("data-state");
			JSONObject jsonObject = JSONObject.parseObject(jsonurl);
			JSONObject articlesJSON = jsonObject.getJSONObject("entities").getJSONObject("articles");
			Set<String> set = articlesJSON.keySet();
			JSONObject articles = new JSONObject();
			for (String key : set) {
				articles = articlesJSON.getJSONObject(key);
				mongoTemplate.insert(articles, "articles");
			}

		} catch (IOException e) {
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
					spliderService.start();
				}
			}, 0, 2, TimeUnit.SECONDS);
		}
	}

	@RequestMapping("hello")
	public void hello() {
		Queue<String> queue = new LinkedList<String>();
		queue.add("ou-ba-81-97");
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
