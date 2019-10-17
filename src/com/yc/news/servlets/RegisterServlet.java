package com.yc.news.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yc.news.biz.IUserInfoBiz;
import com.yc.news.biz.impl.UserInfoBizImpl;
import com.yc.news.entity.UserInfo;
import com.yc.news.timers.MyTimer;
import com.yc.news.utils.MD5Encryption;
import com.yc.news.utils.MailUtil;
import com.yc.news.utils.StringUtil;

@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {
	private IUserInfoBiz userInfoBiz=new UserInfoBizImpl();
	private PrintWriter out;
	private HttpSession session;
	private MyTimer mytimer;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op=request.getParameter("op");
		
		out=response.getWriter();
		session=request.getSession();
		
		if("checkUserName".equals(op)){ //检查用户名
			checkUserName(request,response); 
		}else if("checkemail".equals(op)){ //检查邮箱
			checkemail(request,response); 
		}else if("sendCode".equals(op)){ //发送验证码
			sendCode(request,response);  
		}else if("register".equals(op)){ //用户注册
			register(request,response); 
		}else if("userLogin".equals(op)){ //用户登录
			userLogin(request,response);
		}else if("exitLogin".equals(op)){  //用户注销
			exitLogin(request,response);
		}
	}
	
	//前台用户注销
	private void exitLogin(HttpServletRequest request,HttpServletResponse response) {
		session.setAttribute("currentLoginUser",null);
		session.removeAttribute("currentLoginUser");
		out.print(1);
	}

	//用户登录
	private void userLogin(HttpServletRequest request,HttpServletResponse response) {
		//获取用户名和密码
		String uname=request.getParameter("uname");
		String pwd=request.getParameter("pwd");
		
		if(StringUtil.isNullorEmpty(uname)||StringUtil.isNullorEmpty(pwd)){
			out.print(1); //用户名或密码为空
		}else{
			pwd=MD5Encryption.createPassword(pwd);
			UserInfo userInfo=userInfoBiz.loginUserInfo(uname,pwd);
			if(userInfo!=null){ //说明根据指定的用户名和密码查到了用户
				//将当前用户存入session
				session.setAttribute("currentLoginUser",userInfo);
				out.print(3);
			}else{
				out.print(2); //用户名或密码错误
			}
		}
		
	}

	//注册
	private void register(HttpServletRequest request,HttpServletResponse response) {
		String uname=request.getParameter("uname");
		String pwd=request.getParameter("pwd");
		String email=request.getParameter("email");
		String ucode=request.getParameter("code");
		
		String code=(String) session.getAttribute("code");
		
		if(code==null || "".equals(code)){ //说明验证码已经过期
			out.print(1);
		}else{
			if(!code.equals(ucode)){ //如果用户输入的验证码不正确
				out.print(2);
			}else{
				//将数据添加到数据库
				pwd=MD5Encryption.createPassword(pwd);  //将密码加密
				if(userInfoBiz.addUserInfo(uname, pwd, email)){ //成功
					out.print(3);
					if(mytimer!=null){
						mytimer.closeTime(); //取消定时任务
					}
				}else{
					out.print(4);
				}
			}
		}
		out.flush();
		out.close();
	}

	//发送邮件
	private void sendCode(HttpServletRequest request,HttpServletResponse response) {
		String email=request.getParameter("email");
		MailUtil mailUtil=new MailUtil();
		
		Random rd=new Random();
		StringBuffer sf=new StringBuffer();
		for(int i=0;i<6;i++){
			sf.append( rd.nextInt(10) );
		}
		if(mailUtil.sendMail(email,"注册验证码",sf.toString())){
			//存入session
			session.setAttribute("code",sf.toString());
			
			//启用定时器，两分钟后清空session中的code
			mytimer=new MyTimer();
			mytimer.getTimer(session);
			
			//告诉前台，验证码发送成功
			out.print(1);
		}else{
			out.print(0);
		}
		out.flush();
		out.close();
	}

	//验证邮箱是否存在
	private void checkemail(HttpServletRequest request,HttpServletResponse response) {
		String email=request.getParameter("email");
		boolean bl=userInfoBiz.checkEmail(email);
		if(bl){
			out.print(1); //说明该用户名已经存在
		}else{
			out.print(0);
		}
		out.flush();
		out.close();
	}

	//验证用户是否重复的方法
	private void checkUserName(HttpServletRequest request,HttpServletResponse response) {
		String uname=request.getParameter("uname");
		boolean bl=userInfoBiz.checkUserName(uname);
		if(bl){
			out.print(1); //说明该用户名已经存在
		}else{
			out.print(0);
		}
		out.flush();
		out.close();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
}
