/**
 * 
 */
package com.ocarmon.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ocarmon.entity.Bigangle;

/** 
* @author 李浩铭 
* @since 2018年3月19日 下午4:18:46
*/
@RestController()
@RequestMapping("/bigangle")
public class BigangleController {
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@RequestMapping("log")
	public void log(String ip,int type,HttpServletResponse response,HttpServletRequest request) {
		String requestIp=getIpAdrress(request);
		System.out.println(requestIp);
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM:dd HH:mm:ss");
		String createTime=sdf.format(new Date());
		Bigangle bigangle=new Bigangle();
		bigangle.setCreateTime(createTime);
		bigangle.setType(type);
		bigangle.setIp(ip);
		mongoTemplate.save(bigangle);
	}
	 /**
     * 获取Ip地址
     * @param request
     * @return
     */
    private static String getIpAdrress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }
	
}
