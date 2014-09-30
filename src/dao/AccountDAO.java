package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import com.mysql.jdbc.Statement;

import model.Account;
import model.XssMeasures;

public class AccountDAO {
	XssMeasures xss = new XssMeasures();
	//ログイン処理のメソッド
	public Account findByLogin(Account account2) {
		Connection con = null;
		Account account = null;
		try{
			ConnectMySQL conn = new ConnectMySQL();
			
			con = conn.getConnection();
			String sql = "SELECT * FROM users WHERE account_id = ? AND account_pass = ?";
			PreparedStatement pStmt = con.prepareStatement(sql);
			
			pStmt.setString(1, xss.htmlspecialchars(account2.getUserAccountId()));
			pStmt.setString(2, xss.htmlspecialchars(account2.getPass()));
			
			ResultSet result = pStmt.executeQuery();
			if (result.next()) {
				account = new Account(
						result.getInt("user_id"), 
						result.getString("account_id"), 
						result.getString("account_pass"), 
						result.getString("name"), 
						result.getInt("manage_auth"), 
						result.getInt("condition")
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (con != null) {
				try{
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return account;
	}
	
	//状態取得
	public int CheckCondition(int userId) {
		Connection con = null;
		int condition = 0;
		try{
			ConnectMySQL conn = new ConnectMySQL();
			con = (Connection) conn.getConnection();
			String sql = "SELECT `condition` FROM users WHERE user_id = " + userId;
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				condition = rs.getInt("condition");
			}
		} catch (SQLException e) {
			
		}
		return condition;
	}
	
	//ユーザリスト取得のメソッド
	public Map<Integer, Account> userList(int userId) {
		Map<Integer, Account> userList = new TreeMap<>();
		Connection con = null;
		try{
			ConnectMySQL conn = new ConnectMySQL();
			con = (Connection) conn.getConnection();
			String sql = "SELECT user_id, account_id, name, manage_auth, `condition` FROM users WHERE user_id <> " + userId;
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Account account = new Account(
						rs.getInt("user_id"),
						rs.getString("account_id"),
						rs.getString("name"),
						rs.getInt("manage_auth"),
						rs.getInt("condition")
						);
				userList.put(rs.getInt("user_id"), account);
			}
		} catch (SQLException e) {
			
		}
		return userList;
	}
	
	//ユーザ情報変更時の特定ユーザ情報取得のメソッド
	public Map<String, String> userInfo(int userId) {
		Map<String, String> userInfo = new TreeMap<>();
		Connection con = null;
		try{
			ConnectMySQL conn = new ConnectMySQL();
			con = (Connection) conn.getConnection();
			String sql = "SELECT * FROM users WHERE user_id = " + userId;
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				userInfo.put("userId", String.valueOf(rs.getInt("user_id")));
				userInfo.put("userAccountId", rs.getString("account_id"));
				userInfo.put("userAccountPass", rs.getString("account_pass"));
				userInfo.put("userName", rs.getString("name"));
				userInfo.put("userAuth", String.valueOf(rs.getInt("manage_auth")));
				userInfo.put("userCondition", String.valueOf(rs.getInt("condition")));
			}
		} catch (SQLException e) {
		} 
		return userInfo;
	}
	
	//稼働、停止ボタン時のメソッド
	public void chengeUserCondintion(int userId, int userCondition) {
		Connection con = null;
		try{
			ConnectMySQL conn = new ConnectMySQL();
			con = conn.getConnection();
			String sql = "UPDATE users SET `condition` = ? WHERE user_id = " + userId;
			PreparedStatement pStmt = con.prepareStatement(sql);
			if (userCondition == 0) {
				//稼働中 -> 停止
				pStmt.setInt(1, 1);
			} else {
				//停止中 -> 稼働
				pStmt.setInt(1, 0);
			}
			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try{
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}
	
	//ユーザ情報変更のメソッド
	public void chengeUserInfomation(int userId, String userAccountId, String userAccountPass, String userName, int userAuth) {
		Connection con = null;
		try{
			ConnectMySQL conn = new ConnectMySQL();
			con = conn.getConnection();
			String sql = "UPDATE users SET "
					+ "account_id = ? ,"
					+ "account_pass = ? ,"
					+ "name = ? ,"
					+ "manage_auth = ? "
					+ "WHERE user_id = " + userId;
			PreparedStatement pStmt = con.prepareStatement(sql);
			pStmt.setString(1, xss.htmlspecialchars(userAccountId));
			pStmt.setString(2, xss.htmlspecialchars(userAccountPass));
			pStmt.setString(3, xss.htmlspecialchars(userName));
			pStmt.setInt(4, userAuth);

			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try{
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}
	//ユーザ情報変更のメソッド(パス変更無し)
	public void chengeUserInfomation(int userId, String userAccountId, String userName, int userAuth) {
		Connection con = null;
		try{
			ConnectMySQL conn = new ConnectMySQL();
			con = conn.getConnection();
			String sql = "UPDATE users SET "
					+ "account_id = ? ,"
					+ "name = ? ,"
					+ "manage_auth = ? "
					+ "WHERE user_id = " + userId;
			PreparedStatement pStmt = con.prepareStatement(sql);
			pStmt.setString(1, xss.htmlspecialchars(userAccountId));
			pStmt.setString(2, xss.htmlspecialchars(userName));
			pStmt.setInt(3, userAuth);

			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try{
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}
	
	//ユーザ新規追加のメソッド
	public void addUser(String userAccountId, String userAccountPass, String userName, int userAuth) {
		Connection con = null;
		try{
			ConnectMySQL conn = new ConnectMySQL();
			con = conn.getConnection();
			String sql = "INSERT INTO users (account_id, account_pass, name, manage_auth, `condition`)"
					+ "VALUES ( ? , ? , ? , ?, 0)";
			PreparedStatement pStmt = con.prepareStatement(sql);
			pStmt.setString(1, xss.htmlspecialchars(userAccountId));
			pStmt.setString(2, xss.htmlspecialchars(userAccountPass));
			pStmt.setString(3, xss.htmlspecialchars(userName));
			pStmt.setInt(4, userAuth);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try{
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}
	
	//ユーザIDが存在するかどうかチェック
	public boolean checkUserIdRegist (String userAccountId) {
		Connection con = null;
		try{
			ConnectMySQL conn = new ConnectMySQL();
			con = conn.getConnection();
			String sql = "SELECT account_id FROM users WHERE account_id = ?";
			PreparedStatement pStmt = con.prepareStatement(sql);
			
			pStmt.setString(1, xss.htmlspecialchars(userAccountId));
			
			ResultSet result = pStmt.executeQuery();
			
			if (result.next()) {
				return true;
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try{
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return false;
	}
	
	////ユーザIDが存在するかどうかチェック(編集時自分以外)
	public boolean checkUserIdRegistEdit (int userId, String userAccountId) {
		Connection con = null;
		try{
			ConnectMySQL conn = new ConnectMySQL();
			con = conn.getConnection();
			String sql = "SELECT account_id FROM users WHERE account_id = ? AND user_id <> " + userId;
			PreparedStatement pStmt = con.prepareStatement(sql);
			
			pStmt.setString(1, xss.htmlspecialchars(userAccountId));
			
			ResultSet result = pStmt.executeQuery();
			
			if (result.next()) {
				return true;
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try{
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return false;
	}
}
