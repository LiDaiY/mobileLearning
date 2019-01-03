package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ujs.mlearn.dao.TeacherDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.Teacher;
import ujs.mlearn.entity.Student;

public class TeacherDaoImpl implements TeacherDao {
	private QueryRunner runner=new QueryRunner(DataSourceManager.getDataSource());
	@Override
	public void add(Teacher teacher) {
		// TODO Auto-generated method stub

	}

	@Override
	public void del(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Teacher teacher) {
		String sql = "update teacher set email = ?,teacherName=?,password=?,sex=?,signature=? where teacherID = ? ";
		
		//System.out.println(sql);
		Object[] params = {teacher.getEmail(),teacher.getTeacherName(),teacher.getPassword(),teacher.getSex(),teacher.getSignature(),teacher.getTeacherID()};
		
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}

	@Override
	public Teacher find(String email) {
		String sql = "select * from teacher where email = ?";
		
		try {
			Teacher user = runner.query(sql,new BeanHandler<Teacher>(Teacher.class),email);
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Teacher login(String email, String pwd) {
		String sql = "select * from teacher where email = ? and password = ?";
		Object[] params = new Object[]{email,pwd};
		//System.out.println(email+pwd);
		try {
			Teacher teacher = runner.query(sql, new BeanHandler<Teacher>(Teacher.class), params);
			return teacher;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return null;
	}

	@Override
	public List<Teacher> findAll() {
		String sql = "select * from teacher order by logintime desc";
		
		try {
			List<Teacher> users = runner.query(sql,new BeanListHandler<Teacher>(Teacher.class));
			for(Teacher teacher:users) {
				teacher.setLogintime(teacher.getLogintime().substring(0, 19));
			}
			
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Teacher findById(int userid) {
		String sql = "select * from teacher where teacherID = ?";
		
		try {
			Teacher user = runner.query(sql, new BeanHandler<Teacher>(Teacher.class),userid);
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void uduser(String attribute, String value, int userid) {
		String sql = "update teacher set xxx = ? where teacherID = ? ";
		
		sql=sql.replace("xxx",attribute);
		//System.out.println(sql);
		Object[] params = {value,userid};
		
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}

	}

}
