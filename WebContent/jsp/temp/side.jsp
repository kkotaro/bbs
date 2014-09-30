<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="model.Account" %>
    <%
  //ログイン中のユーザ情報をセッションで取得
  	Account userInfo = (Account) session.getAttribute("userInfo");
  	//セッションに保存されたuser_idを取り出し
  	int userId = userInfo.getUserId();
  	String userAccountId = userInfo.getUserAccountId();
  	String userName = userInfo.getName();
  	int userAuth = userInfo.getAuth();
    %>
	<div id="side">
		<div class="side_info">
			<h2>ログイン中</h2>
			<p class="login_name"><% out.print(userName); %></p>
			<p class="login_name"><% out.print(userAccountId); %> 様</p>
		</div>
		<div class="side_menu">
			<h2>メニュー</h2>
			<ul>
				<li>
					<a href="/bbs/home"><img align="top" alt="ホーム" src="resorce/home.png" >　ホーム</a>
				</li>
				<li>
					<a href="/bbs/entry"><img align="top" alt="新規投稿" src="resorce/entry.png">　新規投稿</a>
				</li>
				<%
					if (userAuth == 1) {
			 	%>
				<li>
					<a href="/bbs/ManageUsers"><img align="top" alt="管理画面" src="resorce/management.png">　ユーザー管理</a>
				</li>
				<% } %>
			</ul>
		</div>
	</div>