package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ujs.mlearn.dao.NoticeDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.Notice;

public class NoticeDaoImpl implements NoticeDao {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());
	@Override
	public void addNotice(Notice notice) {
		// TODO Auto-generated method stub
		String sql = "insert into notice (noticeTitle,noticeContent,teacherID,courseID,noticeTime,courseName) values (?,?,?,?,?,?)";
		Object[] params = {notice.getNoticeTitle(),notice.getNoticeContent(),
				notice.getTeacherID(),notice.getCourseID(),notice.getNoticeTime(),notice.getCourseName()};
		
		try {
			runner.update(sql, params);
			System.out.println("添加公告成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delNotice(int noticid) {
		String sql = "delete from notice where noticeID = ?";
		
		try {
			runner.update(sql, noticid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateNotice(Notice notice) {
		// TODO Auto-generated method stub
		String sql = "update notice set noticeTitle = ?,noticeContent = ?,noticeTime = ? where noticeID = ?";
		Object[] params = {notice.getNoticeTitle(),notice.getNoticeContent(),notice.getNoticeTime(),notice.getNoticeID()};
		
		try {
			runner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Notice findNotice(int noticeid) {
		String sql = "select * from notice where noticeID = ?";
		
		try {
			Notice notice = runner.query(sql, new BeanHandler<Notice>(Notice.class), noticeid);
			return notice;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Notice> findAllNotice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Notice> findMyNotice(int teacherID) {
		String sql = "select * from notice where teacherID=?";
		Object[] params = {teacherID};
		try {
			List<Notice> notics = runner.query(sql, new BeanListHandler<Notice>(Notice.class),params);
			return notics;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Notice> findCourseNotice(int courseID) {
		String sql = "select * from notice where courseID=? order by noticeTime desc";
		try {
			List<Notice> notics = runner.query(sql, new BeanListHandler<Notice>(Notice.class),courseID);
			return notics;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
