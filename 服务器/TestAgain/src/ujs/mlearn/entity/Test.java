package ujs.mlearn.entity;

public class Test {
	private int testID;
	private int courseID;
	private int teacherID;
	private String testContent;
	private String testAnswer;
	private String testOption;
	public Test(int testID, int courseID, int teacherID, String testContent, String testAnswer, String testOption) {
		super();
		this.testID = testID;
		this.courseID = courseID;
		this.teacherID = teacherID;
		this.testContent = testContent;
		this.testAnswer = testAnswer;
		this.testOption = testOption;
	}
	public Test() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getTestID() {
		return testID;
	}
	public void setTestID(int testID) {
		this.testID = testID;
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
	public String getTestContent() {
		return testContent;
	}
	public void setTestContent(String testContent) {
		this.testContent = testContent;
	}
	public String getTestAnswer() {
		return testAnswer;
	}
	public void setTestAnswer(String testAnswer) {
		this.testAnswer = testAnswer;
	}
	public String getTestOption() {
		return testOption;
	}
	public void setTestOption(String testOption) {
		this.testOption = testOption;
	}
	@Override
	public String toString() {
		return "Test [testID=" + testID + ", courseID=" + courseID + ", teacherID=" + teacherID + ", testContent="
				+ testContent + ", testAnswer=" + testAnswer + ", testOption=" + testOption + "]";
	}
	
	
}
