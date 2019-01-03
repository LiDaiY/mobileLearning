package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ujs.mlearn.dao.StuHomeworkDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.StuHomework;

public class StuHomeworkDaoImpl implements StuHomeworkDao {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());
	@Override
	public List<StuHomework> findStuHwByHwID(int hwID) {
		String sql="select shwID,hwID,subTime,hwUrl,stuWorkTitle,studentID,username from stuhomework,student where hwID=? "
				+ "and stuhomework.`studentID`=student.`userID`";
		try {
			List<StuHomework> sList=runner.query(sql, new BeanListHandler<>(StuHomework.class),hwID);
			for(StuHomework stuHomework:sList) {
				stuHomework.setSubTime(stuHomework.getSubTime().substring(0, 19));
			}
			return sList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void upHomework(StuHomework sHomework) {
		String sql="insert into stuhomework(hwID,subTime,hwUrl,stuWorkTitle,studentID) values(?,?,?,?,?)";
		Object[] params = {sHomework.getHwID(),sHomework.getSubTime(),sHomework.getHwUrl(),sHomework.getStuWorkTitle(),sHomework.getStudentID()};
		try {
			runner.update(sql, params);
			System.out.println("上传作业成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public StuHomework findOneWork(int shwID) {
		String sql="select shwID,hwID,subTime,hwUrl,stuWorkTitle,studentID,username from stuhomework,student where shwID=? "
				+ "and stuhomework.`studentID`=student.`userID`";
		try {
			StuHomework sHomework=runner.query(sql, new BeanHandler<>(StuHomework.class),shwID);
			return sHomework;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	@Override
	public StuHomework findWork(int hwID, int studentID) {
		String sql="select * from stuhomework where hwID=? and studentID=?";
		Object[] params= {hwID,studentID};
		try {
			StuHomework sHomework=runner.query(sql, new BeanHandler<>(StuHomework.class),params);
			return sHomework;
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	@Override
	public void upHomework(StuHomework sHomework, int shwID) {
		String sql="update stuhomework set hwUrl=?,stuWorkTitle=? where shwID=?";
		Object[] params= {sHomework.getHwUrl(),sHomework.getStuWorkTitle(),shwID};
		try {
			runner.update(sql,params);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

}
