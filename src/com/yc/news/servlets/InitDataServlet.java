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
import com.yc.news.biz.ITopicBiz;
import com.yc.news.biz.impl.NewsBizImpl;
import com.yc.news.biz.impl.TopicBizImpl;
import com.yc.news.entity.News;
import com.yc.news.entity.Topic;
import com.yc.news.utils.PageUtil;

@SuppressWarnings("serial")
public class InitDataServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		//获取新闻首页的所有数据
		
		//1.获取新闻类型信息
		ITopicBiz topicBiz=new TopicBizImpl();
		List<Topic> topics=topicBiz.getAllTopic();
		session.setAttribute("topics",topics);
		
		INewsBiz newsBiz=new NewsBizImpl();
		List<News> guoneiNews=newsBiz.getNewsHead("国内",1,11);
		session.setAttribute("guoneiNews", guoneiNews);
		
		List<News> guojiNews=newsBiz.getNewsHead("国际",1,11);
		session.setAttribute("guojiNews", guojiNews);

		List<News> yuleNews=newsBiz.getNewsHead("娱乐",1,11);
		session.setAttribute("yuleNews", yuleNews);
		
		PageUtil pageUtil=new PageUtil();
		pageUtil.setPageSize(25);
		int total=newsBiz.getTotal(null);
		pageUtil.setTotalSize(total);
		session.setAttribute("frontPageUtil",pageUtil);
		
		List<News> newsInfo=newsBiz.getNewsHead(null,1,25);
		session.setAttribute("frontNewsInfo", newsInfo);
		
		List<News> picNews=newsBiz.getNewsHeadPic(null,1,4);
		session.setAttribute("picNews", picNews);
		
		PrintWriter out=response.getWriter();
		out.print(1);
		out.flush();
		out.close();
	}

}
