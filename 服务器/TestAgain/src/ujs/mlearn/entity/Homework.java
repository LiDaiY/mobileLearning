package ujs.mlearn.entity;

public class Homework {
	private int hwID;
	private int courseID;
	private int teacherID;
	private String hwContent;
	private String publishTime;
	private String hwTitle;
	
	
	public Homework() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Homework(int hwID, int courseID, int teacherID, String hwContent, String publishTime,String hwTitle) {
		super();
		this.hwID = hwID;
		this.courseID = courseID;
		this.teacherID = teacherID;
		this.hwContent = hwContent;
		this.publishTime = publishTime;
		this.hwTitle=hwTitle;
	}



	public int getHwID() {
		return hwID;
	}



	public void setHwID(int hwID) {
		this.hwID = hwID;
	}



	public int getCourseID() {
		return courseID;
	}



	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}



	public int getTeacherID() {
		return teacherID;
	}



	public void setTeacherID(int teacherID) {
		this.teacherID = teacherID;
	}



	public String getHwContent() {
		return hwContent;
	}



	public void setHwContent(String hwContent) {
		this.hwContent = hwContent;
	}



	public String getPublishTime() {
		return publishTime;
	}



	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}



	public String getHwTitle() {
		return hwTitle;
	}



	public void setHwTitle(String hwTitle) {
		this.hwTitle = hwTitle;
	}



	@Override
	public String toString() {
		return "Homework [hwID=" + hwID + ", courseID=" + courseID + ", teacherID=" + teacherID + ", hwContent="
				+ hwContent + ", publishTime=" + publishTime + ", hwTitle=" + hwTitle + "]";
	}



	
	
	
	
}
