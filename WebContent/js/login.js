//设为首页
function SetHome(obj,url){
	try{
		obj.style.behavior='url(#default#homepage)';
		obj.setHomePage(url);
	}catch(e){
		if(window.netscape){
			try{
				netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
			}catch(e){
				alert("抱歉，此操作被浏览器拒绝！\n\n请在浏览器地址栏输入about:config并回车然后将[signed.applets.codebase_principal_support]设置为'true'");
			}
		}else{
			alert("抱歉，您所使用的浏览器无法完成此操作。\n\n您需要手动将【"+url+"】设置为首页。");
		}
	}
}

//点击收藏
function AddFavorite(title,url){
	try{
		window.external.addFavorite(url,title);
	}catch(e){
		try{window.sidebar.addPanel(title,url,"");
		}catch(e){
			alert("抱歉，您所使用的浏览器无法完成此操作。\n\n加入收藏失败，请使用Ctrl+D进行添加");
		}
	}
}


//用户登录
function showlogin(){
	$("#smallpage #uname").val("");
	$("#smallpage #pwd").val("");
	$("#unamep").html("请输入昵称或邮箱").css("color","#CC6666");
	$("#pwdp").html("请输入您的密码").css("color","#CC6666");
	$("#zcpages").hide();
	$("#loginpages").mywin({left:"center",top:"center"});
	$("#uname").select();
	$(".bg").fadeIn("3000","linear");
}

//登陆框隐藏
function hidenloginpage() {
	var text = $("#loginpages").val();
	$("#loginpages").hide();
	$(".bg").hide();
}

//注册显示
function showzc(){
	$("#zcuname").val("");
	$("#zcpwd").val("");
	$("#zcpwdagain").val("");
	$("#zcemail").val("");
	$("#zccode").val("");

	$("#zcunamep").html("请输入您的昵称或注册邮箱(至少6位)").css("color","#CC6666");
	$("#zcpwdp").html("请输入您的登录密码(至少6位)").css("color","#CC6666");
	$("#zcpwdtwop").html("请重复输入您的登录密码(至少6位)").css("color","#CC6666");
	$("#zcemailp").html("请输入您的邮箱地址").css("color","#CC6666");
	$("#zccodep").html("请输入验证码").css("color","#CC6666");
	$("#loginpages").hide();
	$("#zcpages").mywin({left:"center",top:"center"});$("#player5").hide();$("#setswfembed").hide();
	$(".bg").fadeIn("3000","linear");
	$("#uname").select();
}

//注册框隐藏
function hidenzcpage() {
	var text = $("#zcpages").val();
	$("#zcpages").hide();
	$(".bg").hide();
}

//登陆用户名验证
function checkloginuname() {
	var uname = $("#uname").val();
	if ("" == uname || uname == null) {
		$("#unamep").html("用户名不能为空...").css("color", "#F00");
		return false;
	} else {
		$("#unamep").html("用户名格式正确...").css("color", "#0F0");
		return true;
	}
}

//登陆密码验证
function checkloginpwd() {
	var pwd = $("#pwd").val();
	if ("" == pwd || pwd == null) {
		$("#pwdp").html("您的密码不能为空...").css("color", "#F00");
		return false;
	} else {
		$("#pwdp").html("密码格式正确...").css("color", "#0F0");
		return true;
	}
}

//验证注册用户名
function checkzcuname() {
	var uname = $("#zcuname").val();
	var reg = /^([a-zA-Z0-9\u4E00-\u9FA5_-]{2,12})/;
	if (uname.match(reg)) {
		$.post("registerServlet?time="+new Date().getTime(), {op:"checkUserName",uname: uname},function(data) {
			if (parseInt(data)==1) {
				$("#zcunamep").html("用户名已经存在...").css("color", "#F00");
				return false;
			} else {
				$("#zcunamep").html("该名称可以使用...").css("color", "#0F0");
				return true;
			}
		});
		return true;
	} else {
		$("#uname").val("");
		$("#zcunamep").html("用户名格式不正确...").css("color", "#F00");
		return false;
	}
}

//验证注册密码
function checkzcpwd() {
	var pwd = $("#zcpwd").val();
	var reg = /^([a-zA-Z0-9_-]{6,20})/;
	if (pwd.match(reg)) {
		$("#zcpwdp").html("可以使用该密码...").css("color", "#0F0");
		return true;
	} else {
		$("#zcpwd").val("");
		$("#zcpwdp").html("密码不合格...").css("color", "#F00");
		return false;
	}
}

//验证重复密码
function checkzcpwdagain() {
	var pwdagain = $("#zcpwdagain").val();
	var pwd = $("#zcpwd").val();
	if (pwdagain == pwd && pwd != "" && pwd != null) {
		$("#zcpwdtwop").html("密码重复确认正确...").css("color", "#0F0");
		return true;
	} else {
		$("#zcpwdtwop").html("密码不一致请重新输入...").css("color", "#F00");
		$("#zcpwdagain").val("");
		return false;
	}
}

//验证邮箱
function checkzcemail() {
	var zcemail = $("#zcemail").val();
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
	if (zcemail.match(reg)) {
		$.post("registerServlet?time="+new Date().getTime(), {op: "checkemail",email: zcemail},function(data) {
			if (parseInt(data)==1) {
				$("#zcemailp").html("该邮箱已被注册...").css("color", "#F00");
				$("#zcemail").val("");
				return false;
			} else {
				$("#zcemailp").html("该邮箱可以使用...").css("color", "#0F0");
				return true;
			}
		});
		return true;
	} else {
		$("#zcemailp").html("邮箱格式不正确...").css("color", "#F00");
		$("#zcemail").val("");
		return false;
	}
}

var mytime;
var i=120;
function sendCodeInfo(){
	var zcemail = $("#zcemail").val();
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
	if (zcemail.match(reg)) {
		$.post("registerServlet?time="+new Date().getTime(),{op:"sendCode",email: zcemail},function(data){
			if(parseInt(data)==1){
				$("#zccodep").html("验证码发送成功，请登录邮箱查看（两分钟内有效）...").css("color","#0F0");
				$("#sendCode").attr("disabled",true);
				mytime=setInterval("chengeSendCode()",1000);
			}else{
				$("#zccodep").html("验证码发送失败...").css("color", "#F00");	
			}
		});
	}else {
		$("#zcemailp").html("邮箱格式不正确...").css("color", "#F00");
		$("#zcemail").val("");
	}
}

function chengeSendCode(){
	var obj=$("#sendCode");
	obj.val(i+"秒");
	i--;
	if(i<=0){
		obj.attr("disabled",false).val("重新发送");
		i=120;
		clearInterval(mytime);
	}
}


function checkzccode(){
	var code = $("#zccode").val();
	var reg = /^[0-9]{6}$/;
	if(code.match(reg)){
		$("#zccodep").html("验证码格式正确...").css("color", "#0F0");
		return true;
	}else{
		$("#zccodep").html("验证码格式不正确...").css("color", "#F00");
		return false;
	}
}

//真正开始注册
function userzc(){
	var uname=checkzcuname();
	var pwd=checkzcpwd();
	var pwds=checkzcpwdagain();
	var email=checkzcemail();
	var code=checkzccode();

	if(uname && pwd && pwds && email && code ){
		var name=$.trim( $("#zcuname").val() );
		var upwd=$.trim( $("#zcpwd").val() );
		var uemail=$.trim( $("#zcemail").val() );
		var ucode=$.trim( $("#zccode").val() );

		$.post("registerServlet?time="+new Date().getTime(),{op:"register",uname:name,pwd:upwd,email:uemail,code:ucode },function(data){
			var num=parseInt($.trim(data));
			if(num==1){
				$("#zccodep").html("验证码已经过期...").css("color", "#F00");
			}else if(num==2){
				$("#zccodep").html("验证码错误...").css("color", "#F00");
			}else if(num==3){
				alert("恭喜你，注册成功....");
				$("#zcuname").val("");
				$("#zcpwd").val("");
				$("#zcemail").val("");
				$("#zccode").val("");
				$("#pwdagain").val("");
				$("#zcpages").hide();
				$(".bg").hide();
			}else if(num==4){
				$("#zccodep").html("注册失败...").css("color", "#F00");
			}
		});
	}else{
		alert("您输入的信息有误，请确认....");
		return;
	}
}

//用户登录
function userlogin(){
	var isuname=checkloginuname();
	var ispwd=checkloginpwd();
	if(isuname && ispwd){
		var uname=$.trim( $("#uname").val() );
		var pwd=$.trim( $("#pwd").val() );
		
		$.post("registerServlet?time="+new Date().getTime(),{op:"userLogin",uname:uname,pwd:pwd},function(data){
			var num=parseInt( $.trim(data) );
			if(num==1){
				alert("用户名和密码不能为空....");
			}else if(num==2){
				alert("用户名或密码错误...");
			}else if(num==3){
				//修改标题
				$("#top_login_right").html('<font color="#FF0000" size="2">当前用户：'+uname+
						'</font>&nbsp;&nbsp;&nbsp;<a href="javascript:exitInfo()"><font color="#FF0000" size="2">注销</font></a>');
				$("#loginpages").hide(); //隐藏登录界面
				$(".bg").hide();
			}
		});
	}
	return;
}

//用户注销
function exitInfo(){
	$.post("registerServlet?time="+new Date().getTime(),{op:"exitLogin"},function(data){
		if( $.trim(data)=="1" ){
			$("#top_login_right").html('<a href="javascript:showlogin()"><font color="#FF0000" size="2">请登录</font></a>&nbsp;&nbsp;'+
					'&nbsp;<a href="javascript:showzc()"><font color="#FF0000" size="2">免费注册</font></a>');
		}else{
			return;
		}
	});
}

//重新获取验证码
function changeVilidateCode(obj){
	var timenow=new Date().getTime();
	obj.src="front/pages/valiCodeImg.jsp?d="+timenow;
}


//根据新闻类型
function findNewsByTid(tid){
	var tids=$("#class_month a");
	for(var i=0;i<tids.length;i++){
		tids[i].removeAttribute("class");
	}
	$("#t"+tid).attr("class","selected");
	
	$.post("newsServlet?time="+new Date().getTime(),{op:"findNewsByTid",tid:tid},function(data){
		var str="";
		$.each(data.newsInfo,function(index,item){
			if(index%5==0&&index!=0){
				str+="<li class=\"space\">";
			}
			str+="<li><a href=\"newsServlet?op=look&nid="+item.nid+"\" title=\""+item.title+"\" target=\"_blank\">"+item.title20+"_"+item.tname;
			if(item.pic!=""&&item.pic!=null){
				str+="<font color=\"#FCC006\"> [有图]</font>";
			}
			str+="</a><span>"+item.createDate+"</span></li>";
		});
		str+="<li class=\"space\"></li>";
		$("#front_news").html(str);
		var obj=data.pageInfo;
		$("#front_page").html("当前页数[<span id=\"now_pageno\">"+obj.pageNo+"</span>/<span id=\"now_totalPages\">"+obj.totalPages+"</span>] 总共"+obj.totalSize+"条");
	},"json");
}

//翻页
function frontOverPage(num){
	if($.trim(num)==1||$.trim(num)==2){
		var now=$.trim($("#now_pageno").text());
		if(now==1){
			return;
		}
	}
	
	if($.trim(num)==4||$.trim(num)==3){
		var now=$.trim($("#now_pageno").text());
		var end=$.trim($("#now_totalPages").text());
		if(now==end){
			return;
		}
	}
	
	var tid=$("#class_month a[class='selected']").attr("id");
	tid=tid.replace("t","");
	
	$.post("newsServlet?time="+new Date().getTime(),{op:"frontOverPage",flag:num,tid:tid},function(data){
		var str="";
		$.each(data.newsInfo,function(index,item){
			if(index%5==0&&index!=0){
				str+="<li class=\"space\">";
			}
			str+="<li><a href=\"newsServlet?op=look&nid="+item.nid+"\" title=\""+item.title+"\" target=\"_blank\">"+item.title20+"_"+item.tname;
			if(item.pic!=""&&item.pic!=null){
				str+="<font color=\"#FCC006\"> [有图]</font>";
			}
			str+="</a><span>"+item.createDate+"</span></li>";
		});
		str+="<li class=\"space\"></li>";
		$("#front_news").html(str);
		var obj=data.pageInfo;
		$("#front_page").html("当前页数[<span id=\"now_pageno\">"+obj.pageNo+"</span>/<span id=\"now_totalPages\">"+obj.totalPages+"</span>] 总共"+obj.totalSize+"条");
	},"json");
}

$.fn.mywin=function(position){
	if (position&&position instanceof Object){
		var positionleft=position.left;
		var positiontop=position.top;

		var currentwin=this;
		var mywidth =currentwin.outerWidth(true);
		var myheight = currentwin.outerHeight(true);

		var left=0;
		var top=0;
		var width=0;
		var height=0;
		var scrollleft=0;
		var scrolltop=0;


		function getWinInfo(){
			width=$(window).width();
			height=$(window).height();
			scrollleft=$(window).scrollLeft();
			scrolltop=$(window).scrollTop();
		}

		function calleft(positionleft,scrollleft,width,mywidth){
			if(positionleft!=""&&typeof(positionleft)=="string"){
				if(positionleft=="center"){
					left=scrollleft+(width-mywidth)/2;
				}else if(positionleft=="left"){
					left=scrollleft;
				}else if (positionleft == "right"){
					left=scrollleft+width-mywidth;
				}else{
					left=scrollleft+(width-mywidth)/2;
				}
			}else if(positionleft != ""&&typeof(positionleft)=="number"){
				left=positionleft+scrollleft;
			}else{
				left=0;
			}

		}

		function caltop(positiontop,scrolltop,height,myheight){
			if(positiontop!=""&&typeof(positiontop)=="string"){
				if(positiontop=="center"){
					top=scrolltop+(height-myheight)/2;
				}else if(positiontop == "top"){
					top=scrolltop;
				}else if(positiontop=="bottom"){
					top = scrolltop+height-myheight;
				}else{
					top=scrolltop+(height-myheight)/2;
				}
			}else if(positiontop!=""&&typeof(positiontop)=="number"){
				top=positiontop+scrolltop;
			}else{
				top=0;
			}
		}

		getWinInfo();
		calleft(positionleft, scrollleft,width,mywidth);
		caltop(positiontop,scrolltop,height,myheight);

		$(window).scroll(function(){
			getWinInfo();
			calleft(positionleft,scrollleft,width,mywidth);
			caltop(positiontop,scrolltop,height,myheight);
			currentwin.css("left",left).css("top",top);
		});

		$(window).resize(function(){
			getWinInfo();
			calleft(positionleft, scrollleft, width, mywidth);
			caltop(positiontop, scrolltop, height, myheight);
			currentwin.animate({left : left,top : top}, 300);
		});

		currentwin.css("left",left).css("top", top).fadeIn("slow");
	}
	return this;
}