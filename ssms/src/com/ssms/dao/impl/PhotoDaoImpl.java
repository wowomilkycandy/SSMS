package com.ssms.dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ssms.bean.User;
import com.ssms.dao.inter.PhotoDaoInter;
import com.ssms.tools.MysqlTool;

public class PhotoDaoImpl implements PhotoDaoInter {

	public void setPhoto(User user, InputStream is) throws Exception {
		Connection conn = MysqlTool.getConnection();
		String sql = "";
		if(user.getType() == User.USER_STUDENT){ //学生
			sql = "UPDATE student SET photo=? WHERE number=?";
		} else if(user.getType() == User.USER_TEACHER){ //老师
			sql = "UPDATE teacher SET photo=? WHERE number=?";
		}
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setBinaryStream(1, is, is.available());
		ps.setString(2, user.getAccount());
		ps.execute();
		
		MysqlTool.close(ps);
		MysqlTool.closeConnection();
	}

	public InputStream getPhoto(User user) {
		Connection conn = MysqlTool.getConnection();
		InputStream is = null;
		String sql = "";
		if(user.getType() == User.USER_STUDENT){ //学生
			sql = "SELECT photo FROM student WHERE number=?";
		} else if(user.getType() == User.USER_TEACHER){ //老师
			sql = "SELECT photo FROM teacher WHERE number=?";
		}
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getAccount());
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				is = rs.getBinaryStream(1);
			}
			MysqlTool.close(ps);
			MysqlTool.close(rs);
			MysqlTool.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;
	}
	
}
