/**
 * 
 */
package com.ocarmon.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.alibaba.fastjson.JSONArray;

/**
 * @author 李浩铭
 * @since 2018年3月28日 上午10:34:07 excel操作工具类
 */
public class ExcelUtil {

	/**
	 * @param 输入流
	 * @return JSONArray
	 */
	public static JSONArray getJSONArray(InputStream is) {
		
		HSSFSheet sheet;
		HSSFWorkbook book;
		JSONArray dataArray = new JSONArray();
		try {
			book = new HSSFWorkbook(is);
			// 获得第一个工作表对象( ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheetAt(0);
			// 数据集合
			// 获取行数
			int size = sheet.getLastRowNum()+1;
			for (int i = 0; i < size; i++) {
				HSSFRow row = sheet.getRow(i);
				HSSFCell cell = row.getCell(0);
				if (cell != null) {
					JSONArray array = new JSONArray();
					for (int k = 0; k < row.getLastCellNum(); k++) {
						row.getCell(k).setCellType(Cell.CELL_TYPE_STRING);
						array.add(row.getCell(k).getStringCellValue());
					}
					dataArray.add(array);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return dataArray;
	}

	/**
	 * @param 输入流
	 * @return List集合
	 */
	public static List<List<String>> getList(InputStream is) {
		HSSFSheet sheet;
		HSSFWorkbook book;
		List<List<String>> dataList = new ArrayList<>();
		try {
			book = new HSSFWorkbook(is);
			// 获得第一个工作表对象( ecxel中sheet的编号从0开始,0,1,2,3,....)
			sheet = book.getSheetAt(0);
			// 数据集合
			// 获取行数
			int size = sheet.getLastRowNum();
			for (int i = 0; i < size; i++) {
				HSSFRow row = sheet.getRow(i);
				HSSFCell cell = row.getCell(0);
				if (cell != null) {
					List<String> data = new ArrayList<>();
					for (int k = 0; k < row.getLastCellNum(); k++) {
						row.getCell(k).setCellType(Cell.CELL_TYPE_STRING);
						data.add(row.getCell(k).getStringCellValue());
					}
					dataList.add(data);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return dataList;
	}
}
