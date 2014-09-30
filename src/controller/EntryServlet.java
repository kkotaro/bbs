package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.EntryLogic;
import model.Account;
import model.Contribution;

/**
 * Servlet implementation class DoEntry
 */
@WebServlet("/entry")
public class EntryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EntryServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/entry.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//入力された情報を変数に格納
		String conTitle = request.getParameter("title");
		String conText = request.getParameter("text");
		String conReleased = null;
		if(conTitle != "" && conText != ""){ 		//空でないとき
			if (request.getParameter("conReleasedAppoint") != null) {
				//日付指定ボタンが押されている時
				int year = Integer.parseInt(request.getParameter("year"));
				int month = Integer.parseInt(request.getParameter("month"));
				int day = Integer.parseInt(request.getParameter("day"));
				int hour = Integer.parseInt(request.getParameter("hour"));
				int minute = Integer.parseInt(request.getParameter("minute"));
				//指定日時に代入
				conReleased = String.format("%d-%02d-%02d %02d:%02d:00",year, month, day, hour, minute);
			}
			//ログイン中のユーザ情報をセッションで取得
			HttpSession session = request.getSession();
			Account account = (Account) session.getAttribute("userInfo");
			//セッションに保存されたuser_idを取り出し
			int userId = account.getUserId();
			
			Contribution cont = new Contribution(conTitle, conText, userId, conReleased);
			EntryLogic bo = new EntryLogic();
			boolean result = bo.excute(cont);
			
			if (result) {
				//フォワード
				response.sendRedirect("/bbs/home");
			}
		}else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/entry.jsp");
			dispatcher.forward(request, response);
		}
	}

}
