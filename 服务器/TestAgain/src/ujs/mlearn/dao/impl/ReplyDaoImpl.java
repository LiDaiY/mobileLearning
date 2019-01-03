package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.sun.org.apache.bcel.internal.generic.NEW;

import ujs.mlearn.dao.ReplyDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.ReplyPost;

public class ReplyDaoImpl implements ReplyDao {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());
	@Override
	public void addReply(ReplyPost replyPost) {
		String sql="insert into replypost (postID,userName,replyContent,replyTime,userStatus"
				+ ",starNum,userEmail,userID) values(?,?,?,?,?,?,?,?)";
		Object[] params= {replyPost.getPostID(),replyPost.getUserName(),replyPost.getReplyContent(),
				replyPost.getReplyTime(),replyPost.getUserStatus(),replyPost.getStarNum(),
				replyPost.getUserEmail(),replyPost.getUserID()};
		try {
			runner.update(sql, params);
			System.out.println("回帖成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public List<ReplyPost> findTeacherReply(int teacherID) {
		String sql="select * from replypost where userID=? and userStatus=?";
		Object[] params= {teacherID,1};
		try {
			List<ReplyPost> rList=runner.query(sql,new BeanListHandler<ReplyPost>(ReplyPost.class),params);
			for(ReplyPost replyPost:rList) {
				replyPost.setReplyTime(replyPost.getReplyTime().substring(0, 19));
			}
			return rList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<ReplyPost> findMyReply(int studentID) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<ReplyPost> findPostReply(int postID) {
//		String sql="select * from replypost where postID=? order by userStatus desc";
		String sql="SELECT replyID,postID,replypost.userName,replyContent,replyTime,"
				+ "userStatus,starNum,replypost.userEmail,replypost.userID,"
				+ "signature FROM replypost,student WHERE postID=? "
				+ "AND replypost.`userID`=student.`userID` ORDER BY userStatus DESC"; 
		try {
			List<ReplyPost> replyPosts=runner.query(sql,new BeanListHandler<ReplyPost>(ReplyPost.class),postID);
			for(ReplyPost replyPost:replyPosts) {
				replyPost.setReplyTime(replyPost.getReplyTime().substring(0, 19));
			}
			return replyPosts;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public ReplyPost findOneTeacherReply(int teacherID, int postID) {
		String sql="select * from replypost where userID=? and userStatus='1' and postID=?";
		Object[] params= {teacherID,postID};
		try {
			ReplyPost replyPost=runner.query(sql, new BeanHandler<ReplyPost>(ReplyPost.class),params);
			replyPost.setReplyTime(replyPost.getReplyTime().substring(0, 19));
			return replyPost;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void updateTeacherReply(String replyContent, int replyID) {
		String sql="update replypost set replyContent=? where replyID=?";
		Object[] params= {replyContent,replyID};
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void deleteRep(int replyID) {
		String sql="delete from replypost where replyID=?";
		try {
			runner.update(sql, replyID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
