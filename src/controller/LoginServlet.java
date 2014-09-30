package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.LoginLogic;
import model.Account;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		session.invalidate();
		response.sendRedirect("/bbs/login.jsp?mode=3");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userAccountId = request.getParameter("id");
		String userAccountPass = request.getParameter("pass");
		LoginLogic bo = new LoginLogic();
		Account result = bo.excute(userAccountId, userAccountPass);
		//ログイン成功失敗判定
		if (result == null) {
			request.setAttribute("login", false);
			response.sendRedirect("/bbs/login.jsp?mode=1");
			return;
		}

		if (result.isBanned()){
			request.setAttribute("userCondition", "stop");
			response.sendRedirect("/bbs/login.jsp?mode=2");
			return;
		}

		request.getSession().setAttribute("userInfo", result);
		request.getRequestDispatcher("jsp/home.jsp").forward(request, response);
	}
}

