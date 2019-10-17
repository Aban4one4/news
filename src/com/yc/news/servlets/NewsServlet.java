package com.yc.news.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yc.news.biz.INewsBiz;
import com.yc.news.biz.ITopicBiz;
import com.yc.news.biz.impl.NewsBizImpl;
import com.yc.news.biz.impl.TopicBizImpl;
import com.yc.news.entity.News;
import com.yc.news.entity.Topic;
import com.yc.news.utils.PageUtil;
import com.yc.news.utils.UploadUtil;

@SuppressWarnings("serial")
public class NewsServlet extends HttpServlet {
	private PrintWriter out;
	private JSONArray json=null;
	private JSONObject jb=null;
	private HttpSession session;

	private ITopicBiz topicBiz=new TopicBizImpl();
	private INewsBiz newsBiz=new NewsBizImpl();

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op=request.getParameter("op");

		response.setContentType("text/html;charset=utf-8");
		out=response.getWriter();
		session=request.getSession();

		if("addNews".equals(op)){ //添加新闻
			addNews(request,response);
		}else if("delNews".equals(op)){ //删除新闻
			delNews(request,response);
		}else if("overPage".equals(op)){ //翻页
			overPage(request,response); 
		}else if("showUpdateNews".equals(op)){ //显示要更新的新闻
			showUpdateNews(request,response);
		}else if("updateNews".equals(op)){ //修改新闻
			updateNews(request,response);
		}else if("look".equals(op)){ //查看新闻
			look(request,response);
		}else if("findNewsByTid".equals(op)){ //根据新闻类型查看新闻
			findNewsByTid(request,response); 
		}else if("frontOverPage".equals(op)){ //前台分页查询
			frontOverPage(request,response);
		}
	}

	//前台分页查询
	private void frontOverPage(HttpServletRequest request,HttpServletResponse response) {
		String flag=request.getParameter("flag").trim();
		String tid=request.getParameter("tid").trim();

		PageUtil pageUtil=null;
		if(session.getAttribute("frontPageUtil")==null){
			pageUtil=new PageUtil();
			pageUtil.setPageSize(25);
		}else{
			pageUtil=(PageUtil) session.getAttribute("frontPageUtil");
		}
		if("0".equals(tid)){
			pageUtil.setTotalSize(newsBiz.getTotal(null));
		}else{
			pageUtil.setTotalSize(newsBiz.getTotal(tid));
		}
		
		if("1".equals(flag)){  //首页
			pageUtil.setPageNo(1);
		}else if("2".equals(flag)){ //上一页
			pageUtil.setPageNo( pageUtil.getProPageNo() );
		}else if("3".equals(flag)){ //下一页
			pageUtil.setPageNo( pageUtil.getNextPageNo() );
		}else if("4".equals(flag)){ //末页
			pageUtil.setPageNo( pageUtil.getTotalPages() );
		}

		session.setAttribute("frontPageUtil", pageUtil);
		
		List<News> list=null;
		if("0".equals(tid)){
			list=newsBiz.getHeadNews(null,pageUtil.getPageNo(),pageUtil.getPageSize());
		}else{
			list=newsBiz.getHeadNews(tid,pageUtil.getPageNo(),pageUtil.getPageSize());
		}

		jb=new JSONObject();

		json=JSONArray.fromObject(list);
		jb.put("newsInfo",json);

		json=JSONArray.fromObject(pageUtil);
		jb.put("pageInfo",pageUtil);

		out.print(jb.toString());
		out.flush();
		out.close();
	}

	//根据新闻类型查看新闻
	private void findNewsByTid(HttpServletRequest request,HttpServletResponse response) {
		String tid=request.getParameter("tid");

		PageUtil pageUtil=null;
		if(session.getAttribute("frontPageUtil")==null){
			pageUtil=new PageUtil();
			pageUtil.setPageSize(25);
		}else{
			pageUtil=(PageUtil) session.getAttribute("frontPageUtil");
		}
		
		List<News> list=null;
		if("0".equals(tid.trim())){
			pageUtil.setTotalSize(newsBiz.getTotal(null));
			list=newsBiz.getHeadNews(null,pageUtil.getPageNo(),pageUtil.getPageSize());
		}else{
			pageUtil.setTotalSize(newsBiz.getTotal(tid));
			list=newsBiz.getHeadNews(tid,pageUtil.getPageNo(),pageUtil.getPageSize());
		}
		
		pageUtil.setPageNo(pageUtil.getPageNo());
		session.setAttribute("frontPageUtil", pageUtil);
		
		jb=new JSONObject();

		json=JSONArray.fromObject(list);
		jb.put("newsInfo",json);

		json=JSONArray.fromObject(pageUtil);
		jb.put("pageInfo",pageUtil);

		out.print(jb.toString());
		out.flush();
		out.close();
	}

	//查看新闻
	private void look(HttpServletRequest request, HttpServletResponse response) {
		String nid=request.getParameter("nid");

		News news=newsBiz.getNews(nid);
		try {
			if(news!=null){
				session.setAttribute("looknews",news);
				response.sendRedirect("front/shownews.jsp");
			}else{
				response.sendRedirect("index.html");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//修改新闻
	private void updateNews(HttpServletRequest request,HttpServletResponse response) {
		String nid=request.getParameter("nid");
		UploadUtil uploadUtil=new UploadUtil();

		try {
			Map<String,String> map=uploadUtil.upload(JspFactory.getDefaultFactory().getPageContext(this,request,response,null,true,1024,true));

			if(newsBiz.updateNews(map.get("tid"),map.get("title"),map.get("author"),map.get("createDate"),map.get("pic"),map.get("content"),nid)){
				PageUtil pageUtil=null;
				if(session.getAttribute("backPageUtil")==null){
					pageUtil=new PageUtil();
					pageUtil.setPageSize(15);
					int total=newsBiz.getTotal(null);
					pageUtil.setTotalSize(total);
				}else{
					pageUtil=(PageUtil) session.getAttribute("backPageUtil");
					pageUtil.setTotalSize( pageUtil.getTotalSize()+1 );
				}

				pageUtil.setPageNo(pageUtil.getPageNo());
				session.setAttribute("backPageUtil", pageUtil);

				List<News> newsInfo=newsBiz.getNewsHead(null,pageUtil.getPageNo(),pageUtil.getPageSize());
				session.setAttribute("newsInfo",newsInfo);
				out.print("<script>alert('新闻信息修改成功...');location.href='back/index.html';</script>");
			}else{
				out.print("<script>alert('新闻信息修改失败...');location.href='back/index.html';</script>");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} 

	}

	private void showUpdateNews(HttpServletRequest request,HttpServletResponse response) {
		//查询所有的新闻类型数据
		List<Topic> list=topicBiz.getAllTopic();
		session=request.getSession();
		session.setAttribute("newsTopics",list);

		String nid=request.getParameter("nid");
		News news=newsBiz.getNews(nid);
		session.setAttribute("updateNews",news);

		if(news!=null){
			out.print("1");
		}else{
			out.print("0");
		}
		out.flush();
		out.close();
	}

	private void overPage(HttpServletRequest request,HttpServletResponse response) {
		String flag=request.getParameter("flag");

		PageUtil pageUtil=null;
		if(session.getAttribute("backPageUtil")==null){
			pageUtil=new PageUtil();
			pageUtil.setPageSize(15);
			int total=newsBiz.getTotal(null);
			pageUtil.setTotalSize(total);
		}else{
			pageUtil=(PageUtil) session.getAttribute("backPageUtil");
		}

		if("1".equals(flag)){  //首页
			pageUtil.setPageNo(1);
		}else if("2".equals(flag)){ //上一页
			pageUtil.setPageNo( pageUtil.getProPageNo() );
		}else if("3".equals(flag)){ //下一页
			pageUtil.setPageNo( pageUtil.getNextPageNo() );
		}else if("4".equals(flag)){ //末页
			pageUtil.setPageNo( pageUtil.getTotalPages() );
		}

		session.setAttribute("backPageUtil", pageUtil);
		List<News> list=newsBiz.getNewsHead(null,pageUtil.getPageNo(),pageUtil.getPageSize());

		jb=new JSONObject();

		json=JSONArray.fromObject(list);
		jb.put("newsInfo",json);

		json=JSONArray.fromObject(pageUtil);
		jb.put("pageInfo",pageUtil);

		out.print(jb.toString());
		out.flush();
		out.close();
	}

	//删除新闻
	private void delNews(HttpServletRequest request,HttpServletResponse response) {
		String nid=request.getParameter("nid");
		if(newsBiz.delNews(nid)){
			PageUtil pageUtil=null;
			if(session.getAttribute("backPageUtil")==null){
				pageUtil=new PageUtil();
				pageUtil.setPageSize(15);

			}else{
				pageUtil=(PageUtil) session.getAttribute("backPageUtil");
			}

			pageUtil.setTotalSize(newsBiz.getTotal(null));
			pageUtil.setPageNo(pageUtil.getPageNo());
			session.setAttribute("backPageUtil", pageUtil);

			List<News> list=newsBiz.getNewsHead(null,pageUtil.getPageNo(),pageUtil.getPageSize());
			session.setAttribute("newsInfo",list); //更新session中的数据，以免用户在删除后，按刷新

			jb=new JSONObject();

			json=JSONArray.fromObject(list);
			jb.put("newsInfo",json);

			json=JSONArray.fromObject(pageUtil);
			jb.put("pageInfo",pageUtil);

			out.print(jb.toString());
			out.flush();
			out.close();
		}else{
			out.print("null");
			out.flush();
			out.close();
		}
	}

	//添加新闻
	private void addNews(HttpServletRequest request,HttpServletResponse response) {
		UploadUtil uploadUtil=new UploadUtil();
		
		try {
			Map<String,String> map=uploadUtil.upload(JspFactory.getDefaultFactory().getPageContext(this,request,response,null,true,1024,true));

			if(newsBiz.addNews(map.get("tid"),map.get("title"),map.get("author"),map.get("createDate"),map.get("pic"),map.get("content"))){
				//更新管理页面中的新闻数据
				PageUtil pageUtil=null;
				if(session.getAttribute("backPageUtil")==null){
					pageUtil=new PageUtil();
					pageUtil.setPageSize(15);
					int total=newsBiz.getTotal(null);
					pageUtil.setTotalSize(total);
				}else{
					pageUtil=(PageUtil) session.getAttribute("backPageUtil");
					pageUtil.setTotalSize( pageUtil.getTotalSize()+1 );
				}

				pageUtil.setPageNo(pageUtil.getPageNo());
				session.setAttribute("backPageUtil", pageUtil);

				List<News> newsInfo=newsBiz.getNewsHead(null,pageUtil.getPageNo(),pageUtil.getPageSize());
				session.setAttribute("newsInfo",newsInfo);

				out.print("<script>alert('新闻信息添加成功...');location.href='back/index.html';</script>");
			}else{
				out.print("<script>alert('新闻信息添加失败...');location.href='back/index.html';</script>");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
}
