package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.RegistLogic;

@WebServlet("/RegistUser")
public class RegistUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/regist_user.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		//登録ロジックインスタンス
		RegistLogic bo = new RegistLogic();
		if(mode.equals("adduser")) {
			/*-----ユーザ登録時------*/
			//書き込み値を取得
			String userName = request.getParameter("name");
			String userAccountId = request.getParameter("account_id");
			String userAccountPass = request.getParameter("account_pass");
			String userAccountPassCom = request.getParameter("pass_com");
			int auth = Integer.parseInt(request.getParameter("auth"));
			//各値を登録
			String result = bo.excuteRegist(userName, userAccountId, userAccountPass, userAccountPassCom, auth);
			if (result.equals("true")) {
				//成功時。管理画面へフォワード
				response.sendRedirect("/bbs/ManageUsers");
				return ;
			} else if (result.equals("userIdRepeated")){
				//ID重複時
				request.setAttribute("exist", true);
			} else {
				request.setAttribute("formCheck", false);
			}
			//失敗時。入力画面にフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/regist_user.jsp");
			dispatcher.forward(request, response);
		}
	}

}
