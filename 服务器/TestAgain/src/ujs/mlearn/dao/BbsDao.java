package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.BbsTheme;

public interface BbsDao {
	/**
	 * 根据课程id找到帖子
	 * @param courseID
	 * @return
	 */
	public List<BbsTheme> findNoteByCourseID(int courseID);
	/**
	 * 根据帖子id找帖子
	 * @param postID
	 * @return
	 */
	public BbsTheme findByID(int postID);
	/**
	 * 根据课程id找到帖子数量
	 * @param courseID
	 * @return
	 */
	public int findCount(int courseID);

	public void addPost(BbsTheme bbsTheme);
	
//	public void upUserName(int userID,String userName);//根据用户id改姓名，用于用户修改姓名时的级联修改
	/**
	 * 修改主题帖的回答数和状态
	 * @param flag 10表示老师回答 11表示老师删除回答 20表示学生回帖 21表示学生删除回帖
	 */
	public void stateChange(int flag,int postID);
	/**
	 * 根据帖子id删除帖子
	 * @param postID
	 */
	public void deletePost(int postID);
	
}
