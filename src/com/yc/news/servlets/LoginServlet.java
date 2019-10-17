package com.yc.news.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yc.news.biz.INewsBiz;
import com.yc.news.biz.IUserInfoBiz;
import com.yc.news.biz.impl.NewsBizImpl;
import com.yc.news.biz.impl.UserInfoBizImpl;
import com.yc.news.entity.News;
import com.yc.news.entity.UserInfo;
import com.yc.news.utils.PageUtil;
import com.yc.news.utils.StringUtil;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	private PrintWriter out;
	private HttpSession session;
	private IUserInfoBiz userInfoBiz=new UserInfoBizImpl();
	private INewsBiz newsBiz=new NewsBizImpl();

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op=request.getParameter("op");

		out=response.getWriter();
		session=request.getSession();

		if("adminLogin".equals(op)){ //管理员登录
			adminLogin(request,response);
		}else if("exitLogin".equals(op)){ //用户退出
			exitLogin(request,response);
		}

	}

	//退出登录
	private void exitLogin(HttpServletRequest request,HttpServletResponse response) {
		session.removeAttribute("currentLoginAdmin");
		session.invalidate();
		out.print(1);
		out.flush();
		out.close();
	}

	private void adminLogin(HttpServletRequest request,	HttpServletResponse response) {
		String uname=request.getParameter("uname"); //取用户名
		String pwd=request.getParameter("pwd"); //取密码
		String ucode=request.getParameter("vcode"); //取验证码
		
		String code=(String) session.getAttribute("rand"); //取系统生存的验证码
		
		if(StringUtil.isNullorEmpty(ucode) || StringUtil.isNullorEmpty(pwd) || StringUtil.isNullorEmpty(uname)){
			out.print("1"); //登录信息不完整
		}else{
			if(!code.equals(ucode)){
				out.print("2"); //验证码错误
			}else{
				//验证用户名和密码是否正确
				UserInfo uf=userInfoBiz.loginUserInfo(uname, pwd);
				if(uf!=null){ //如果根据指定的用户名和密码，能找到对应的用户
					session.setAttribute("currentLoginAdmin", uf);
					
					
					//将主页要显示的新闻信息查询出来
					PageUtil pageUtil=new PageUtil();
					pageUtil.setPageSize(15);
					
					int total=newsBiz.getTotal(null);
					pageUtil.setTotalSize(total);
					
					session.setAttribute("backPageUtil",pageUtil);
					
					List<News> newsInfo=newsBiz.getNewsHead(null,1,15);
					session.setAttribute("newsInfo",newsInfo);
					
					out.print("4"); //登录成功
				}else{
					out.print("3"); //用户名或密码错误
				}
			}
		}
		out.flush();
		out.close();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
}
