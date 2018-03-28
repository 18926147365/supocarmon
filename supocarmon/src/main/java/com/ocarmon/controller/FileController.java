/**
 * 
 */
package com.ocarmon.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.ocarmon.util.ExcelUtil;

/** 
* @author 李浩铭 
* @since 2018年3月28日 上午10:13:04
*/
@RestController()
@RequestMapping("/file")
public class FileController {

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(@RequestParam("file") MultipartFile file) {
		InputStream is = null;
		
		
	}
	
	@RequestMapping(value = "/readExcel", method = RequestMethod.POST)
	public void readExcel(@RequestParam("excelfile") MultipartFile file) {
		InputStream is = null;
		try {
			is = file.getInputStream();
			JSONArray dataList= ExcelUtil.getJSONArray(is);
			for (Object object : dataList) {
				System.out.println(object);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
