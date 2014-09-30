package model;

public class Account {
	private int userId;
	private String userAccountId;
	private String pass;
	private String name;
	private int auth;
	private int condition;
	
	//ログインユーザ情報取得コンストラクタ
	public Account(int userId, String userAccountId, String pass, String name, int auth, int condition) {
		this.userId = userId;
		this.userAccountId = userAccountId;
		this.pass = pass;
		this.name = name;
		this.auth = auth;
		this.condition = condition;
	}
	//ログイン用のコンストラクタ
	public Account(String userAccountId, String pass) {
		this.userAccountId = userAccountId;
		this.pass = pass;
	}
	//ユーザリスト取得のコンストラクタ
	public Account(int userId, String userAccountId, String name, int auth, int condition) {
		this.userId = userId;
		this.userAccountId = userAccountId;
		this.name = name;
		this.auth = auth;
		this.condition = condition;
	}
	
	public boolean isBanned() {
		return this.getCondition() == 1;
	}
	
	public int getUserId() {
		return userId;
	}
	public String getUserAccountId() {
		return userAccountId;
	}
	public String getPass() {
		return pass;
	}
	public String getName() {
		return name;
	}
	public int getAuth() {
		return auth;
	}
	public int getCondition() {
		return condition;
	}
}
