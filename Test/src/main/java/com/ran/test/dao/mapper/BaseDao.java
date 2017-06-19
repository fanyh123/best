package com.ran.test.dao.mapper;

import java.util.Map;

public interface BaseDao {
	
	
	public <T> void insert(T t);
	
	public void delete(Map<String,Object> params);
	
	public void update(Map<String,Object> params);
	
	public <T> T get(Map<String,Object> params);	

}
