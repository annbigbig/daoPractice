package com.kashu.dao.jdbc.annotation.domain;

import java.util.Date;

public class Reply {
	//native columns in TB_REPLY table
	private Long id;
	private String content;
	private Date createdTime;
	private Date lastModified;
	private Article article;		//foreign key , column name in table TB_REPLY will be ARTICLE_ID
	private User user;			//foreign key , column name in table TB_REPLY will be USER_ID
	
	public Reply(){
		
	}
	
	// All of the columns that a Reply record must contain
	public Reply(String content, Date createdTime, Date lastModified, Article article, User user){
		this.content = content;
		this.createdTime = createdTime;
		this.lastModified = lastModified;
		this.article = article;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
