package com.kashu.dao.jdbc.annotation.domain;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

public class User {
	//native columns in TB_USER table
	private Long id;
	private String uuid;
	private String passwd;
	private String firstName;
	private String lastName;
	private String displayName;
	private boolean male = true;
	private Date birthday;
	private String address;
	private String phone;
	private String mobile;
	private Integer score = 0;
	
	//associations (NOT exists in TB_USER table)
	private Set<Group> groups = new HashSet<Group>();		//many to many
	private Set<Article> articles = new HashSet<Article>();	//one to many
	
	public User() {
		
	}
	
	// Only the columns that must be NOT NULL
	public User(String uuid, String passwd, String firstName, String lastName, String displayName){
		this.uuid = uuid;
		this.passwd = passwd;
		this.firstName = firstName;
		this.lastName = lastName;
		this.displayName = displayName;
	}
	
	// All of the columns that a User record will contain
	public User(String uuid, String passwd, String firstName, String lastName, String displayName,
			boolean male, Date birthday, String address, String phone, String mobile, Integer score){
		this.uuid = uuid;
		this.passwd = passwd;
		this.firstName = firstName;
		this.lastName = lastName;
		this.displayName = displayName;
		this.male = male;
		this.birthday = birthday;
		this.address = address;
		this.phone = phone;
		this.mobile = mobile;
		this.score = score;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isMale() {
		return male;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	public String toString(){
		return "[ " + this.getClass().getSimpleName() + " ] -- [ " + 
					id + " \t " + uuid + "\t" + passwd + "\t" + firstName + "\t" + lastName + "\t" + displayName + "\t" +
					male + "\t" + birthday + "\t" + address + "\t" + phone + "\t" + mobile + "\t" + score + " ]\n";
	}
}
