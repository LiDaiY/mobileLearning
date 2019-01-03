package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.ReplyPost;

public interface ReplyDao {
	/**
	 * 回帖
	 * @param replyPost
	 */
	public void addReply(ReplyPost replyPost);
	/**
	 * 找到老师的所有回答
	 * @param teacherID
	 * @return
	 */
	public List<ReplyPost> findTeacherReply(int teacherID);
	/**
	 * 用户的所有回帖
	 * @param studentID
	 * @return
	 */
	public List<ReplyPost> findMyReply(int studentID);
	/**
	 * 找到主题帖下的所有回复
	 * @param postID
	 * @return
	 */
	public List<ReplyPost> findPostReply(int postID);
	/**
	 * 找到老师的某一个回答
	 * @param teacherID
	 * @param postID
	 * @return
	 */
	public ReplyPost findOneTeacherReply(int teacherID,int postID);
	/**
	 * 修改老师的回答
	 * @param teacherID
	 * @param replyID
	 */
	public void updateTeacherReply(String replyContent,int replyID);
	/**
	 * 删除帖子
	 * @param replyID
	 */
	public void deleteRep(int replyID); 
}
