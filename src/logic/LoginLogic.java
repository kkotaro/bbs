package logic;
import java.security.NoSuchAlgorithmException;

import model.Account;
import model.HashPass;
import dao.AccountDAO;

public class LoginLogic {
	public Account excute(String userAccountId, String userAccountPass) {
		//アカウントインスタンス
		AccountDAO dao = new AccountDAO();
		//暗号化インスタンス
		HashPass sha = new HashPass();
		String encryptionUserAccountPass = null;
		//入力されたパスワードを暗号化
		try {
			encryptionUserAccountPass = sha.encryptionSha(userAccountPass);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		//ログイン時入力情報を格納
		Account account = new Account(userAccountId, encryptionUserAccountPass);
		//ログイン処理
		Account accountResult = dao.findByLogin(account);
		//ログインユーザ情報リターン
		return accountResult;
	}
}