<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.TreeMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="dao.AccountDAO"%>
<%@ page import="model.Account"%>
<%
    //GET送信されたユーザID
	int userId = Integer.parseInt(request.getParameter("user_id"));
    AccountDAO userInfo = new AccountDAO();
	Map<String, String> information = new TreeMap<>();
	//編集するユーザの情報
	information = userInfo.userInfo(userId);
	
	//管理者が自身のアカウントを変更するとき
	Account account = (Account) session.getAttribute("userInfo");
	int authUserId = account.getUserId();
	
	//サーバ側のフォームチェック用
	boolean existUserAccountId = false;
	boolean formCheck = true;
	if(request.getAttribute("exist") != null){
		existUserAccountId = (boolean) request.getAttribute("exist");
	} else if (request.getAttribute("formCheck") != null){
		formCheck = (boolean) request.getAttribute("formCheck");
	}
	%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="CSS/style.css" rel="stylesheet" type="text/css">
<title>ユーザ編集</title>
<script type="text/javascript" src="js/formCheck.js"></script>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="temp/head.jsp" />
		<div id="main">
			<jsp:include page="temp/side.jsp" />
			<div id="content">
				<noscript>
					<div class="noscriptalert">
						現在お使いのブラウザのJavaScriptの設定が無効になっています。<br />パスワードの変更機能を利用するためには、JavaScriptの設定を有効にしてください。
					</div>
				</noscript>
				<a href="/bbs/ManageUsers">管理画面に戻る</a>
				<p class="topic">ユーザ編集</p>
				<p>ユーザ情報の変更を行います。</p>
				<p>以下の項目を入力してください。</p>
				<% if (existUserAccountId) { %>
				<div class="form_alert">
					このユーザIDは既に登録されています。<br />別のIDをお試し下さい。
				</div>
				<% } else if (!formCheck) {%>
				<div class="form_alert">
					フォームの内容が正しくありません。確認してもう一度入力してください。<br />
					<noscript>（javascriptを有効にすると詳細情報を表示することができます。）</noscript>
				</div>
				<% } %>
				<div id="alert_place"></div>
				<form action="/bbs/ManageUsers" name="form" method="POST"
					onSubmit="return check()">
					<table class="user_form">
						<tr>
							<th>
								<p class="td_topic">名称</p>
							</th>
							<td><input type="text" name="name" maxlength="10"
								value="<%= information.get("userName")%>" /></td>
						</tr>
						<tr>
							<td></td>
							<td><span class="explain">本社や各店舗の名称を10文字以下で入力してください</span></td>
						</tr>
						<tr>
							<th>
								<p class="td_topic">ログインID</p>
							</th>
							<td><input type="text" name="account_id" maxlength="20"
								value="<%= information.get("userAccountId")%>" /></td>
						</tr>
						<tr>
							<td></td>
							<td><span class="explain">システムへのログインに使用するIDを半角英数字で6文字以上20文字以下で入力してください</span>
							</td>
						</tr>
						<tr>
							<th>
								<p class="td_topic">パスワード</p>
							</th>
							<td><label><input type="checkbox" name="chengePass"
									value="1" onclick="processPass()" /> パスワードを変更する</label></td>
						</tr>
						<tr>
							<td></td>
							<td><input class="inputPass" type="password"
								name="account_pass" maxlength="255" disabled /></td>
						</tr>
						<tr>
							<td></td>
							<td><span class="explain">システムへのログインに使用するパスワードを記号含む全ての半角文字で6文字以上255文字以下で入力してください</span>
							</td>
						</tr>
						<tr>
							<th>
								<p class="td_topic">パスワード（確認）</p>
							</th>
							<td><input class="inputPass" type="password" name="pass_com"
								maxlength="255" disabled /></td>
						</tr>
						<tr>
							<td></td>
							<td><span class="explain">確認のためパスワードをもう一度入力してください</span></td>
						</tr>
						<tr>
							<th>
								<p class="td_topic">管理者権限</p>
							</th>
							<td>
								<% if (authUserId == userId) {%> <span class="explain">自身の管理者権限を変えることは出来ません。</span>
								<% } else { %> <% if (information.get("userAuth").equals("0")) { %>
								<label><input type="radio" name="auth" value="0"
									checked="checked">権限なし</label> <label><input
									type="radio" name="auth" value="1">権限あり</label> <% } else { %> <label><input
									type="radio" name="auth" value="0">権限なし</label> <label><input
									type="radio" name="auth" value="1" checked="checked">権限あり</label>
								<% } %> <% } %>
							</td>
						</tr>
						<tr>
							<td class="entry_com" colspan="2" align="center">上記の内容でよろしければ、登録ボタンを押してください</td>
						</tr>
						<tr>
							<td></td>
							<td><input type="hidden" name="mode" value="editsubmit" /> <input
								type="hidden" name="user_id" value="<%= userId %>" /> <input
								type="submit" value="登録" /></td>
						</tr>
					</table>

				</form>
			</div>
			<div class="clear"></div>
		</div>

		<div id="footer"></div>
	</div>
</body>
</html>