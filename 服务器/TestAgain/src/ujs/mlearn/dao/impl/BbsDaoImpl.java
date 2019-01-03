package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.sun.org.apache.bcel.internal.generic.NEW;

import ujs.mlearn.dao.BbsDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.BbsTheme;

public class BbsDaoImpl implements BbsDao {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());
	@Override
	public List<BbsTheme> findNoteByCourseID(int courseID) {

		String sql="SELECT p.*, " + 
				"s.`userName` AS studentName,s.`email` AS studentEmail, " + 
				"COUNT(r.replyID) AS replyCount " + 
				"FROM themepost p " + 
				"LEFT JOIN replypost r ON p.`postID`=r.`postID` " + 
				"LEFT JOIN student s ON s.`userID`=p.`studentID` " + 
				"GROUP BY p.`postID` " + 
				"HAVING p.`courseID`=? " + 
				"ORDER BY postTime DESC";
		try {
			List<BbsTheme> bbsThemes = runner.query(sql,
					new BeanListHandler<BbsTheme>(BbsTheme.class),courseID);
			for(BbsTheme bbsTheme:bbsThemes) {
				bbsTheme.setPostTime(bbsTheme.getPostTime().substring(0, 19));
//				System.out.println(bbsTheme.toString());
			}
			System.out.println("有"+bbsThemes.size()+"条帖子");
			return bbsThemes;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BbsTheme findByID(int postID) {
		String sql="select * from themepost where postID=?";
		try {
			BbsTheme bbsTheme=runner.query(sql, new BeanHandler<BbsTheme>(BbsTheme.class),postID);
			bbsTheme.setPostTime(bbsTheme.getPostTime().substring(0, 19));
			return bbsTheme;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int findCount(int courseID) {
		List<BbsTheme> list=findNoteByCourseID(courseID);
		if (list==null) {
			return 0;
		}
		return list.size();
	}

	@Override
	public void addPost(BbsTheme bbsTheme) {
		System.out.println("准备插入帖子");
		String sql = "insert into themepost (studentID,courseID,postTitle,"
				+ "postContent,postTime,state) "
				+ "values (?,?,?,?,?,?)";
		Object[] params = {bbsTheme.getStudentID(),
				bbsTheme.getCourseID(),bbsTheme.getPostTitle(),
				bbsTheme.getPostContent(),bbsTheme.getPostTime(),bbsTheme.getState(),
				};
		try {
			runner.update(sql, params);
			System.out.println("插入帖子成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	@Override
//	public void upUserName(int userID, String userName) {
//		// TODO Auto-generated method stub
//		String sql="update themepost set studentName=? where studentID=?";
//		Object[] params= {userName,userID};
//		try {
//			runner.update(sql, params);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@Override
	public void stateChange(int flag,int postID) {
		String sql="";
//		sql="SELECT COUNT(replyID) FROM replypost WHERE postID=?";
//		int replyCount=0;
//		try {
//			String s=Arrays.toString(runner.query(sql, new ArrayHandler(),postID));
//			char b=s.charAt(1);
//			s=String.valueOf(b);
//			replyCount=Integer.parseInt(s);
//			System.out.println("有"+replyCount+"条回帖");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if (flag==10) {
			sql="update themepost set state='1' where postID=?";
		}else if (flag==11) {
			sql="update themepost set state='0' where postID=?";
		}
//		Object[] params= {replyCount,postID};
		try {
			runner.update(sql,postID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	@Override
	public void deletePost(int postID) {
		String sql="delete from themepost where postID=?";
		try {
			runner.update(sql, postID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
