package ujs.mlearn.entity;
/**
 * 学生提交的作业信息类
 * @author 李宗豪
 *
 */
public class StuHomework {
	public int shwID;
	public int courseID;
	public int hwID;
	public String subTime;
	public String hwUrl;
	public String stuWorkTitle;
	public int studentID;
	public String username;
	
	public StuHomework() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StuHomework(int shwID, int courseID, int hwID, String subTime, String hwUrl, String stuWorkTitle, int studentID,
			String studentName) {
		super();
		this.shwID = shwID;
		this.courseID = courseID;
		this.hwID = hwID;
		this.subTime = subTime;
		this.hwUrl = hwUrl;
		this.stuWorkTitle = stuWorkTitle;
		this.studentID = studentID;
		this.username = studentName;
	}

	public int getShwID() {
		return shwID;
	}

	public void setShwID(int shwID) {
		this.shwID = shwID;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public int getHwID() {
		return hwID;
	}

	public void setHwID(int hwID) {
		this.hwID = hwID;
	}

	public String getSubTime() {
		return subTime;
	}

	public void setSubTime(String subTime) {
		this.subTime = subTime;
	}

	public String getHwUrl() {
		return hwUrl;
	}

	public void setHwUrl(String hwUrl) {
		this.hwUrl = hwUrl;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}



	public String getStuWorkTitle() {
		return stuWorkTitle;
	}

	public void setStuWorkTitle(String stuWorkTitle) {
		this.stuWorkTitle = stuWorkTitle;
	}

	@Override
	public String toString() {
		return "StuHomework [shwID=" + shwID + ", courseID=" + courseID + ", hwID=" + hwID + ", subTime=" + subTime
				+ ", hwUrl=" + hwUrl + ", stuWorkTitle=" + stuWorkTitle + ", studentID=" + studentID + ", studentName="
				+ username + "]";
	}


	
	
	
}
