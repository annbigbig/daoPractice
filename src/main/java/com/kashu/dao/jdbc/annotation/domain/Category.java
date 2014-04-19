package com.kashu.dao.jdbc.annotation.domain;

import java.util.HashSet;
import java.util.Set;

public class Category {
	//native columns in TB_CATEGORY table
	private Long id;
	private String name;
	private Category parent;	//foreign key , column name in table TB_CATEGORY will be PID
	
	//associations
	private Set<Category> children = new HashSet<Category>();
	
	public Category(){
		
	}
	
	// All of the columns that a Category record must contain
	public Category(String name, Category parent){
		this.name = name;
		this.parent = parent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Set<Category> getChildren() {
		return children;
	}

	public void setChildren(Set<Category> children) {
		this.children = children;
	}

}
