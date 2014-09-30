package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;

public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Account  loginUser;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		loginUser = (Account) request.getSession().getAttribute("userInfo");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		loginUser = (Account) request.getSession().getAttribute("userInfo");
	}

}
