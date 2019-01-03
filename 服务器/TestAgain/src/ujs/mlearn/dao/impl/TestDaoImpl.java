package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ujs.mlearn.dao.TestDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.Test;

public class TestDaoImpl implements TestDao {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());
	@Override
	public void addtest(Test test) {
		System.out.println("开始添加");
		String sql = "insert into test(courseID,teacherID,testContent,testAnswer,testOption) values(?,?,?,?,?)";
		Object[] params = {test.getCourseID(),test.getTeacherID(),test.getTestContent(),
				test.getTestAnswer(),test.getTestOption()};
		try {
			
			runner.update(sql, params);
			System.out.println("添加成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deltest(int testid) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Test> findCourseTest(int courseID) {
		String sql = "select * from test where courseID=?";
		try {
			List<Test> tests = runner.query(sql, new BeanListHandler<Test>(Test.class),courseID);
			return tests;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Test> findAllTest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Test findTest(int testID) {
		// TODO Auto-generated method stub
		String sql = "select * from test where testID = ?";
		try {
			Test test=runner.query(sql, new BeanHandler<Test>(Test.class),testID);
			return test;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateTest(Test test) {
		String sql = "update test set testContent = ?,testAnswer = ?,testOption = ? where testID = ?";
		
		Object[] params = {test.getTestContent(),test.getTestAnswer(),test.getTestOption(),test.getTestID()};
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
