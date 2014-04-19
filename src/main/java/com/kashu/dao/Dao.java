package com.kashu.dao;

import java.util.List;
import java.util.Map;

public interface Dao<T> {

		public T queryOne(Long id);
	
		public List<T> queryAll();
		
		public List<T> queryList(Map<String,Object> params);
		
		public T queryOneWith(Long id);
		
		public List<T> queryAllWith();
		
		public List<T> queryListWith(Map<String,?> params);
		
		public boolean exists(Long id);
		
		public long count();
		
		public long countList(Map<String,Object> params);
		
		public T insert(T entity);
		
		public T insertWith(T entity);
		
		public T update(T entity);
		
		public T updateWith(T entity);
		
		public void delete(Long id);
		
		public void delete(T entity);
}
