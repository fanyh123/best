package com.ran.test.entity;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 8915594952106299369L;
	
	private Long id;
	private String name;
	private String sex;
	private String age;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String name, String sex, String age) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
	}
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", sex=" + sex + ", age=" + age + "]";
	}
	

}
