/**
 * 
 */
package com.ocarmon.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ocarmon.entity.User;

/** 
* @author 李浩铭 
* @since 2018年3月30日 下午5:21:08
*/
@RestController()
@RequestMapping("/user")
public class UserController extends BaseController{
	private static final Logger LOGGER = Logger.getLogger(UserController.class);
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	@ResponseBody
	@RequestMapping("/checkUser")
	public String checkUser(String account,String password) {
		Query query=new Query();
		query.addCriteria(Criteria.where("account").is(account).and("password").is(password));
		User user= mongoTemplate.findOne(query, User.class,"user");
		if(user==null) {
			return returnInfo("密码或账号错误", 1, "error");
		}else {
			JSONObject userJson=new  JSONObject();
			userJson.put("type", user.getType());
			userJson.put("headimg", user.getHeadimg());
			userJson.put("name", user.getName());
			user.setPassword("");
			request.getSession().setMaxInactiveInterval(5*60);//会话超时时间5分钟
			request.getSession().setAttribute("user", user);//数据存入session
			return returnInfo(userJson.toString(), 0, "success");
		}
		
	}
}
