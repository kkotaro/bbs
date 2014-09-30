package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.EntryLogic;

@WebServlet("/entry")
public class EntryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/entry.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
		EntryLogic bo = new EntryLogic(request, loginUser.getUserId());
		boolean result = bo.excute();
		if (result) {
			response.sendRedirect("/bbs/home");
			return ;
		}
		request.getRequestDispatcher("jsp/entry.jsp").forward(request, response);
	}
}
