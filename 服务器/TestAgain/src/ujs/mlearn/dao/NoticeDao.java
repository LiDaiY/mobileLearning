package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.Notice;

public interface NoticeDao {
	public void addNotice(Notice notice);
	
	public void delNotice(int noticid);
	
	public void updateNotice(Notice notice);
	/**
	 * 根据公告的id找公告
	 * @param noticeid
	 * @return
	 */
	public Notice findNotice(int noticeid);
	
	public List<Notice> findAllNotice();
	
	public List<Notice> findMyNotice(int teacherID);
	
	/**
	 * 通过课程id找到这门课所有的通知
	 * @param courseID
	 * @return
	 */
	public List<Notice> findCourseNotice(int courseID);
}
