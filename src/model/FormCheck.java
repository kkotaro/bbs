package model;

public class FormCheck {
	//ユーザ登録、編集サーバ側フォームチェック
	public boolean isCorrectUserForm(String name, String id, String pass, String passCom) {
		if ("".equals(name) || "".equals(id) || "".equals(pass) || "".equals(passCom)){
			return false;
		} else if (!id.matches("[0-9a-zA-Z]+")){ 
			return false;
		}else if (pass.length() < 6 || 255 < pass.length()){ 
			return false;
		} else if (!pass.equals(passCom)){ 
			return false;
		} else if (!pass.matches("(?!^[^A-Za-z]*$)"
				+ "(?!^[^(\\!-\\/|:-@|\\[-`|{-~]*$)^([\\!-~]+)$")){ 
			return false;
		}
		return true;
	}
	//編集サーバ側フォームチェック(パスワード変更無し)
		public boolean isCorrectUserForm(String name, String id) {
			if ("".equals(name) || "".equals(id)){
				return false;
			} else if (!id.matches("[0-9a-zA-Z]+")){ 
				return false;
			}
			return true;
		}
	
	//掲示板書き込みサーバ側フォームチェック
	public boolean isCorrectEntryForm(String title, String text) {
		if (title == "" || title == " " || title == "　"){
			return false;
		} else if (text == "" || text == " " || text== "　") {
			return false;
		} 
		return true;
	}
}
