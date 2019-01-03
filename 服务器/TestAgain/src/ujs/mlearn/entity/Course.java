package ujs.mlearn.entity;

import java.io.Serializable;

public class Course implements Serializable{
	private int courseID;
	private String courseName;
	private String teacherName;
	private String courseUrl;
	private String courseAbstract;
	private int type;
	private int teacherID;
	private String detailInfo;
	
	
	public Course() {
	}



	public Course(int courseID, String courseName, String teacherName, String courseUrl, String courseAbstract,
			int type, int teacherID, String detailInfo) {
		super();
		this.courseID = courseID;
		this.courseName = courseName;
		this.teacherName = teacherName;
		this.courseUrl = courseUrl;
		this.courseAbstract = courseAbstract;
		this.type = type;
		this.teacherID = teacherID;
		this.detailInfo = detailInfo;
	}








	public String getDetailInfo() {
		return detailInfo;
	}








	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}








	public int getTeacherID() {
		return teacherID;
	}



	public void setTeacherID(int teacherID) {
		this.teacherID = teacherID;
	}



	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCourseUrl() {
		return courseUrl;
	}

	public void setCourseUrl(String courseUrl) {
		this.courseUrl = courseUrl;
	}


	public String getCourseAbstract() {
		return courseAbstract;
	}

	public void setCourseAbstract(String courseAbstract) {
		this.courseAbstract = courseAbstract;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}








	@Override
	public String toString() {
		return "Course [courseID=" + courseID + ", courseName=" + courseName + ", teacherName=" + teacherName
				+ ", courseUrl=" + courseUrl + ", courseAbstract=" + courseAbstract + ", type=" + type + ", teacherID="
				+ teacherID + ", detailInfo=" + detailInfo + "]";
	}

	

}
