package com.ssms.service;

import com.ssms.bean.Clazz;
import com.ssms.bean.Course;
import com.ssms.bean.Grade;
import com.ssms.bean.Student;
import com.ssms.dao.impl.BaseDaoImpl;
import com.ssms.dao.inter.BaseDaoInter;
import com.ssms.tools.MysqlTool;
import com.ssms.tools.StringTool;
import net.sf.json.JSONArray;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程服务层
 * @author liuzhuojin
 *
 */
public class CourseService {
	
	BaseDaoInter dao = new BaseDaoImpl() {

    };
	
	/**
	 * 获取所有课程
	 * @return
	 */
	public String getCourseList(String gradeid){
		List<Object> list;
		if(StringTool.isEmpty(gradeid)){
			list = dao.getList(Course.class, "SELECT * FROM course");
		} else{
			list = dao.getList(Course.class, 
					"SELECT c.* FROM course c, grade_course gc WHERE c.id=gc.courseid AND gc.gradeid=?", 
					new Object[]{Integer.parseInt(gradeid)});
		}
		//json化
        String result = JSONArray.fromObject(list).toString();
        
        return result;
	}



	/**
	 * 添加课程
	 * @param course
	 */
	public void addCourse(Course course) {
		dao.insert("INSERT INTO course(name) value(?)", new Object[]{course.getName()});
	}

	/**
	 * 删除课程
	 * @param courseid
	 * @throws Exception 
	 */
	public void deleteClazz(int courseid) throws Exception {
		//获取连接
		Connection conn = MysqlTool.getConnection();
		try {
			//开启事务
			MysqlTool.startTransaction();
			//删除成绩表
			dao.deleteTransaction(conn, "DELETE FROM escore WHERE courseid=?", new Object[]{courseid});
			//删除班级的课程和老师的关联
			dao.deleteTransaction(conn, "DELETE FROM clazz_course_teacher WHERE courseid=?", new Object[]{courseid});
			//删除年级与课程关联
			dao.deleteTransaction(conn, "DELETE FROM grade_course WHERE courseid=?",  new Object[]{courseid});
			//最后删除课程
			dao.deleteTransaction(conn, "DELETE FROM course WHERE id=?",  new Object[]{courseid});
			
			//提交事务
			MysqlTool.commit();
		} catch (Exception e) {
			//回滚事务
			MysqlTool.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			MysqlTool.closeConnection();
		}
	}


	public String getStudentList(String courseid) {


		List<Object> list;
		if(StringTool.isEmpty(courseid)){
			list = dao.getList(Student.class, "SELECT * FROM student");
		} else{
			list = dao.getList(Student.class,
					"SELECT * FROM student WHERE gradeid in (SELECT gradeid FROM grade_course WHERE courseid=?)",
					new Object[]{Integer.parseInt(courseid)});
		}
		List<Map<String,String>> mapList =new ArrayList<>();

		for(Object object : list){
			Student student = (Student)object;
			Grade grade = (Grade)dao.getObject(Grade.class,"SELECT * FROM grade WHERE id=?",new Object[]{student.getGradeid()});
			Clazz clazz = (Clazz)dao.getObject(Clazz.class,"SELECT * FROM clazz WHERE id=?",new Object[]{student.getClazzid()});
			Map<String,String> map = new HashMap<>();
			map.put("number", student.getNumber());
			map.put("name", student.getName());
			map.put("clazz", clazz.getName());
			map.put("grade", grade.getName());
			mapList.add(map);
		}
		//json化
		String result = JSONArray.fromObject(mapList).toString();
		return result;
	}
}
