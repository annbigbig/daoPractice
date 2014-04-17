package com.kashu.dao.jdbc.annotation.domain;

import java.util.Date;

public class User {
	private Long id;
	private String uuid;
	private String passwd;
	private String firstName;
	private String lastName;
	private String displayName;
	private boolean male;
	private Date birthday;
	private String address;
	private String phone;
	private String mobile;
	private Integer score;
	
	public User() {
		
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
	}private Integer version;	//for optimistic locking

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
	
	public String toString(){
		return "[ " + this.getClass().getSimpleName() + " ] -- [ " + 
					id + " \t " + uuid + "\t" + passwd + "\t" + firstName + "\t" + lastName + "\t" + displayName + "\t" +
					male + "\t" + birthday + "\t" + address + "\t" + phone + "\t" + mobile + "\t" + score + " ]\n";
	}
}
