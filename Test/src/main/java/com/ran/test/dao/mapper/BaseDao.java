package com.ran.test.dao.mapper;

import java.util.List;
import java.util.Map;

public interface BaseDao<T> {	
	
	public void insert(T t);
	
	public void delete(Map<String,Object> params);
	
	public void update(Map<String,Object> params);
	
	public T get(Map<String,Object> params);
	
	public List<T> getAll();

}
