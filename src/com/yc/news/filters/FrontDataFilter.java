package com.yc.news.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FrontDataFilter implements Filter {
	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response=(HttpServletResponse)resp;

		HttpSession session=request.getSession();

		if(session.getAttribute("topics")==null){ //说明此用户没有经过index.html
			response.sendRedirect("../index.html");
		}else{
			//把请求和响应给下一个过滤器
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig config) throws ServletException {

	}
}
