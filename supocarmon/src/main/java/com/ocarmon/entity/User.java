/**
 * 
 */
package com.ocarmon.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** 
* @author 李浩铭 
* @since 2018年3月30日 下午5:21:47
*/
@Document(collection="user")
public class User {
	@Id
	private String _id;

	
	private Integer id;
	
	private String account;
	
	private String password;
	
	private String name;
	
	private String headimg;
	
	private Integer type;//账号类型
	
	

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the headimg
	 */
	public String getHeadimg() {
		return headimg;
	}

	/**
	 * @param headimg the headimg to set
	 */
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", account=" + account + ", password=" + password + ", name=" + name + ", headimg="
				+ headimg + ", type=" + type + "]";
	}
	
	
}
