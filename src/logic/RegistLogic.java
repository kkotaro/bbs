package logic;

import java.security.NoSuchAlgorithmException;

import dao.AccountDAO;
import model.FormCheck;
import model.HashPass;

public class RegistLogic {
	public String excuteRegist(String userName, String userAccountId, String userAccountPass, String userAccountPassCom , int auth) {
		//フォームチェック
		FormCheck fc = new FormCheck();
		boolean fcResult = fc.isCorrectUserForm(userName, userAccountId, userAccountPass, userAccountPassCom);
		if (fcResult) {
			//登録インスタンス
			AccountDAO dao = new AccountDAO();
			//ユーザIDに重複が無いかチェック
			boolean isExistUserAccountId = dao.checkUserIdRegist(userAccountId);
			if (!isExistUserAccountId){
				//重複しない時、登録
				//受け取ったパスワードを暗号化
				String encryptionUserAccountPass = null; 
				HashPass sha = new HashPass();
				try {
					encryptionUserAccountPass = sha.encryptionSha(userAccountPass);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				dao.addUser(userAccountId, encryptionUserAccountPass, userName, auth);
				return "true";
			} else {
				return "userIdRepeated";
			}
		}
		//失敗時
		return "false";
	}
	
	public String exuteEdit(String userName, String userAccountId, String userAccountPass, String userAccountPassCom , int auth, int userId){
		//フォームチェック
		FormCheck fc = new FormCheck();
		boolean fcResult = fc.isCorrectUserForm(userName, userAccountId, userAccountPass, userAccountPassCom);
		if (fcResult) {
			//登録インスタンス
			AccountDAO dao = new AccountDAO();
			//ユーザIDに重複が無いかチェック
			boolean isExistUserAccountId = dao.checkUserIdRegistEdit(userId, userAccountId);
			if (!isExistUserAccountId){
				//重複しない時、登録
				//受け取ったパスワードを暗号化
				String encryptionUserAccountPass = null; 
				HashPass sha = new HashPass();
				try {
					encryptionUserAccountPass = sha.encryptionSha(userAccountPass);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				dao.chengeUserInfomation(userId,userAccountId, encryptionUserAccountPass, userName, auth);
				return "true";
			} else {
				return "userIdRepeated";
			}
		}
		//失敗時
		return "false";
	}
	
	public String exuteEdit(String userName, String userAccountId, int auth, int userId){
		//フォームチェック
		FormCheck fc = new FormCheck();
		boolean fcResult = fc.isCorrectUserForm(userName, userAccountId);
		if (fcResult) {
			//登録インスタンス
			AccountDAO dao = new AccountDAO();
			//ユーザIDに重複が無いかチェック
			boolean isExistUserAccountId = dao.checkUserIdRegistEdit(userId, userAccountId);
			if (!isExistUserAccountId){
				//重複しない時、登録
				dao.chengeUserInfomation(userId,userAccountId, userName, auth);
				return "true";
			} else {
				return "userIdRepeated";
			}
		}
		//失敗時
		return "false";
	}
}
