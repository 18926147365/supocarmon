/**
 * 
 */
package com.ocarmon.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** 
* @author 李浩铭 
* @since 2018年3月30日 下午6:11:53
*/
@Document(collection="files")
public class Files {
	@Id
	private String _id;
	
	private int user_id;//文件的归属编号
	
	private String file_name;//文件名称
	
	private Long file_size;//文件大小
	
	private String suffix;//文件后缀
	
	private Integer type;//文件类型
	
	private String uuid;//文件标识码
	
	private String create_time;
	
	private String update_time;

	/**
	 * @return the user_id
	 */
	public int getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the file_name
	 */
	public String getFile_name() {
		return file_name;
	}

	/**
	 * @param file_name the file_name to set
	 */
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	/**
	 * @return the suffix
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * @param suffix the suffix to set
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/**
	 * @return the file_size
	 */
	public Long getFile_size() {
		return file_size;
	}

	/**
	 * @param file_size the file_size to set
	 */
	public void setFile_size(Long file_size) {
		this.file_size = file_size;
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

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the create_time
	 */
	public String getCreate_time() {
		return create_time;
	}

	/**
	 * @param create_time the create_time to set
	 */
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	/**
	 * @return the update_time
	 */
	public String getUpdate_time() {
		return update_time;
	}

	/**
	 * @param update_time the update_time to set
	 */
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
	
	
	
}
