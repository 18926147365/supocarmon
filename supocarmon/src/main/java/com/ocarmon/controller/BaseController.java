/**
 * 
 */
package com.ocarmon.controller;

import com.alibaba.fastjson.JSONObject;

/** 
* @author 李浩铭 
* @since 2018年3月9日 下午3:44:02
* 请求响应处理类
*/
public class BaseController {

	
	/**
	 * 返回格式
	 * {"code":code,"msg":msg,"info":info}
	 * */
	public String returnInfo(String info,int code,String msg) {
		JSONObject json=new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		json.put("info", info);
		return json.toString();
	}
	
	
	/**
	 * 返回格式
	 * {"code":-1,"msg":"fail","info":info}
	 * */
	public String returnFail(String info) {
		JSONObject json=new JSONObject();
		json.put("code", -1);
		json.put("msg", "fail");
		json.put("info", info);
		return json.toString();
	}
	/**
	 * 返回格式
	 * {"code":-1,"msg":msg,"info":info}
	 * */
	public String returnFail(String info,String msg) {
		JSONObject json=new JSONObject();
		json.put("code", -1);
		json.put("msg", msg);
		json.put("info", info);
		return json.toString();
	}

	/**
	 * 返回格式
	 * {"code":0,"msg":msg,"info":info}
	 * */
	public String retunSuccess(String info) {
		JSONObject json=new JSONObject();
		json.put("code", 0);
		json.put("msg", "success");
		json.put("info", info);
		return json.toString();
	}
	
	/**
	 * 返回格式
	 * {"code":0,"msg":msg,"info":info}
	 * */
	public String retunSuccess(String info,String msg) {
		JSONObject json=new JSONObject();
		json.put("code", 0);
		json.put("msg", msg);
		json.put("info", info);
		return json.toString();
	}
	
	
}
