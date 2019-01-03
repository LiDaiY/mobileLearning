package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ujs.mlearn.dao.StudentCourseDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.Course;
import ujs.mlearn.entity.StudentCourse;

public class StudentCourseDaoImpl implements StudentCourseDao {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());

	@Override
	public void del(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(StudentCourse sc) {
		// TODO Auto-generated method stub

	}

	@Override
	public StudentCourse find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentCourse> findAll() {
		return null;
	}

	@Override
	public List<StudentCourse> findAll(int id) {
		String sql  = "select * from studentcourse where studentID=?";
		Object[] params = new Object[]{id};

		try {
			List<StudentCourse> sCourses = runner.query(sql,
					new BeanListHandler<StudentCourse>(StudentCourse.class),params);	
			return sCourses;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public StudentCourse find(int courseID, int studentID) {
		String sql  = "select * from studentcourse where courseID=? and studentID=?";
		Object[] params = new Object[]{courseID,studentID};

		try {
			StudentCourse sc = runner.query(sql,
					new BeanHandler<StudentCourse>(StudentCourse.class),params);	
			return sc;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void add(int courseID, int studentID) {
		String sql = "insert into studentcourse (courseID,studentID) values (?,?)";
		Object[] params = {courseID,studentID};
			try {
				runner.update(sql, params);
				System.out.println("插入选课关系成功");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void del(int courseID, int studentID) {
		// TODO Auto-generated method stub
		String sql = "delete from studentcourse where courseID=? and studentID=?";
		Object[] params = {courseID,studentID};
			try {
				runner.update(sql, params);
				System.out.println("删除选课关系成功");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void update(int courseID, int studentID, int studentGrade, String studnetAnswer) {
		String sql = "update studentcourse set studentGrade=?,studentAnswer=? where courseID=? and studentID=?";
		Object[] params = {studentGrade,studnetAnswer,courseID,studentID};
		
			try {
				runner.update(sql, params);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public List<StudentCourse> findByCouresID(int courseID) {
		String sql="select * from studentcourse where courseID=?";
		try {
			List<StudentCourse> sCourses = runner.query(sql,
					new BeanListHandler<StudentCourse>(StudentCourse.class),courseID);
			return sCourses;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int findCountByCourseID(int courseID) {
		List<StudentCourse>	list=findByCouresID(courseID);
		if (list==null) {
			return 0;
		}
		return list.size();
	}
}
