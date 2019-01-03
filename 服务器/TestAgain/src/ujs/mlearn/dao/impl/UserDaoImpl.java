package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ujs.mlearn.dao.UserDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.ReplyPost;
import ujs.mlearn.entity.Student;

public class UserDaoImpl implements UserDao {
	private QueryRunner runner=new QueryRunner(DataSourceManager.getDataSource());
	@Override
	public void add(Student student) {
		System.out.println("准备插入用户");
		if (student == null) {
			throw new IllegalArgumentException();
		} else {
			String sql = "insert into student (username,password,sex,email,photo,signature,type,logintime) values (?,?,?,?,?,?,?,?)";
			Object[] params = {student.getUsername(),student.getPassword(),"男",student.getEmail(),student.getPhoto(),student.getSignature(),student.getType(),student.getLogintime()};
			
				try {
					runner.update(sql, params);
					System.out.println("插入用户成功");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	@Override
	public void del(int id) {

	}

	@Override
	public void update(Student user) {
		String sql = "update student set email = ?,username=?,password=?,sex=?,signature=? where userID = ? ";
		
		//System.out.println(sql);
		Object[] params = {user.getEmail(),user.getUsername(),user.getPassword(),user.getSex(),user.getSignature(),user.getUserId()};
		
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}

	@Override
	public Student find(String email) {
		String sql = "select * from student where email = ?";
		
		try {
			Student user = runner.query(sql,new BeanHandler<Student>(Student.class),email);
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Student login(String email, String pwd) {
		String sql = "select * from student where email = ? and password = ?";
		Object[] params = new Object[]{email,pwd};
		//System.out.println(email+pwd);
		try {
			Student user = runner.query(sql, new BeanHandler<Student>(Student.class), params);
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return null;
	}

	@Override
	public List<Student> findAll() {
		String sql = "select * from student order by logintime desc";
		
		try {
			List<Student> users = runner.query(sql,new BeanListHandler<Student>(Student.class));
			for(Student student:users) {
				student.setLogintime(student.getLogintime().substring(0, 19));
			}
			
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Student findById(int userid) {
		String sql = "select * from student where userID = ?";
		
		try {
			Student user = runner.query(sql, new BeanHandler<Student>(Student.class),userid);
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void uduser(String attribute, String value, int userid) {
		String sql = "update student set xxx = ? where userID = ? ";
		
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
