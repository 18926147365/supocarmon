/**
 * 
 */
package com.ocarmon.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/** 
* @author 李浩铭 
* @since 2018年3月14日 下午2:03:20
*/
public class CommonHttpClientUtil {

	public static String getIp(String url) {
		url = StringUtils.trim(url);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = null;
		String result = null;
		try {
			httpPost = new HttpPost(url);
			httpPost.addHeader("Content ", "text/xml,charset=UTF-8");

			CloseableHttpResponse httpResponse = httpclient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			InputStream responseBodyAsStream = entity.getContent();

			result = IOUtils.toString(responseBodyAsStream, "UTF-8");

			return result;
		} catch (Exception e) {
			if (httpPost != null) {
				httpPost.abort();
			}
			e.printStackTrace();
		} finally {
			try {
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
}
