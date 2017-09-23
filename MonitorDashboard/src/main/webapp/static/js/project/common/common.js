
function getCurrDate(hasMonth,hasDay,hasHour) {
	var s = new Date();
	var year = s.getFullYear();
	var month = s.getMonth() + 1;
	var day = s.getDate();
	if (day < 10) {
		day = "0" + day;
	}
	var hour = s.getHours();
	var minutes = s.getMinutes();
	if (month < 10) {
		month = "0" + month;
	}
	if (hour < 10) {
		hour = "0" + hour;
	}
	if (minutes < 10) {
		minutes = "0" + minutes;
	}
	var time = year;
	if(hasMonth){
		time = time + "-" + month;
		if(hasDay){
			time = time + "-" + day;
		}
	}
	
	if(hasHour){
		time = time+" "+hour+":"+minutes;
	}
	return time;
}

function getCookie(name){
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg)){
		return unescape(arr[2]);
	}
	return null;
}
//s20
//h12
//d30
function getsec(str){
	var str1=str.substring(1,str.length)*1;
	var str2=str.substring(0,1);
	if (str2=="s"){
		return str1*1000;
	}else if (str2=="h"){
		return str1*60*60*1000;
	}else if (str2=="d"){
		return str1*24*60*60*1000;
	}
}

function setCookie(name,value,time)
{
	var strsec = getsec(time);
	var exp = new Date();
	exp.setTime(exp.getTime() + strsec*1);
	document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+"; path=/";
}

function delCookie(name){
	var date=new Date();
  date.setTime(date.getTime()-1*24*60*60*1000);
  document.cookie=name+"=''; expires="+date.toGMTString()+"; path=/";
}

function setSelectChecked(selectId, checkValue){  
  var select = document.getElementById(selectId);  
  for(var i=0; i<select.options.length; i++){  
      if(select.options[i].innerHTML == checkValue){  
          select.options[i].selected = true;  
          break;  
      }  
  }  
};

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
		if (r != null){
			return unescape(r[2]);
		}
		return null;
}

String.prototype.startWith=function(str){
	var reg=new RegExp("^"+str);
	return reg.test(this);
}
