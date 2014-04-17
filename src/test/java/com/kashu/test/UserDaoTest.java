package com.kashu.test;

import java.util.Date;
import java.util.List;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.kashu.dao.Dao;
import com.kashu.dao.jdbc.annotation.domain.User;

public class UserDaoTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:jdbc/annotation/appContext.xml");
		ctx.refresh();

		Dao<User> userDao = ctx.getBean("userDao",Dao.class);
		
		// find all users
		listUsers(userDao.findAll());
		
		// add a new user
		userDao.insert(getUser1());
		
		// find all users
		listUsers(userDao.findAll());
	}

	public static void listUsers(List<User> users){
		for(User user : users){
			System.out.println(user);
		}
	}
	
	public static User getUser1(){
		User user = new User();
		user.setUuid("WaluSadi");
		user.setPasswd("walupasswd");
		user.setFirstName("Wa");
		user.setLastName("LuSadi");
		user.setDisplayName("WLSD");
		user.setMale(true);
		user.setBirthday(new Date());
		user.setAddress("我家就住在那太麻里隔壁");
		user.setPhone("12345678");
		user.setMobile("87654321");
		user.setScore(1200);
		return user;
	}
	
}
