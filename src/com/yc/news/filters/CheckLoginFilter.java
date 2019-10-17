package com.yc.news.filters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckLoginFilter implements Filter {
	private String errorLogin="admin/login.jsp"; //默认
	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response=(HttpServletResponse)resp;
		response.setContentType("text/html;charset=utf-8");

		HttpSession session=request.getSession();

		String path=request.getContextPath();
		String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;


		if(session.getAttribute("currentLoginAdmin")==null){ //说明此用户没有成功登录过
			PrintWriter out=response.getWriter();
			out.print("<script>alert('请先登录...');location.href='"+basePath+errorLogin+"'</script>");
			out.flush();
			out.close();
		}else{
			//把请求和响应给下一个过滤器
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig config) throws ServletException {
		String temp=config.getInitParameter("errorLogin"); //取登录的配置路径
		if(temp!=null){
			errorLogin=temp;
		}
	}
}
