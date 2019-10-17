<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="loginpages" style="z-index:99999;">
    <div id="smallpage">
        <div id="loginlogo">
            <div style="float:left;" class="loginee">
                <span id="ids">登录</span>
            </div>
            <div style="float:right;">
                <a href="javascript:hidenloginpage()">
                <img style="width:24px; height:24px;" src="images/close_delete_2.png" />
                </a>
            </div>
        </div>
        <hr style="border:1px solid #A6C9E1;width:100%" />
        <form action="" method="post">
            <dl>
                <div style="height:17px; overflow:hidden"></div>
                <dd>
                    <span class="titles">用户名：</span>
                    <input type="text" name="uname"	id="uname" onblur="checkloginuname()" /><span id="unamep">请输入昵称或邮箱</span>
                </dd>
                <br />
                <div style="height:17px; overflow:hidden"></div>
                <dd>
                    <span class="titles">密&nbsp;&nbsp;&nbsp;&nbsp;码：</span>
                    <input type="password" name="pwd" id="pwd" onblur="checkloginpwd()" /><span id="pwdp">请输入您的密码</span>
                </dd>
            </dl>
            <div style="margin-left:70px; margin-bottom:10px;">
                <img alt="登录" src="images/loginss.png" onClick="userlogin()" style="margin-top:10px;float:left"/>
                <span style="margin-left:30px;float:left;margin-top:25px;">如果没有账号 <a href="javascript:showzc()">免费注册</a></span>
            </div>
        </form>
    </div>
</div>