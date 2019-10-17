package com.yc.news.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yc.news.biz.ITopicBiz;
import com.yc.news.biz.impl.TopicBizImpl;
import com.yc.news.entity.Topic;
import com.yc.news.utils.StringUtil;

@SuppressWarnings("serial")
public class TopicServlet extends HttpServlet {
	private PrintWriter out;
	private ITopicBiz topicBiz=new TopicBizImpl();
	private JSONArray json=null;
	private JSONObject jb=null;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op=request.getParameter("op");
		
		out=response.getWriter();
		
		if("showTopics".equals(op)){ //显示所有的新闻类型
			showTopics(request,response);
		}else if("addTopic".equals(op)){  //添加新闻类型
			addTopic(request,response); 
		}else if("updateTopic".equals(op)){  //修改新闻类型
			updateTopic(request,response); 
		}else if("delTopic".equals(op)){  //删除新闻类型
			delTopic(request,response); 
		}else if("getAllTopic".equals(op)){ //获取所有的新闻类型
			getAllTopic(request,response); 
		}
		
	}

	private void getAllTopic(HttpServletRequest request,HttpServletResponse response) {
		//查询所有的新闻类型数据
		List<Topic> list=topicBiz.getAllTopic();
		
		jb=new JSONObject();
		
		json=JSONArray.fromObject(list);
		jb.put("info",json);
		out.print(jb.toString());
		out.flush();
		out.close();
	}

	private void delTopic(HttpServletRequest request,HttpServletResponse response) {
		String tid=request.getParameter("tid");
		if(StringUtil.isNullorEmpty(tid)){
			out.print(0);
		}else{
			if(topicBiz.delTopic(tid)){
				out.print(1);
			}else{
				out.print(0);
			}
		}
		out.flush();
		out.close();
	}

	private void updateTopic(HttpServletRequest request,HttpServletResponse response) {
		String tname=request.getParameter("tname");
		String tid=request.getParameter("tid");
		if(StringUtil.isNullorEmpty(tname)||StringUtil.isNullorEmpty(tid)){
			out.print(0);
		}else{
			if(topicBiz.updateTopic(tname, tid)){
				out.print(1);
			}else{
				out.print(0);
			}
		}
		out.flush();
		out.close();
	}

	private void addTopic(HttpServletRequest request,HttpServletResponse response) {
		String tname=request.getParameter("tname");
		if(StringUtil.isNullorEmpty(tname)){
			out.print(0);
		}else{
			if(topicBiz.addTopic(tname)){
				out.print(1);
			}else{
				out.print(0);
			}
		}
		out.flush();
		out.close();
	}

	//显示所有的新闻类型
	private void showTopics(HttpServletRequest request,HttpServletResponse response) {
		int page=Integer.parseInt(request.getParameter("page")); //要显示页数
		int rows=Integer.parseInt(request.getParameter("rows")); //每页显示的行数
		
		//查询所有的新闻类型数据
		List<Topic> list=topicBiz.getAllTopic(page,rows);
		
		int total=topicBiz.getTotal(); //获取总记录条数
		
		jb=new JSONObject();
		jb.put("total",total);
		
		json=JSONArray.fromObject(list);
		jb.put("rows",json);
		
		out.print(jb.toString());
		
		out.flush();
		out.close();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
}
