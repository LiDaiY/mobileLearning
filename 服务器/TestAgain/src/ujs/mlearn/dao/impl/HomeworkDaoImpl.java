package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.sun.org.apache.bcel.internal.generic.NEW;

import ujs.mlearn.dao.HomeworkDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.Homework;

public class HomeworkDaoImpl implements HomeworkDao {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());
	@Override
	public void addHomework(Homework homework) {
		System.out.println("开始添加");
		String sql = "insert into homework(courseID,teacherID,hwContent,publishTime) values(?,?,?,?)";
		Object[] params = {homework.getCourseID(),homework.getTeacherID(),homework.getHwContent(),
				homework.getPublishTime()};
		try {
			
			runner.update(sql, params);
			System.out.println("添加成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<Homework> findHwByCourseID(int courseID) {
		String sql="select * from homework where courseID=?";

		try {
			List<Homework> hList=runner.query(sql, new BeanListHandler<>(Homework.class),courseID);
			for(Homework homework:hList) {
				homework.setPublishTime(homework.getPublishTime().substring(0, 19));
			}
			return hList;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateHomework(Homework homework) {
		String sql="update homework set hwContent=?,hwTitle=? where hwID=?";
		Object[] params= {homework.getHwContent(),homework.getHwTitle(),homework.getHwID()};
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Override
	public void deleteHomework(int hwID) {
		// TODO Auto-generated method stub

	}

	@Override
	public Homework findHwByHwID(int hwID) {
		String sql="select * from homework where hwID=?";
		try {
			Homework homework=runner.query(sql, new BeanHandler<>(Homework.class),hwID);
			homework.setPublishTime(homework.getPublishTime().substring(0, 19));
			return homework;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
