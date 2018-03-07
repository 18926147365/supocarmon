/**
 * 
 */
package com.ocarmon.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** 
* @author 李浩铭 
* @since 2018年3月7日 下午2:40:05
*/
@Document(collection="splidered_users")
public class UrlToken {
	@Id
	private String _id;
	
	private String urlToken;

	/**
	 * @return the urlToken
	 */
	public String getUrlToken() {
		return urlToken;
	}

	public void setUrlToken(String urlToken) {
		this.urlToken = urlToken;
	}
	
	
}
