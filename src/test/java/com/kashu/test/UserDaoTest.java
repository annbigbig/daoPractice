package com.kashu.test;

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
		listUsers(userDao.findAll());
	}

	public static void listUsers(List<User> users){
		for(User user : users){
			System.out.println(user);
		}
	}
}
