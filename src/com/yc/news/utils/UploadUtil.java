package com.yc.news.utils;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.jsp.PageContext;

import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;

public class UploadUtil {
	public static String PATH; //保存图片的文件夹
	private static final String ALLOWED="gif,jpg,jpeg,png"; //可以上次的文件类型
	private static final String DENIED="exe,bat,jsp,html,com"; //不允许上次的文件类型
	private static final int TOTALMAXSIZE=20*1024*1024; //最多上传的大小
	private static final int SINGEMAXSIZE=1024*1024; //单个文件的最大大小
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

	private String fname; //文件名

	@SuppressWarnings("unchecked")
	public Map<String,String> upload(PageContext pagecontext) throws Exception{
		Map<String,String> map=new HashMap<String,String>();

		SmartUpload su=new SmartUpload();

		//初始化
		su.initialize(pagecontext);

		//设置参数
		su.setMaxFileSize(SINGEMAXSIZE);
		su.setTotalMaxFileSize(TOTALMAXSIZE);
		su.setAllowedFilesList(ALLOWED);
		su.setDeniedFilesList(DENIED);
		su.setCharset("utf-8");

		su.upload(); //调用SmartUpload里面的upload方法开始上传

		Request request=su.getRequest(); //重SmartUpload中获取用户请求的信息
		map.put("tid",request.getParameter("ntid"));
		map.put("title",request.getParameter("ntitle"));
		if(StringUtil.isNullorEmpty(request.getParameter("nauthor"))){
			map.put("author","匿名");
		}else{
			map.put("author",request.getParameter("nauthor"));
		}

		if(StringUtil.isNullorEmpty(request.getParameter("ncreatedate"))){
			map.put("createDate",sdf.format(new Date()));
		}else{
			map.put("createDate",request.getParameter("ncreatedate"));
		}
		map.put("content",request.getParameter("ncontent"));

		if(su.getFiles()!=null&&su.getFiles().getCount()>0){ //说明请求中含有文件
			Files fs=su.getFiles(); //取出请求中的所有文件信息
			Collection<File> col=fs.getCollection();

			String extname; //文件的扩展名
			String realpath; //存入服务器的路径 服务器所在的地址:8080/项目名/PATH/文件名
			String path="";
			
			for(File f:col){ //循环取出每一个上传的文件
				if( ! f.isMissing() ){ //判断上传的文件有没有丢失
					//获取上传的文件的后缀名
					extname=f.getFileExt();
					fname=new Date().getTime()+""+new Random().nextInt(1000)+"."+extname;//给上传的文件重新命名，以免重复 42342355535

					realpath=PATH+"/"+fname;

					//把上传的图片写入服务器
					f.saveAs(realpath,SmartUpload.SAVE_VIRTUAL);
					path+=realpath+",";
				}
			}
			if(path.indexOf(",")>0){
				path=path.substring(0,path.lastIndexOf(","));
			}
			map.put("pic",path); //往数据库写的路径
		}else{
			map.put("pic",null); //往数据库写的路径
		}

		return map;
	}

}
