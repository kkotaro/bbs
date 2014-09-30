<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.TreeMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="dao.AccountDAO"%>
<%@ page import="model.Account"%>
<%
  //ログイン中のユーザ情報をセッションで取得
	Account account = (Account) session.getAttribute("userInfo");

	AccountDAO users = new AccountDAO();
	Map<Integer, Account> userList = new TreeMap<>();
	userList = users.userList(account.getUserId());
	%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="CSS/style.css" rel="stylesheet" type="text/css">
<title>ユーザ管理</title>
<script type="text/javascript">
function stopCom(){
	if(window.confirm('このユーザを停止します。よろしいですか？')){
		return true;
	} else {
		return false;
	}
}
function runCom(){
	if(window.confirm('このユーザの停止を解除します。よろしいですか？')){
		return true;
	} else {
		return false;
	}
}
</script>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="temp/head.jsp" />
		<div id="main">
			<jsp:include page="temp/side.jsp" />
			<div id="content">
				<a href="/bbs/home">ホームに戻る</a>
				<p class="topic">ユーザ管理</p>
				<p>ユーザ管理画面です。</p>
				<p>ユーザの登録、編集、停止、復活させることが出来ます。</p>
				<p class="sub_topic">ログインユーザー管理</p>
				<table class="login_user_table">
					<tr>
						<th>ログインID</th>
						<td>
							<% out.print(account.getUserAccountId()); %>
						</td>
					</tr>
					<tr>
						<th>名称</th>
						<td>
							<% out.print(account.getName()); %>
						</td>
					</tr>
					<tr>
						<th>編集する</th>
						<td><a
							href="/bbs/ControlUserServlet?mode=edit&user_id=<% out.println(account.getUserId()); %>">
								<span class="stop_button"> <img align="top" alt="編集"
									src="resorce/edit.png" width="20px"> 編集する
							</span>
						</a></td>
					</tr>
				</table>
				<p class="sub_topic">ユーザ一覧</p>
				<div class="button_align">
					<a href="/bbs/RegistUser"> <span class="entry_button"><img
							align="top" alt="新規登録" src="resorce/adduser.png" width="20px">ユーザを新規登録</span>
					</a>
				</div>
				<% if (userList.size() > 0) { //ログインユーザ以外のユーザが存在する時%>
				<table class="user_list_table">
					<tr>
						<th>ログインID</th>
						<th>名称</th>
						<th>管理者権限</th>
						<th>ユーザ状態</th>
						<th>編集</th>
					</tr>
					<% for (Account accounts : userList.values()) { %>
					<tr>
						<td rowspan="2">
							<% out.println(accounts.getUserAccountId()); %>
						</td>
						<td rowspan="2">
							<% out.println(accounts.getName()); %>
						</td>
						<td rowspan="2" align="center">
							<% if (accounts.getAuth() == 1) {%> <img align="top" alt="停止"
							src="resorce/circle.png" width="20px"> <% } else { %> - <% } %>
						</td>
						<td align="center" class="user_con">
							<%if (accounts.getCondition() == 0) { %> 現在稼働中 <%} else { %> <font
							color="#EE6557">現在停止中</font> <% } %>
						</td>
						<td rowspan="2" align="center"><a
							href="/bbs/ControlUserServlet?mode=edit&user_id=<%= accounts.getUserId() %>">
								<span class="stop_button"> <img align="top" alt="編集"
									src="resorce/edit.png" width="20px"> 編集する
							</span>
						</a></td>
					</tr>
					<tr>
						<td align="center" valign="middle" style="padding: 8px;">
							<% if (accounts.getCondition() == 0) { %>
							<form action="/bbs/ControlUserServlet" method="POST"
								onSubmit="return stopCom()">
								<button type="submit" class="form_button">
									<img align="top" alt="停止" src="resorce/minus.png" width="20px">
									停止させる
								</button>
								<input type="hidden" name="user_id"
									value="<%= accounts.getUserId()%>" /> <input type="hidden"
									name="mode" value="stop" />
								<% } else { %>
								<form action="/bbs/ControlUserServlet" method="POST"
									onSubmit="return runCom()">
									<button type="submit" class="form_button">
										<img align="top" alt="稼働" src="resorce/play.png" width="20px">
										稼働させる
									</button>
									<input type="hidden" name="user_id"
										value="<%= accounts.getUserId()%>" /> <input type="hidden"
										name="mode" value="run" />
									<% } %>
								</form>
						</td>
					</tr>
					<% } %>
				</table>
				<%} else {%>
				<p class="notice">現在登録ユーザはいません。新規登録ボタンよりユーザを追加してください。</p>
				<% } %>
			</div>
			<div class="clear"></div>
		</div>

		<div id="footer"></div>
	</div>
</body>
</html>