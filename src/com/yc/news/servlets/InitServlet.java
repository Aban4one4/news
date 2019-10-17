package com.yc.news.servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.news.utils.UploadUtil;

@SuppressWarnings("serial")
public class InitServlet extends HttpServlet {
	private File path;

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	@Override
	public void init() throws ServletException {
		String uploadPath="../dataInfo";

		if(this.getInitParameter("uploadPath")!=null){
			uploadPath=this.getInitParameter("uploadPath");
		}
		//如果用于保存上传图片的路径不存在，则先创建
		path= new File(this.getServletContext().getRealPath(""),uploadPath) ;  
		if (!path.exists()) {  
			path.mkdir();  
		}
		
		UploadUtil.PATH=uploadPath;

		//如果不显示调用父类方法，就不会有itemManager实例，因此会造成空指针  
		super.init();
	}
}
