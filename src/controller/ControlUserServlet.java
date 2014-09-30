package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.AccountDAO;

@WebServlet("/ControlUserServlet")
public class ControlUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		if (mode.equals("edit")) {
			//編集ボタンを押したとき
			request.getRequestDispatcher("jsp/edit_user.jsp?user_id=" + user_id).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		AccountDAO userCondition = new AccountDAO();
		
		if (mode.equals("stop")) {
			//停止ボタンを押したとき
			userCondition.chengeUserCondintion(user_id, 0);
			request.getRequestDispatcher("jsp/manage_user.jsp").forward(request, response);
		} else if (mode.equals("run")) {
			//稼働ボタンを押したとき
			userCondition.chengeUserCondintion(user_id, 1);
			request.getRequestDispatcher("jsp/manage_user.jsp").forward(request, response);
		}
	}
}
