/**
 * 
 */
package com.ocarmon.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** 
* @author 李浩铭 
* @since 2018年3月6日 下午5:07:28
* 文章
*/
@Document(collection="articles")
public class Articles {
	@Id
	private String _id;
	
	private Long id;
	
	private String title;
	
	private String created;
	
	private String updated;
	
	private String commentPermission;
	
	private String isNormal;
	
	private int voting;
	
	private String excerptTitle;
	
	private int voteupCount;
	
	private String excerpt;
	
	private String type;
	
	private Long commentCount;
	
	private String url;
	
	private String content;
	
	private String imageUrl;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the updated
	 */
	public String getUpdated() {
		return updated;
	}

	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(String updated) {
		this.updated = updated;
	}

	/**
	 * @return the commentPermission
	 */
	public String getCommentPermission() {
		return commentPermission;
	}

	/**
	 * @param commentPermission the commentPermission to set
	 */
	public void setCommentPermission(String commentPermission) {
		this.commentPermission = commentPermission;
	}

	/**
	 * @return the isNormal
	 */
	public String getIsNormal() {
		return isNormal;
	}

	/**
	 * @param isNormal the isNormal to set
	 */
	public void setIsNormal(String isNormal) {
		this.isNormal = isNormal;
	}

	/**
	 * @return the voting
	 */
	public int getVoting() {
		return voting;
	}

	/**
	 * @param voting the voting to set
	 */
	public void setVoting(int voting) {
		this.voting = voting;
	}

	/**
	 * @return the excerptTitle
	 */
	public String getExcerptTitle() {
		return excerptTitle;
	}

	/**
	 * @param excerptTitle the excerptTitle to set
	 */
	public void setExcerptTitle(String excerptTitle) {
		this.excerptTitle = excerptTitle;
	}

	/**
	 * @return the voteupCount
	 */
	public int getVoteupCount() {
		return voteupCount;
	}

	/**
	 * @param voteupCount the voteupCount to set
	 */
	public void setVoteupCount(int voteupCount) {
		this.voteupCount = voteupCount;
	}

	/**
	 * @return the excerpt
	 */
	public String getExcerpt() {
		return excerpt;
	}

	/**
	 * @param excerpt the excerpt to set
	 */
	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the commentCount
	 */
	public Long getCommentCount() {
		return commentCount;
	}

	/**
	 * @param commentCount the commentCount to set
	 */
	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Articles [_id=" + _id + ", id=" + id + ", title=" + title + ", created=" + created + ", updated="
				+ updated + ", commentPermission=" + commentPermission + ", isNormal=" + isNormal + ", voting=" + voting
				+ ", excerptTitle=" + excerptTitle + ", voteupCount=" + voteupCount + ", excerpt=" + excerpt + ", type="
				+ type + ", commentCount=" + commentCount + ", url=" + url + ", content=" + content + ", imageUrl="
				+ imageUrl + "]";
	}
	
	
}
