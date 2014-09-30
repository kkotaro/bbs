<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// 内容: セッションを使用する
// セッション変数の取得
String userAccountId = (String)session.getAttribute("userAccountId");
String userName = (String)session.getAttribute("userName");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/formCheck.js"></script>
<script type="text/javascript" src="js/selectDate.js"></script>
<LINK href="CSS/style.css" rel="stylesheet" type="text/css">
<title>新規投稿</title>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="temp/head.jsp" />
		<div id="main">
			<jsp:include page="temp/side.jsp" />
			<div id="content">
				<noscript>
					<div class="noscriptalert">
						現在お使いのブラウザのJavaScriptの設定が無効になっています。<br />投稿の日時指定機能を利用するためには、JavaScriptの設定を有効にしてください。
					</div>
				</noscript>
				<a href="/bbs/home">ホームに戻る</a>
				<p class="topic">新規投稿</p>
				<p>掲示板への新規投稿をします。</p>
				<p>以下の項目を入力し、投稿ボタンを押してください。</p>
				<div id="alert_place"></div>
				<form action="entry" method="POST" name="form"
					onsubmit="return entryCheck()">
					<table class="entryform_table">
						<tr>
							<th>
								<p class="td_topic">件名</p>
							</th>
							<td><span class="explain">投稿の件名を50文字以下で入力してください</span></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="text" name="title" maxlength="50" /></td>
						</tr>
						<tr>
							<th>
								<p class="td_topic">本文</p>
							</th>
							<td><span class="explain">投稿の本文を1000文字以下で入力してください</span></td>
						</tr>
						<tr>
							<td></td>
							<td><textarea name="text" rows="9" cols="46"
									maxlength="1000"></textarea></td>
						</tr>
						<tr>
							<th>
								<p class="td_topic">投稿日時</p>
							</th>
							<td><span class="explain">投稿の日時を指定する場合、チェックし投稿日時をお選び下さい。そのままの場合、投稿した時間が投稿日時となります</span>
							</td>
						</tr>
						<tr>
							<td></td>
							<td><label><input type="checkbox"
									name="conReleasedAppoint" value="1" onclick="process()" />
									投稿日時を指定する</label></td>
						</tr>
						<tr>
							<td></td>
							<td class="input_timefield"><select name="year" id="year"
								disabled>
									<option value=""></option>
							</select>年 <select name="month" id="month" disabled>
									<option value=""></option>

							</select>月 <select name="day" id="day" disabled>
									<option value=""></option>
							</select>日 <select name="hour" id="hour" disabled>
									<option value=""></option>
							</select>時 <select name="minute" id="minute" disabled>
									<option value=""></option>
							</select>分
						</tr>
						<tr>
							<td class="entry_com" colspan="2" align="center">上記の内容でよろしければ、投稿ボタンを押してください</td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" value="投稿" /></td>
						</tr>
					</table>
					<input type="hidden" name="action" value="done" />
				</form>
			</div>
			<div class="clear"></div>
		</div>

		<div id="footer"></div>
	</div>
</body>
</html>