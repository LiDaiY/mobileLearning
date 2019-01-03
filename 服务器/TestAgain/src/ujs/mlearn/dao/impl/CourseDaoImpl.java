package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.Course;
import ujs.mlearn.entity.Student;

public class CourseDaoImpl implements CourseDao {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());
	@Override
	public String add(Course course) {
		if (course == null) {
			throw new IllegalArgumentException();
		} else {
			String sql = "insert into course (courseName,teacherName,courseUrl,courseAbstract,courseType,teacherID,detailInfo) values (?,?,?,?,?,?,?)";
			Object[] params = {course.getCourseName(),course.getTeacherName(),"",course.getCourseAbstract(),1,course.getTeacherID(),
					course.getDetailInfo()};
				try {
					runner.update(sql, params);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			String sql2="select * from course where courseName=? and teacherID=?";
			Object[] params2= {course.getCourseName(),course.getTeacherID()};
			Course course2=null;
			try {
				course2 = runner.query(sql2, 
						new BeanHandler<Course>(Course.class),params2);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println(course2);
			String sql3="update course set courseUrl = ? where courseID = ?";
			String path="res/course/"+course2.getCourseID()+"/cover.jpg";
			Object[] params3= {path,course2.getCourseID()};
			try {
				runner.update(sql3, params3);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return path;
		}
	}

	@Override
	public void del(int id) {

	}

	@Override
	public void update(Course course) {
		String sql="update course set courseName=?,teacherName=?,courseAbstract=?,detailInfo=? where courseID=?";
		Object[] params=new Object[] {course.getCourseName(),course.getTeacherName(),course.getCourseAbstract(),course.getDetailInfo()
				,course.getCourseID()};
		try {
			runner.update(sql,params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Course find(int id) {
		String sql  = "select * from course where courseID=?";
		Object[] params = new Object[]{id};
		try {
			Course course = runner.query(sql, 
					new BeanHandler<Course>(Course.class),params);
			return course;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Course> findAll() {
		String sql  = "select * from course";
		try {
			List<Course> courses = runner.query(sql, new BeanListHandler<Course>(Course.class));
			for(Course course:courses) {
				System.out.println(course.toString());
			}
			return courses;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Course> findMyCourse(int teacherID) {
		String sql  = "select * from course where teacherID=?";
		try {
			List<Course> courses = runner.query(sql, new BeanListHandler<Course>(Course.class),teacherID);
			for(Course course:courses) {
				System.out.println(course.toString());
			}
			return courses;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Course findIdByName(String courseName, int teacherID) {
		String sql  = "select * from course where courseName=? and teacherID=?";
		Object[] params = new Object[]{courseName,teacherID};
		try {
			Course course = runner.query(sql, 
					new BeanHandler<Course>(Course.class),params);
			return course;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


}
