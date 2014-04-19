package com.kashu.dao.jdbc.annotation.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Article {
	//native columns in TB_ARTICLE table
	private Long id;
	private String subject;
	private String content;
	private Date createdTime;
	private Date lastModified;
	private User user;				//foreign key , column name in table TB_ARTICLE will be USER_ID
	private Category category;	//foreign key , column name in table TB_ARTICLE will be CATEGORY_ID
	
	//associations (NOT exists in TB_ARTICLE table)
	private Set<Reply> replies = new HashSet<Reply>();		//one to many
	
	public Article(){
		
	}
	
	// All of the columns that a Article record must contain
	public Article(String subject, String content, Date createdTime, Date lastModified, User user, Category category){
		this.subject = subject;
		this.content = content;
		this.createdTime = createdTime;
		this.lastModified = lastModified;
		this.user = user;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<Reply> getReplies() {
		return replies;
	}

	public void setReplies(Set<Reply> replies) {
		this.replies = replies;
	}
	
}
