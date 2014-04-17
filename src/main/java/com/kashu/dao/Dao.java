package com.kashu.dao;

import java.util.List;

public interface Dao<T> {

		public List<T> findAll();
		
		public boolean exists(Long id);
		
		public long count();
		
		public T insert(T entity);
		
		public T insertWith(T entity);
		
		public T update(T entity);
		
		public T updateWith(T entity);
		
		public void delete(Long id);
		
		public void delete(T entity);
}
