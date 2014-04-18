package com.kashu.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
		findAll(userDao);
		
		//insert(userDao);
		
		findByParams(userDao);
		
	}

	public static void findAll(Dao<User> userDao){
		System.out.println("----- find all -----");
		List<User> users = userDao.findAll();
		listUsers(users);
	}
	
	public static void listUsers(List<User> users){
		for(User user : users){
			System.out.println(user);
		}
	}
	
	public static void insert(Dao<User> userDao){
		System.out.println("------insert -----");
		User user = new User();
		user.setUuid("chikabu");
		user.setPasswd("chika-passwd");
		user.setFirstName("Chi");
		user.setLastName("KaBu");
		user.setDisplayName("CKB");
		user.setMale(false);
		user.setBirthday(getDate("1981-12-08"));
		user.setAddress("日本東京都不知火舞市丁小路350號2樓");
		user.setPhone("83143258");
		user.setMobile("11532561");
		user.setScore(11703);
		userDao.insert(user);
		System.out.println("Has inserted a user with id [" + user.getId() + "]");
		System.out.println(user);
	}
	
	public static void findByParams(Dao<User> userDao){
		System.out.println("---- find by params ----");
		Map<String,Object> params = new HashMap<String,Object>();
		
		System.out.println("-----------search by uuid -----------");
		params.put("uuid", "%tony%");
		List<User> users = userDao.findByParams(params);
		listUsers(users);
		
		System.out.println("----------search by firstName");
		params.clear();
		params.put("firstName","%sa%");
		users = userDao.findByParams(params);
		listUsers(users);
		
		System.out.println("----------search by lastName");
		params.clear();
		params.put("lastName", "%S%");
		users = userDao.findByParams(params);
		listUsers(users);
	}
	
	public static Map<String,Object> getParams(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("birthday", getDate("1990-03-15 10:20:56"));
		params.put("operator", "gt");
		return params;
	}
	
	public static Date getDate(String str){
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD hh:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
		Date date = new Date();
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(date); //1973-03-15 10:20:56
		return date;
	}
}
