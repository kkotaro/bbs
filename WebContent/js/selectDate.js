window.onload = function (){
	setEvent_to_OBJ("document.getElementById('year')" ,"change","iniD") 
	setEvent_to_OBJ("document.getElementById('month')","change","iniD") 
	ini();
}
function setEvent_to_OBJ(objNameStr,eventTypeNameStr,fncNameStr){
	eval(objNameStr+".on"+eventTypeNameStr+"="+ fncNameStr);
}

arrM = new Array(0,31,28,31,30,31,30,31,31,30,31,30,31);
var dayT = new Date;
var strY = dayT.getYear();
var strM = dayT.getMonth();
var strD = dayT.getDate();
var strH = dayT.getHours();
var strMM = dayT.getMinutes();

if(strY < 1900){strY += 1900;}

function ini(){
	iniY();
	iniM();
	iniD();
	iniH();
	iniMM();
}
function iniY(){
	var yr = document.getElementById("year");
	yr.options.length = 4;
	yr.options[0].text  = strY;
	yr.options[1].text  = strY + 1;
	yr.options[2].text  = strY + 2;
	yr.options[3].text  = strY + 3;
	yr.options[0].value = strY;
	yr.options[1].value = strY + 1;
	yr.options[2].value = strY + 2;
	yr.options[3].value = strY + 3;
	yr.options[0].selected = "selected";
}
function iniM(){
	var mt = document.getElementById("month");
	mt.options.length = 12;
	for(var i=0;i<12;i++){
		mt.options[i].text  = i + 1;
		mt.options[i].value = i + 1;
		if(i == strM){
			mt.options[i].selected = "selected";
		}
	}
}
function iniD(){
	var yr    = document.getElementById("year");
	var dt    = document.getElementById("day");
	var mt    = document.getElementById("month");
	var d_flg = 0;//年月が現在と同じか否か
	//月末の日数
	var lngD  = arrM[mt.options[mt.selectedIndex].value];
	if(mt.options[mt.selectedIndex].value == strM + 1 &&
	yr.options[yr.selectedIndex].value == strY){d_flg = 1;}

	dt.options.length = lngD;
	for(var i=0;i<lngD;i++){
		dt.options[i].text  = i + 1;
		dt.options[i].value = i + 1;
		if(i == strD - 1 && d_flg == 1){
			dt.options[i].selected = "selected";
		}
	}
	if(d_flg == 0){
		dt.options[0].selected = "selected";
	}
}

function iniH() {
	var hr = document.getElementById("hour");
	hr.options.length = 24;
	for(var i=0;i<24;i++){
		hr.options[i].text  = i;
		hr.options[i].value = i;
		if(i == strH){
			hr.options[i].selected = "selected";
		}
	}
}

function iniMM() {
	var mm = document.getElementById("minute");
	mm.options.length = 60;
	for(var i=0;i<60;i++){
		mm.options[i].text  = i;
		mm.options[i].value = i;
		if(i == strMM){
			mm.options[i].selected = "selected";
		}
	}
}

