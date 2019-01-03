package com.example.entity;

import java.sql.Date;

public class BbsTheme {
	public int postID;//帖子id
	public int studentID;
	public String studentName;
	public int courseID;
	public String postTitle;//帖子主题
	public String postContent;//帖子内容
	public int replyCount;//回复数量
	public String postTime;//发帖时间
	public String replyTime;//最后回复时间
	public int state;//状态，0表示老师未回复，1表示老师已回复
	public String studentEmail;//学生email，用于加载用户头像

	public BbsTheme() {
	}

	public BbsTheme(int postID, int studentID, String studentName, int courseID, String postTitle, String postContent,
					int replyCount, String postTime, String replyTime, int state, String studentEmail) {
		super();
		this.postID = postID;
		this.studentID = studentID;
		this.studentName = studentName;
		this.courseID = courseID;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.replyCount = replyCount;
		this.postTime = postTime;
		this.replyTime = replyTime;
		this.state = state;
		this.studentEmail = studentEmail;
	}
	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public int getCourseID() {
		return courseID;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public String getPostTime() {
		return postTime;
	}
	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}
	public String getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStudentEmail() {
		return studentEmail;
	}
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	@Override
	public String toString() {
		return "BbsTheme [postID=" + postID + ", studentID=" + studentID + ", studentName=" + studentName
				+ ", courseID=" + courseID + ", postTitle=" + postTitle + ", postContent=" + postContent
				+ ", replyCount=" + replyCount + ", postTime=" + postTime + ", replyTime=" + replyTime + ", state="
				+ state + ", studentEmail=" + studentEmail + "]";
	}
	
	
}
