package ujs.mlearn.entity;

public class Student {

	private int userId;
	
	private String username;
	
	private String password;
	
	private String sex;
	
	private String email;
	
	private String photo;
	
	private String signature;
	
	private int type;
	
	private String logintime;
	

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student(int userId, String username, String password, String sex,
			String email, String photo, String signature, int type, String logintime) {
		super();
		this.userId = userId;
		this.username = username;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", sex=" + sex
				+ ", email=" + email + ", photo=" + photo + ", signature=" + signature + ", type=" + type + ", logintime="
				+ logintime + "]";
	}
	
	
}

