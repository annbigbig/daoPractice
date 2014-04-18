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
		System.out.println("[params] uuid = %tony%");
		List<User> users = userDao.findByParams(params);
		listUsers(users);
		
		System.out.println("----------search by firstName");
		params.clear();
		params.put("firstName","%sa%");
		System.out.println("[params] firstName = %sa%");
		users = userDao.findByParams(params);
		listUsers(users);
		
		System.out.println("----------search by lastName");
		params.clear();
		params.put("lastName", "%S%");
		System.out.println("[params] lastName = %S%");
		users = userDao.findByParams(params);
		listUsers(users);
		
		System.out.println("----------search by displayName");
		params.clear();
		params.put("displayName", "%y%");
		System.out.println("[params] displayName = %y%");
		users = userDao.findByParams(params);
		listUsers(users);
		
		System.out.println("----------search by male");
		params.clear();
		params.put("male", true);
		System.out.println("[params] male = true");
		users = userDao.findByParams(params);
		listUsers(users);
		
		System.out.println("----------search by birthday (lt)");
		params.clear();
		//params.put("birthday", getDate("1989-03-15"));
		params.put("birthday", "1989-03-15");
		params.put("operator", "lt");
		System.out.println("[params] birthday = 1989-03-15 , operator = lt");
		users = userDao.findByParams(params);
		listUsers(users);
		
		System.out.println("----------search by birthday (gt)");
		params.clear();
		//params.put("birthday", getDate("1989-03-15"));
		params.put("birthday", "1989-03-15");
		params.put("operator", "gt");
		System.out.println("[params] birthday = 1989-03-15 , operator = gt");
		users = userDao.findByParams(params);
		listUsers(users);
		
		//以日期查詢時， 參數請用String型態，不要用Date型態， 以免發生不可預期的結果（找不到）
		System.out.println("----------search by birthday (eq)");
		params.clear();
		params.put("birthday", "1989-03-15");		//這行找得到
		//params.put("birthday", getDate("1989-03-15"));		//這行找不到
		params.put("operator", "eq");
		System.out.println("[params] birthday = 1989-03-15 , operator = eq");
		users = userDao.findByParams(params);
		listUsers(users);
		
		System.out.println("----------search by birthday (between)");
		params.clear();
		params.put("date1", "1980-01-01");
		params.put("date2", "1989-12-31");
		System.out.println("[params] date1 = 1980-01-01 , date2 = 1989-12-31");
		users = userDao.findByParams(params);
		listUsers(users);
		
		System.out.println("----------search by address");
		params.clear();
		params.put("address", "%某城%");
		System.out.println("[params] address = %某城%");
		users = userDao.findByParams(params);
		listUsers(users);
		
		System.out.println("----------search by phone");
		params.clear();
		params.put("phone", "%23%");
		System.out.println("[params] phone = %23%");
		users = userDao.findByParams(params);
		listUsers(users);
		
		System.out.println("----------search by mobile");
		params.clear();
		params.put("mobile", "%32%");
		System.out.println("[params] mobile = %32%");
		users = userDao.findByParams(params);
		listUsers(users);
		
		System.out.println("----------search by score gt");
		params.clear();
		params.put("score", new Integer(11703));
		params.put("operator", "gt");
		System.out.println("[params] score = 11703 , operator = gt");
		users = userDao.findByParams(params);
		listUsers(users);
		
		System.out.println("----------search by score lt");
		params.clear();
		params.put("score", new Integer(11703));
		params.put("operator", "lt");
		System.out.println("[params] score = 11703 , operator = lt");
		users = userDao.findByParams(params);
		listUsers(users);
		
		System.out.println("----------search by score eq");
		params.clear();
		params.put("score", new Integer(11703));
		params.put("operator", "eq");
		System.out.println("[params] score = 11703 , operator = eq");
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
