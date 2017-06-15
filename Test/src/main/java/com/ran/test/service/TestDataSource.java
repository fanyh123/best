package com.ran.test.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

public class TestDataSource {
	
	@Autowired
	private DruidDataSource dataSource;
	
	public String execute(){
		try {
			DruidPooledConnection connection = dataSource.getConnection();
			Statement stat = connection.createStatement();
			stat.execute("select count(1) from user_info");
			ResultSet rs = stat.getResultSet();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

}
