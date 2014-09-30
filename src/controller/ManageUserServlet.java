package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.RegistLogic;
import model.Account;

@WebServlet("/ManageUsers")
public class ManageUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ログイン中のユーザ情報をセッションで取得
		HttpSession session = request.getSession();
	  	Account account = (Account) session.getAttribute("userInfo");
	  	int userAuth = account.getAuth();
		if (userAuth == 1) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/manage_user.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/home.jsp?auth=0");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		int userId = Integer.parseInt(request.getParameter("user_id"));
		RegistLogic bo = new RegistLogic();
		if (mode.equals("editsubmit")) {
			/*-----ユーザ編集時------*/
			//書き込み値を取得
			String userName = request.getParameter("name");
			String userAccountId = request.getParameter("account_id");
			String chengePass = request.getParameter("chengePass");
			int auth = Integer.parseInt(request.getParameter("auth"));
			String result = null;
			if (chengePass != null) { //パスワード変更にチェックが入っている場合
				//各値を登録
				String userAccountPassCom = request.getParameter("pass_com");
				String userAccountPass = request.getParameter("account_pass");
				result = bo.exuteEdit(userName, userAccountId, userAccountPass, userAccountPassCom, auth, userId);
			} else {
				result = bo.exuteEdit(userName, userAccountId, auth, userId);
			}
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
		}
		//失敗時。入力画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/edit_user.jsp");
		dispatcher.forward(request, response);
	}
}
