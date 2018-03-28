/**
 * 
 */
package com.ocarmon.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ocarmon.util.ExcelUtil;

/** 
* @author 李浩铭 
* @since 2018年3月28日 上午10:49:01
*/
@RestController
@RequestMapping("utils")
public class UtilsController extends BaseController{
	/**
	 * 上传excel并解析文件
	 * */
	@ResponseBody
	@RequestMapping(value = "/excelUtils", method = RequestMethod.POST)
	public String excelUtils(@RequestParam("excelfile") MultipartFile file) {
		InputStream is = null;
		
		try {
			is = file.getInputStream();
			JSONArray dataList= ExcelUtil.getJSONArray(is);
			return retunSuccess(dataList.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return returnFail("上传异常");
		}finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
