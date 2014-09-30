package logic;

import model.Comment;
import dao.CommentDAO;

public class CommentEntryLogic {
	public boolean excute(Comment comment) {
		CommentDAO dao = new CommentDAO();
		Comment entry1 = dao.entryComment(comment);
		return entry1 != null;
	}
}
