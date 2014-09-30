package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.AccountDAO;

/**
 * Servlet implementation class ControlUserServlet
 */
@WebServlet("/ControlUserServlet")
public class ControlUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControlUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		if (mode.equals("edit")) {
			//編集ボタンを押したとき
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/edit_user.jsp?user_id=" + user_id);
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		AccountDAO userCondition = new AccountDAO();
		
		if (mode.equals("stop")) {
			//停止ボタンを押したとき
			userCondition.chengeUserCondintion(user_id, 0);
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/manage_user.jsp");
			dispatcher.forward(request, response);
		} else if (mode.equals("run")) {
			//稼働ボタンを押したとき
			userCondition.chengeUserCondintion(user_id, 1);
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/manage_user.jsp");
				dispatcher.forward(request, response);
			}
	}
	
	}
