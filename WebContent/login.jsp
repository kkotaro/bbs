<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String mode = request.getParameter("mode");
	%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン</title>
<LINK href="CSS/login.css" rel="stylesheet" type="text/css">
<script>
function check(){
	if(document.form.id.value == ""){
		window.alert("IDを入力してください。");
		return false;
	}else if(document.form.pass.value == ""){
		window.alert("パスワードを入力してください。");
		return false;
	}else{
		return true;
	}
}
</script>
</head>
<body>
	<div id="wrapper">
		<div id="header"></div>
		<div id="main">
			<div class="login">
				<h1>ログイン</h1>
				<% if ("1".equals(mode)) { %>
				<p class="alert">メール アドレスまたはパスワードが正しくありません</p>
				<% } else if("2".equals(mode)){%>
				<p class="alert">現在このアカウントは停止中です。</p>
				<p class="alert">店舗の管理者にお問い合わせ下さい</p>
				<% } else if("3".equals(mode)){%>
				<p class="alert">ログアウトしました。</p>
				<% } %>
				<form action="/bbs/Login" method="POST" name="form"
					onSubmit="return check()">
					<table>
						<tr>
							<td><input type="text" name="id" placeholder="ログインID"
								maxlength="20" /></td>
						</tr>
						<tr>
							<td><input type="password" name="pass" placeholder="パスワード"
								maxlength="255" /></td>
						</tr>
						<tr>
							<td align="right"><input type="submit" value="ログイン" /></td>
						</tr>
					</table>
					<input type="hidden" name="action" value="login_action">
				</form>
			</div>
		</div>
		<div id="footer"></div>
	</div>

</body>
</html>