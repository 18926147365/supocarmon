/**
 * 
 */
package com.ocarmon.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ocarmon.entity.Files;
import com.ocarmon.entity.User;
import com.ocarmon.util.CommonUtils;
import com.ocarmon.util.ExcelUtil;
import com.ocarmon.util.OSSClientUtil;

/** 
* @author 李浩铭 
* @since 2018年3月28日 上午10:49:01
*/
@RestController
@RequestMapping("utils")
public class UtilsController extends BaseController{
	private static final Logger LOGGER = Logger.getLogger(UtilsController.class);
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private MongoTemplate mongoTemplate;
	/**
	 * 上传我的临时文件
	 * */
	@RequestMapping(value = "/uploadMyFile", method = RequestMethod.POST)
	public String uploadMyFile(@RequestParam("uploadMyFile") MultipartFile file) {
		//此处做用户验证
		User user=(User) request.getSession().getAttribute("user");
		if(user==null ) {
			return returnInfo("请登录！", 2, "user is not login");
		}else if(user.getType()!=1) {
			return returnInfo("权限不足", 3, "Insufficient authority");
		}
		OSSClientUtil ossClientUtil=new OSSClientUtil("supocarmon/temporary_file/18926147365/");
		try {
			String uuid=CommonUtils.getUUID();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String originalFilename=file.getOriginalFilename();
			String fileName="none";
			String suffix="file";
			//判断后缀
			if(originalFilename.indexOf(".")!=-1) {
				String[] fileNames=originalFilename.split("\\.");
				suffix=fileNames[1];
				fileName=fileNames[0];
			}
			Long fileSize=file.getSize();
			LOGGER.info("uuid:"+uuid+" 文件上传:"+originalFilename+" 大小:"+fileSize);
			Files files=new Files();
			files.setFile_name(fileName);
			files.setUser_id(1);
			files.setFile_size(fileSize);
			files.setCreate_time(sdf.format(new Date()));
			files.setUuid(uuid);
			files.setSuffix(suffix);
			files.setType(1);
			mongoTemplate.save(files);
			ossClientUtil.uploadFile2OSS(file.getInputStream(), uuid+"."+suffix);
			return returnInfo("上传成功", 0, "success");
		} catch (Exception e) {
			ossClientUtil.destory();
			e.printStackTrace();
			return returnInfo("系统异常", 1, "error");
		}
		
	}
	
	
	
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
