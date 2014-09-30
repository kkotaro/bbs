package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.CommentEntryLogic;
import model.Account;
import model.Comment;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/Comment")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//入力された情報を変数に格納
		String comText = request.getParameter("text");
		int conId = Integer.parseInt(request.getParameter("conId"));
		//ログイン中のユーザ情報をセッションで取得
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("userInfo");
		//セッションに保存されたuser_idを取り出し
		int userId = account.getUserId();
		Comment comment = new Comment(comText, conId, userId);
		CommentEntryLogic bo = new CommentEntryLogic();
		boolean result = bo.excute(comment);
		if (result) {
			//フォワード
			response.sendRedirect("/bbs/home");
		}
	}

}