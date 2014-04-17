package com.kashu.dao;

import java.util.List;
import java.util.Map;

public interface Dao<T> {

		public List<T> findAll();
		
		public List<T> findByParams(Map<String,Object> params);
		
		public boolean exists(Long id);
		
		public long count();
		
		public long countByParams(Map<String,Object> params);
		
		public T insert(T entity);
		
		public T insertWith(T entity);
		
		public T update(T entity);
		
		public T updateWith(T entity);
		
		public void delete(Long id);
		
		public void delete(T entity);
}
