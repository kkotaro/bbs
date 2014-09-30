package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import model.Contribution;
import model.XssMeasures;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import dao.ConnectMySQL;

public class ContributionDAO {
	Map<Integer, Contribution> contributions = new LinkedHashMap<>();
	//掲示板読み込みメソッド
	public Map<Integer, Contribution> contribution () throws SQLException, ParseException {
		Connection con = null;
		try{
			ConnectMySQL conn = new ConnectMySQL();
			con = (Connection) conn.getConnection();
			
			String sql = "SELECT con_id, title, text, created, user_id,"
					+ " account_id, name, released "
					+ "FROM contributions INNER JOIN users USING (user_id) "
					+ "WHERE released <= NOW() ORDER BY released DESC limit 0,10";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy'年'MM'月'dd'日'HH'時'mm'分'");
			while (rs.next()) {
				System.out.println(rs.getString("text"));
				Contribution cont = new Contribution(
						rs.getInt("con_id"),
						rs.getString("title"), 
						rs.getString("text"), 
						sdf.format(rs.getTimestamp("created")), 
						rs.getInt("user_id"), 
						rs.getString("account_id"),
						rs.getString("name"),
						sdf.format(rs.getTimestamp("released")),
						countComments(rs.getInt("con_id"))
						);
				contributions.put(rs.getInt("con_id"), cont);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contributions;
	}
	
	Map<Integer, Contribution> contributionsPage = new LinkedHashMap<>();
	//掲示板読み込みメソッド
	public Map<Integer, Contribution> contribution (int pageNum) throws SQLException, ParseException {
		Connection con = null;
		try{
			ConnectMySQL conn = new ConnectMySQL();
			con = (Connection) conn.getConnection();
			
			int contNum = pageNum * 10;
			String sql = "SELECT con_id, title, text, created, user_id,"
					+ " account_id, name, released "
					+ "FROM contributions INNER JOIN users USING (user_id) "
					+ "WHERE released <= NOW() ORDER BY released DESC limit " + contNum + ",10";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy'年'MM'月'dd'日'HH'時'mm'分'");
			while (rs.next()) {
				Contribution cont = new Contribution(
						rs.getInt("con_id"),
						rs.getString("title"), 
						rs.getString("text"), 
						sdf.format(rs.getTimestamp("created")), 
						rs.getInt("user_id"), 
						rs.getString("account_id"),
						rs.getString("name"),
						sdf.format(rs.getTimestamp("released")),
						countComments(rs.getInt("con_id"))
						);
				contributionsPage.put(rs.getInt("con_id"), cont);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contributionsPage;
	}
	
	//掲示板書き込みメソッド
	public Contribution entryContributions(Contribution cont) {
		Connection con = null;
		try{
			ConnectMySQL conn = new ConnectMySQL();
			con = (Connection) conn.getConnection();
			XssMeasures xss = new XssMeasures();
			String sql = "INSERT INTO contributions(title, text, created, user_id, released)"
					+ " VALUES (?, ?, ?, ?, ?);";
			PreparedStatement pStmt = con.prepareStatement(sql);
			
			pStmt.setString(1, xss.htmlspecialchars(cont.getConTitle()));
			pStmt.setString(2, xss.htmlspecialchars(cont.getConText()));
			pStmt.setString(3, null);
			pStmt.setInt(4, cont.getConUserId());
			pStmt.setString(5, cont.getConReleased());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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
		return cont;
	}
	
	//掲示板書き込み数
	public int countContributions(){
		Connection con = null;
		int contNum = 0;
		try{
			ConnectMySQL conn = new ConnectMySQL();
			con = (Connection) conn.getConnection();
			
			String sql = "SELECT COUNT(*) FROM contributions";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				contNum =rs.getInt("COUNT(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try{
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
					return 0;
				}
			}
		}
		return contNum;
		
	}
	
	//コメント数
	public int countComments(int con_id){
		Connection con = null;
		int commNum = 0;
		try{
			ConnectMySQL conn = new ConnectMySQL();
			con = (Connection) conn.getConnection();
			
			String sql = "SELECT COUNT(*) FROM comments WHERE con_id = " + con_id;
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				commNum =rs.getInt("COUNT(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try{
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
					return 0;
				}
			}
		}
		return commNum;
	}
}
