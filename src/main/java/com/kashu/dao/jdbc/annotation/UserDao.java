package com.kashu.dao.jdbc.annotation;

import java.util.List;

import com.kashu.dao.jdbc.annotation.domain.User;

public interface UserDao {
	public List<User> findAll();
	
	public boolean exists(Long id);
	
	public long count();
	
	public User insert(User user);
	
	public User insertWith(User user);
	
	public User update(User user);
	
	public User updateWith(User user);
	
	public void delete(Long id);
	
	public void delete(User user);
}
