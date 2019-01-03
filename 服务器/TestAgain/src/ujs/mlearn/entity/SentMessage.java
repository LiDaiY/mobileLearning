package ujs.mlearn.entity;

public class SentMessage {
	private int code;//code为0表示失败，为1表示成功，为2表示查询
	private String message;
	public SentMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SentMessage(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
