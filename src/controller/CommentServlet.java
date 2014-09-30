package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.CommentEntryLogic;
import model.Comment;
@WebServlet("/Comment")
public class CommentServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
		String comText = request.getParameter("text");
		int conId = Integer.parseInt(request.getParameter("conId"));
		int userId = loginUser.getUserId();
		Comment comment = new Comment(comText, conId, userId);
		CommentEntryLogic bo = new CommentEntryLogic();
		boolean result = bo.excute(comment);
		if (result) {
			response.sendRedirect("/bbs/home");
		}
	}

}