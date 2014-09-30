package model;

public class Comment {
	private int commId;
	private String commText;
	private String commCreated;
	private int conId;
	private int commUserId;
	private String commUserAccountId;
	private String commUserName;

	
	public Comment(int commId, String commText, String commCreated, int conId, String commUserAccountId, String commUserName){
		this.commId = commId;
		this.commText = commText;
		this.commCreated = commCreated;
		this.conId = conId;
		this.commUserAccountId = commUserAccountId;
		this.commUserName = commUserName;
	}
	public Comment(String commText, int conId, int commUserId){
		this.commText = commText;
		this.conId = conId;
		this.commUserId = commUserId;
	}
	public int getCommId(){
		return commId;
	}
	public String getCommText() {
		return commText;
	}
	public String getCommCreated() {
		return commCreated;
	}
	public int getConId(){
		return conId;
	}
	public String getCommUserAccountId(){
		return commUserAccountId;
	}
	public String getCommUserName() {
		return commUserName;
	}
	public int getCommUserId() {
		return commUserId;
	}
}