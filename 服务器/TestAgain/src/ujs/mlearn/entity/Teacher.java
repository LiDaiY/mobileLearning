package ujs.mlearn.entity;

public class Teacher {
	
	private int teacherID;
	
	private String teacherName;
	
	private String password;
	
	private String sex;
	
	private String email;
	
	private String photo;
	
	private String signature;
	
	private int type;
	
	private String logintime;

	public int getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(int teacherID) {
		this.teacherID = teacherID;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	

	public String getLogintime() {
		return logintime;
	}

	public void setLogintime(String logintime) {
		if (logintime==null||logintime.equals("")) {
			this.logintime="2018-01-01 00:00:000";
		}else {
			this.logintime = logintime;
		}
	}

	public Teacher() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Teacher(int teacherID, String teacherName, String password, String sex, String email, String photo,
			String signature, int type, String logintime) {
		super();
		this.teacherID = teacherID;
		this.teacherName = teacherName;
		this.password = password;
		this.sex = sex;
		this.email = email;
		this.photo = photo;
		this.signature = signature;
		this.type = type;
		if (logintime==null||logintime.equals("")) {
			this.logintime="2018-01-01 00:00:000";
		}else {
			this.logintime = logintime;
		}
	}

	@Override
	public String toString() {
		return "Teacher [teacherID=" + teacherID + ", teacherName=" + teacherName + ", password=" + password + ", sex="
				+ sex + ", email=" + email + ", photo=" + photo + ", signature=" + signature + ", type=" + type
				+ ", logintime=" + logintime + "]";
	}
	
	
}
