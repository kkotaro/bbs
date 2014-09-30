function check(){	//入力チェック
	var name = document.form.name.value;
	var id = document.form.account_id.value;
	var chengePass = document.form.chengePass.checked;
	var pass = document.form.account_pass.value;
	var passCom = document.form.pass_com.value;
	var flag = 0;
	if (chengePass) {
		if(name == "" || id == "" || pass == "" || passCom == ""){ //空白の時
			flag = 2;
		}
	} else {
		if(name == "" || id == ""){ //空白の時
			flag = 1;
		}
	}
	
	if(flag == 1){
		var alertWords = "未入力の項目があります。確認してすべての項目を入力してください。";
		appendNode(alertWords);
		return false
	} else if (flag == 2) {
		var alertWords = "パスワードが入力されていません。"
			appendNode(alertWords);
		return false;
	} else if (id.match(/[^\w]/)){
		var alertWords = "ログインIDは半角英数で入力してください。";
		appendNode(alertWords);
		return false
	} else if (chengePass){
		if(pass != passCom){
			var alertWords = "入力されたパスワードが確認用パスワードと一致しません。もう一度入力してください。";
			appendNode(alertWords);
			return false
		} else if (pass.length < 6 || 255 < pass.length ) {
			var alertWords = "パスワードは6文字以上255文字以下で入力してください。";
			appendNode(alertWords);
			return false
		} else if (pass.match(/[\w]/) == null) {
			var alertWords = "パスワードは記号含む全ての半角英数文字で入力してください。";
			appendNode(alertWords);
			return false
		} else if (pass.match(/[!-/:-@≠\[-`{-~]+/) == null){
			var alertWords = "パスワードに記号が入っていません。";
			appendNode(alertWords);
			return false
		}
	} else {
		return true
	}
}

function entryCheck(){//書き込みチェック
	//本日の日付
	var today = new Date();
	var year = document.form.year.value;
	var month = document.form.month.value - 1;
	var day = document.form.day.value;
	var hour = document.form.hour.value;
	var minute = document.form.minute.value;
	
	var selectDay = new Date(year, month, day, hour, minute, 59, 999);
	
	var title = document.form.title.value;
	var text = document.form.text.value;
	var flag = 0;
	if(title == "" || title == "　" || title == " "){ //タイトル
		flag = 1;
	} else if (text == "" || text == "　" || text == " "){ //本文
		flag = 1;
	} else if (text.match(/[\w]/) == null && text.match(/[\s]/)) {
		flag = 1;
	} else if (year == "" || month == "" || day == "" || hour == "" || minute == ""){
		flag = 2;
	}
	if(flag == 1){
		var alertWords = "未入力の項目があります。確認してすべての項目を入力してください。";
		appendNode(alertWords);
		return false;
	}else if (document.form.conReleasedAppoint.checked && flag == 2){
		var alertWords = "日付指定をする際はすべての項目をお選び下さい。";
		appendNode(alertWords);
		return false;
	} else if (document.form.conReleasedAppoint.checked && selectDay.getTime() < today.getTime()){
		var alertWords = "現在の日付より前の時刻の投稿は出来ません。";
		appendNode(alertWords);
		return false;
	}  else {
		return true;
	}
}


function process() {
	var fmt = document.form.year;
	var fmt1 = document.form.month;
	var fmt2 = document.form.day;
	var fmt3 = document.form.hour;
	var fmt4 = document.form.minute;
	fmt.disabled = !document.form.conReleasedAppoint.checked;
	fmt1.disabled = !document.form.conReleasedAppoint.checked;
	fmt2.disabled = !document.form.conReleasedAppoint.checked;
	fmt3.disabled = !document.form.conReleasedAppoint.checked;
	fmt4.disabled = !document.form.conReleasedAppoint.checked;
}

function processPass() {
	var fmt = document.form.account_pass;
	var fmt1 = document.form.pass_com;
	fmt.disabled = !document.form.chengePass.checked;
	fmt1.disabled = !document.form.chengePass.checked;
}
function appendNode(alertWord){//警告文表示
	if (document.getElementById("noscriptalert")) {
		var dom_obj=document.getElementById("noscriptalert");
		var dom_obj_parent=dom_obj.parentNode;
		dom_obj_parent.removeChild(dom_obj);
	}
	var ele = document.createElement("div");
	ele.setAttribute("id", "noscriptalert");
	var str = document.createTextNode(alertWord);
	ele.appendChild(str);
	document.getElementById("alert_place").appendChild(ele);
}

function appendNodeComment(alertWord, id){//警告文表示
	if (document.getElementById("noscriptalert")) {
		var dom_obj=document.getElementById("noscriptalert");
		var dom_obj_parent=dom_obj.parentNode;
		dom_obj_parent.removeChild(dom_obj);
	}
	var ele = document.createElement("div");
	ele.setAttribute("id", "noscriptalert");
	var str = document.createTextNode(alertWord);
	ele.appendChild(str);
	document.getElementById("alert_place" + id).appendChild(ele);
}

function commentCheck(comment, conId) {
	var text = comment.value;
	var id = conId.value
	if (text == "") {
		var alertWords = "コメントは空白で入力することは出来ません。";
		appendNodeComment(alertWords, id);
		return false
	} else if (text.match(/[\w]/) == null && text.match(/[\s]/)) {
		var alertWords = "コメントは空白で入力することは出来ません。";
		appendNodeComment(alertWords, id);
		return false
	} else {
		return true;
	}
}
