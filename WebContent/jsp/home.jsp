<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.TreeMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="dao.ContributionDAO"%>
<%@ page import="dao.CommentDAO"%>
<%@ page import="model.Contribution"%>
<%@ page import="model.Comment"%>
<%@ page import="model.Account"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.util.LinkedHashMap"%>
<%
	ContributionDAO cont = new ContributionDAO();
	Map<Integer, Contribution> contributions = new LinkedHashMap<>();
	if(request.getParameter("page") != null){
		contributions = (LinkedHashMap) session.getAttribute("contributionsPage");
	} else {
		contributions = cont.contribution();
	}
	int contNum = cont.countContributions();
	//ログイン中のユーザ情報をセッションで取得
	Account account = (Account) session.getAttribute("userInfo");
	//セッションに保存されたuser_idを取り出し
	int userId = account.getUserId();
	String userAccountId = account.getUserAccountId();
	String userName = account.getName();
	
	%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<LINK href="CSS/style.css" rel="stylesheet" type="text/css">
<link href="CSS/jquery.mCustomScrollbar.css" rel="stylesheet"
	type="text/css" />

<script src="js/jquery-1.11.1.min.js"></script>
<script src="js/jquery.autosize.js" type="text/javascript"></script>
<script src="js/jquery-migrate-1.1.0.js" type="text/javascript"></script>
<script src="js/jquery.mCustomScrollbar.concat.min.js"
	type="text/javascript"></script>
<script type="text/javascript" src="js/formCheck.js"></script>

<script type="text/javascript">
//テキストエリア拡張jquery
$(document).ready(function(){
	$('textarea').autosize({
	});
});

//コメント表示jQuery
$(document).ready(function(){
	$(".comment").hide();
	var flg = "close";
	$(".viewcommentbtn").click(function(){
		contNum = Number($(this).attr('id').replace('viewcommentbtn',''));
		$("#comment" + contNum).slideToggle();
		if(flg == "close"){
			$(this).text("閉じる");
			flg = "open";
		}else{
			$(this).text("表示");
			flg = "close";
		}
	});
});

//スクロールjQuery
$(function(){
	$(window).load(function(){
		$(".comment_list").mCustomScrollbar();
	})
});

$(function(){
	$('#pagetop').hide();
	
	$(window).scroll(function(){
		if ($(this).scrollTop() > 100) {
			$('#pagetop').fadeIn();
		} else {
			$('#pagetop').fadeOut();
		}
	});
	$("#pagetop").click(function(){
	$("html,body").animate({scrollTop:0},'slow');
	return false;
	});
});
</script>
<title>ホーム</title>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="temp/head.jsp" />
		<div id="main">
			<jsp:include page="temp/side.jsp" />
			<div id="content">
				<noscript>
					<div class="noscriptalert">
						現在お使いのブラウザのJavaScriptの設定が無効になっています。<br />すべての機能を利用するためには、JavaScriptの設定を有効にしてください。
					</div>
				</noscript>
				<% if (request.getParameter("auth") != null){
				int auth = Integer.parseInt(request.getParameter("auth"));
				if (auth == 0) {
			 %>
				<div class="noscriptalert">このアカウントはユーザ管理権限がないため、ユーザ管理画面に遷移出来ません。</div>
				<% } }%>
				<p class="topic">ホーム</p>
				<p><%= userName + " " + userAccountId %>
					さん、こんにちは。
				</p>
				<p>左のメニューから項目をお選び下さい。</p>
				<p class="topic">掲示板</p>

				<div class="button_align2">
					<a href="/bbs/entry"> <span class="entry_button"><img
							align="top" alt="新規登録" src="resorce/bbsentry.png" width="25px">新規投稿する</span>
					</a>
				</div>
				<% if (contributions.isEmpty()){ %>
				<p class="notice">まだ投稿がありません。新規投稿ボタンから投稿することができます。</p>
				<% } else {%>
				<div class="con_topic">
					<p class="sub_topic">トピック一覧</p>
					<table class="con_topic_table">
						<% for (Contribution conTopic : contributions.values()) { %>
						<tr>
							<td class="topic_img" rowspan="2" align="center"><img
								alt="ユーザ" src="resorce/user.png" width="25px"></td>
							<th colspan="2"><a
								href="#contribution<%= conTopic.getConId() %>"><%= conTopic.getConTitle()%>
									(<%=conTopic.getCommNum() %>)</a></th>
						</tr>
						<tr>
							<td class="con_topic_username"><%= conTopic.getConUserName() + "(" + conTopic.getConUserAccountId() %>)
								さん</td>
							<td class="con_topic_released" width="180px"><%= conTopic.getConReleased() %>
							</td>
						</tr>
						<% } %>
					</table>
					<div class="con_page">
						<a href="/bbs/home"><span class="select_page">1</span></a>
						<% if (contNum > 10){ 
							for (int i=1; i <= (contNum/10); i++){ %>
						<a href="/bbs/home?page=<%= i %>"><span class="select_page"><%= i+1 %></span></a>
						<% } }%>
					</div>
				</div>
				<p class="sub_topic">書き込み一覧</p>
				<% } %>
				<% for (Contribution con : contributions.values()) { %>
				<p class="cont_created"><%= con.getConReleased() %></p>
				<div id="contribution<%= con.getConId() %>"></div>
				<div class="bbs">
					<table class="bbs_table">
						<tr>
							<td class="bbs_img" rowspan="4" valign="top"><img alt="ユーザ"
								src="resorce/user.png" width="30px"></td>
							<th class="bbs_th">投稿者：</th>
							<td colspan="3"><%= con.getConUserName() + "(" + con.getConUserAccountId() %>)
								さん</td>
						</tr>
						<tr>
							<th class="bbs_th">件名：</th>
							<td colspan="3"><%= con.getConTitle()%></td>
						</tr>
						<tr>
							<th colspan="4" align="center">本文</th>
						</tr>
						<tr>
							<td class="bbs_content" colspan="4"><pre>
									<% out.print(con.getConText()); %>
								</pre></td>
						</tr>
					</table>
					<div class="button_align_comment">
						<p class="comment_num"><%=con.getCommNum() %>件のコメント
						</p>
						<p id="viewcommentbtn<%= con.getConId() %>" class="viewcommentbtn">表示</p>
					</div>
					<div id="comment<%= con.getConId() %>" class="comment">
						<div class="comment_form">
							<p class="p_comment_list">
								<% if (con.getCommNum() > 0){ %>
								<%=con.getCommNum()%>
								件のコメントを表示
							</p>
							<% } else { %>
							コメントを書き込む
							<% } %>

							<form name="form" action="Comment" method="POST"
								onsubmit="return commentCheck(this.text, this.conId)">
								<img class="commentuser_img" alt="コメントユーザ"
									src="resorce/commentuser.png" width="30px" align="middle">
								<div id="alert_place<%=con.getConId() %>"></div>
								<textarea class="textareafeild"
									placeholder="この書き込みに対し500文字以内でコメントする" name="text"
									maxlength="500"></textarea>
								<input type="hidden" name="conId" value="<%= con.getConId() %>" />
								<input type="submit" value="コメントする" />
							</form>
						</div>
						<%
						CommentDAO comm = new CommentDAO();
						Map<Integer, Comment> comments = new LinkedHashMap<>();
						comments = comm.comment(con.getConId());
						if (!comments.isEmpty()) {
					%>
						<div class="comment_list">
							<% for (Comment com : comments.values()) {%>
							<table class="comment_table">
								<tr>
									<td class="bbs_img" rowspan="2"><img
										class="commentuser_img" alt="コメントユーザ"
										src="resorce/commentuser.png" width="30px" align="middle">
									</td>
									<td class="comment_name"><%= com.getCommUserName() + "(" + com.getCommUserAccountId() %>)
										<font color="#747474">さん</font></td>
									<td class="comment_time"><%= com.getCommCreated() %></td>
								</tr>
								<tr>
									<td colspan="2" class="comment_text"><pre><%= com.getCommText() %></pre>
									</td>
								</tr>
							</table>
							<% } %>
						</div>
						<% } else { %>
						<p class="comment_alert">コメントはありません。</p>
						<% }%>
					</div>
				</div>
				<% } %>
				<% if (!contributions.isEmpty()){ %>
				<div class="con_page">
					<a href="/bbs/home"><span class="select_page">1</span></a>
					<% if (contNum > 10){ 
				for (int i=1; i <= (contNum/10); i++){ %>
					<a href="/bbs/home?page=<%= i %>"><span class="select_page"><%= i+1 %></span></a>
					<% } }%>
				</div>
				<% } %>
				<div id="pagetop">
					<a href="#">トップへ</a>
				</div>
			</div>
			<div class="clear"></div>
		</div>

		<div id="footer"></div>
	</div>
</body>
</html>