package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import model.Comment;
import model.XssMeasures;
import dao.ConnectMySQL;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class CommentDAO {
	Map<Integer, Comment> comments = new LinkedHashMap<>();
	//コメント読み込みメソッド
	public Map<Integer, Comment> comment (int contributionNum) throws SQLException {
		Connection con = null;
		try{
			ConnectMySQL conn = new ConnectMySQL();
			con = (Connection) conn.getConnection();
			String sql = "SELECT comm_id, comments.text, comments.created, con_id, account_id, name "
					+ "FROM comments INNER JOIN users USING (user_id) INNER JOIN contributions USING (con_id)"
					+ "WHERE con_id = " + contributionNum
					+ " ORDER BY created DESC";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy'年'MM'月'dd'日'HH'時'mm'分'");
			while (rs.next()) {
				Comment comm = new Comment(
						rs.getInt("comm_id"),
						rs.getString("text"), 
						sdf.format(rs.getTimestamp("created")), 
						rs.getInt("con_id"), 
						rs.getString("account_id"),
						rs.getString("name")
						);
				comments.put(rs.getInt("comm_id"), comm);
			}
		} catch (SQLException e) {
			
		}
		return comments;
	}
	
	//コメント書き込みメソッド
	public Comment entryComment(Comment comment) {
		Connection con = null;
		try{
			ConnectMySQL conn = new ConnectMySQL();
			con = (Connection) conn.getConnection();
			XssMeasures xss = new XssMeasures();
			String sql = "INSERT INTO comments(text, created, con_id, user_id) VALUES (?, ?, ?, ?);";
			PreparedStatement pStmt = con.prepareStatement(sql);
			pStmt.setString(1, xss.htmlspecialchars(comment.getCommText()));
			pStmt.setString(2, null);
			pStmt.setInt(3, comment.getConId());
			pStmt.setInt(4, comment.getCommUserId());
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
		return comment;
	}
}
