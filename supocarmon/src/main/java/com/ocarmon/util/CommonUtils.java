/**
 * 
 */
package com.ocarmon.util;

import java.util.UUID;

/** 
* @author 李浩铭 
* @since 2018年3月30日 下午6:17:57
* 常用的工具类方法
*/
public class CommonUtils {

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();  
        String str = uuid.toString();  
        String temp = str.substring(0, 8) + str.substring(9, 13)  
                + str.substring(14, 18) + str.substring(19, 23)  
                + str.substring(24);  
        return temp;
	}
}
