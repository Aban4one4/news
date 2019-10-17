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

public class CharacterEncoding implements Filter {
	private String encoding="UTF-8"; //默认
	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response=(HttpServletResponse)resp;
		
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		
		//把请求和响应给下一个过滤器
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		String temp=config.getInitParameter("encoding");
		if(temp!=null){
			encoding=temp;
		}
	}
}
