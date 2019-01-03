package ujs.mlearn.entity;

public class Notice {
	private int noticeID;
	private int teacherID;
	private int courseID;
	private String noticeTitle;
	private String noticeContent;
	private String noticeTime;
	private String courseName;
	

	public Notice(int noticeID, int teacherID, int courseID, String noticeTitle, String noticeContent,
			String noticeTime, String courseName) {
		super();
		this.noticeID = noticeID;
		this.teacherID = teacherID;
		this.courseID = courseID;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.noticeTime = noticeTime;
		this.courseName = courseName;
	}
	public Notice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getNoticeID() {
		return noticeID;
	}
	public void setNoticeID(int noticeID) {
		this.noticeID = noticeID;
	}
	public int getTeacherID() {
		return teacherID;
	}
	public void setTeacherID(int teacherID) {
		this.teacherID = teacherID;
	}
	public int getCourseID() {
		return courseID;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public String getNoticeTime() {
		return noticeTime;
	}
	public void setNoticeTime(String noticeTime) {
		this.noticeTime = noticeTime;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	
	
	
	
}
