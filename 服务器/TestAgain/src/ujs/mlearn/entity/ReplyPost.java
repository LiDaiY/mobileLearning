package ujs.mlearn.entity;
/**
 * 回帖
 * @author 李宗豪
 *
 */
public class ReplyPost {
	private int replyID;
	private int postID;
	private String userName;
	private String replyContent;
	private String replyTime;
	private int userStatus;//用户身份0表示学生，1表示老师
	private int starNum=0;//赞数，默认为0
	private String userEmail;//用户邮件，唯一的
	private int userID;//用户id，老师和学生可能一样，需要用身份辅助判断
	private String signature="";//用户的签名，老师没有签名
	
	
	public ReplyPost() {
		super();
	}

	



	public ReplyPost(int replyID, int postID, String userName, String replyContent, String replyTime, int userStatus,
			int starNum, String userEmail, int userID, String signature) {
		super();
		this.replyID = replyID;
		this.postID = postID;
		this.userName = userName;
		this.replyContent = replyContent;
		this.replyTime = replyTime;
		this.userStatus = userStatus;
		this.starNum = starNum;
		this.userEmail = userEmail;
		this.userID = userID;
		this.signature = signature;
	}





	public int getReplyID() {
		return replyID;
	}



	public void setReplyID(int replyID) {
		this.replyID = replyID;
	}



	public int getPostID() {
		return postID;
	}



	public void setPostID(int postID) {
		this.postID = postID;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getReplyContent() {
		return replyContent;
	}



	public void setReplyContent(String replyContent) {
		this.replyContent=replyContent;
	}



	public String getReplyTime() {
		return replyTime;
	}



	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}



	public int getUserStatus() {
		return userStatus;
	}



	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}



	public int getStarNum() {
		return starNum;
	}



	public void setStarNum(int starNum) {
		this.starNum = starNum;
	}



	public String getUserEmail() {
		return userEmail;
	}



	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}



	public int getUserID() {
		return userID;
	}



	public void setUserID(int userID) {
		this.userID = userID;
	}



	public String getSignature() {
		return signature;
	}





	public void setSignature(String signature) {
		this.signature = signature;
	}





	@Override
	public String toString() {
		return "ReplyPost [replyID=" + replyID + ", postID=" + postID + ", userName=" + userName + ", replyContent="
				+ replyContent + ", replyTime=" + replyTime + ", userStatus=" + userStatus + ", starNum=" + starNum
				+ ", userEmail=" + userEmail + ", userID=" + userID + ", signature=" + signature + "]";
	}




	
	
	
	
}
