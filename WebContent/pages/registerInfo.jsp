<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="zcpages">
    <div id="zcsmallpage">
        <div id="loginlogos">
            <div style="float:left;">
                <p id="ids">注册</p>
            </div>
            <div style="float:right;">
                <a href="javascript:hidenzcpage()"><img
                    style="width:25px; height:25px;" src="images/close_delete_2.png" />
                </a>
            </div>
        </div>
        <hr style="border:1px solid #A6C9E1;width:100%" />
        <form action="" method="post">
                <div style="height:17px;overflow:hidden"></div>
                <label class="labelInfo" for="uname">用户名称：</label>
                <input type="text" name="uname" id="zcuname" onblur="checkzcuname()" />
                <p id="zcunamep">请输入您的昵称或注册邮箱(至少6位)</p>
                <label class="labelInfo" for="pwd">输入密码：</label>
                <input type="password" name="pwd" id="zcpwd" onblur="checkzcpwd()" />
                <p id="zcpwdp">请输入您的登录密码(至少6位)</p>
                <label class="labelInfo" for="pwdagain">重复密码：</label>
                <input type="password" name="pwdagain" id="zcpwdagain" onblur="checkzcpwdagain()" />
                <p id="zcpwdtwop">请重复输入您的登录密码(至少6位)</p>
                <label class="labelInfo" for="zcemail">输入邮箱：</label>
                <input type="text" name="zcemail" id="zcemail" onblur="checkzcemail()" />
                <p id="zcemailp">请输入您的邮箱地址</p>
                <label class="labelInfo" for=zccode">验证码：</label>
                <input type="text" name="zccode" id="zccode" onblur="checkzccode()" style="font-size:14px; width:100px;" />&nbsp;&nbsp;&nbsp;&nbsp;
                
                <input type="button" value="发送验证码" id="sendCode" style="width:100px;" onclick="sendCodeInfo()">
                
                <p id="zccodep">请输入验证码</p>
            <div style="margin-left:30px;">
                <img alt="注册" src="images/zcss.png" onClick="userzc()" style="float:left"/>
                <span style="margin-left:20px;float:left;margin-top:15px;">如果已经有账号 <a href="javascript:showlogin()">马上登录</a></span> 
            </div>
        </form>
    </div>
  </div> 