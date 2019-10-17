<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<title>新闻中国</title>
  </head>
  
  <body>
  <center>
  	<img src="images/error1.jpg" />
  	<h2 style="color:red;font-family:'微软雅黑'">资源地址错误，请确认后重新搜索...</h2>
  </center>
  </body>
</html>
