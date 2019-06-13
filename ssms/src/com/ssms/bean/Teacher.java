package com.ssms.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 教师类
 * @author bojiangzhou
 *
 */
public class Teacher {
	
	private int id; //ID
	
	private String number; //工号
	
	private String name; //姓名
	
	private String sex; //性别
	
	private List<CourseItem> courseList = new ArrayList<>();
	
	private String[] course = new String[]{}; //课程集合

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public List<CourseItem> getCourseList() {
		return courseList;
	}
	
	public void setCourseList(List<CourseItem> courseList) {
		this.courseList = courseList;
	}

	public String[] getCourse() {
		return course;
	}

	public void setCourse(String[] course) {
		this.course = course;
	}
	
}
