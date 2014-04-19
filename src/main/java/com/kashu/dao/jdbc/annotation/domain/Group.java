package com.kashu.dao.jdbc.annotation.domain;

import java.util.HashSet;
import java.util.Set;

public class Group {
	//native columns in TB_GROUP table
	private Long id;
	private String name;
	
	//associations (NOT exists in TB_GROUP table)
	private Set<User> users = new HashSet<User>();		//many to many
	
	public Group(){
		
	}
	
	public Group(String name){
		this.name = name;
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

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
}
