package ujs.mlearn.entity;
/**
 * 课程资源
 * 包括文本和视频等
 * @author 李宗豪
 *
 */
public class CourseMaterial {
	private int resID;
	private int courseID;
	private String publishTime;
	private String resTitle;
	private String resUrl;
	private int teacherID;
	private long size;
	
	public CourseMaterial() {
		super();
		// TODO Auto-generated constructor stub
	}







	public CourseMaterial(int resID, int courseID, String publishTime, String resTitle, String resUrl, int teacherID,long size) {
		super();
		this.resID = resID;
		this.courseID = courseID;
		this.publishTime = publishTime;
		this.resTitle = resTitle;
		this.resUrl = resUrl;
		this.teacherID = teacherID;
		this.size=size;
	}







	public int getResID() {
		return resID;
	}



	public void setResID(int resID) {
		this.resID = resID;
	}



	public int getCourseID() {
		return courseID;
	}



	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}



	public String getPublishTime() {
		return publishTime;
	}



	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}



	public String getResTitle() {
		return resTitle;
	}



	public void setResTitle(String resTitle) {
		this.resTitle = resTitle;
	}



	public String getResUrl() {
		return resUrl;
	}



	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}



	public int getTeacherID() {
		return teacherID;
	}







	public void setTeacherID(int teacherID) {
		this.teacherID = teacherID;
	}







	public long getSize() {
		return size;
	}







	public void setSize(long size) {
		this.size = size;
	}







	@Override
	public String toString() {
		return "CourseMaterial [resID=" + resID + ", courseID=" + courseID + ", publishTime=" + publishTime
				+ ", resTitle=" + resTitle + ", resUrl=" + resUrl + ", teacherID=" + teacherID + "]";
	}




	
	
	
	
}
