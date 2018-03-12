/**
 * 
 */
package com.ocarmon.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;

/** 
* @author 李浩铭 
* @since 2018年3月9日 下午4:21:52
*/
@Document 
public class Author {

	private int gender;
	
	private String name;
	
	private String avatarUrl;

	/**
	 * @return the gender
	 */
	public int getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(int gender) {
		this.gender = gender;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the avatarUrl
	 */
	public String getAvatarUrl() {
		return avatarUrl;
	}

	/**
	 * @param avatarUrl the avatarUrl to set
	 */
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Author [gender=" + gender + ", name=" + name + ", avatarUrl=" + avatarUrl + "]";
	}
	
	
}
