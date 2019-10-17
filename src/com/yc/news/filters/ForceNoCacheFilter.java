package com.yc.news.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author navy
 * 页面缓存过滤器
 */
public class ForceNoCacheFilter implements Filter{
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		//清空页面缓存
		((HttpServletResponse) response).setHeader("Cache-Control","no-cache"); 
		((HttpServletResponse) response).setHeader("Pragma","no-cache"); 
		((HttpServletResponse) response).setDateHeader ("Expires", -1); 
		
		chain.doFilter(request, response); 
	}
	
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}
