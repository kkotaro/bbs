package model;

public class Contribution {
	private int conId ;
	private String conTitle;
	private String conText;
	private String conCreated;
	private int conUserId;
	private String conUserAccountId;
	private String conUserName;
	private String conReleased;
	private int conNum;
	private int commNum;
	
	public Contribution(int conId, String conTitle, String conText, String conCreated, 
			int conUserId, String conUserAccountId, String conUserName, String conReleased, int commNum){
		this.conId = conId;
		this.conTitle = conTitle;
		this.conText = conText;
		this.conCreated = conCreated;
		this.conUserId = conUserId;
		this.conUserAccountId = conUserAccountId;
		this.conUserName = conUserName;
		this.conReleased = conReleased;
		this.commNum = commNum;
	}
	public Contribution(String conTitle, String conText, int conUserId, String conReleased){
		this.conTitle = conTitle;
		this.conText = conText;
		this.conUserId = conUserId;
		this.conReleased = conReleased;
	}
	public Contribution(int conNum){
		this.conNum = conNum;
	}
	public int getConId(){
		return conId;
	}
	public String getConTitle() {
		return conTitle;
	}
	public String getConText() {
		return conText;
	}
	public String getConCreated() {
		return conCreated;
	}
	public int getConUserId() {
		return conUserId;
	}
	public String getConUserAccountId() {
		return conUserAccountId;
	}
	public String getConUserName() {
		return conUserName;
	}
	public String getConReleased() {
		return conReleased;
	}
	public int getConNum() {
		return conNum;
	}
	public int getCommNum(){
		return commNum;
	}
}
