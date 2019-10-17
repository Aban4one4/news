<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>新闻中国</title>
   	<%@include file="../pages/params.jsp" %>
	
	<link rel="stylesheet" type="text/css" href="css/shownew.css" />
	<link rel="stylesheet" type="text/css" href="css/login.css" />
	
	<script type="text/javascript" src="js/jquery-1.9.1.js" charset="utf-8"></script>
	<script type="text/javascript" src="js/login.js" charset="utf-8"></script>
  </head>
  
 <body>
	<div id="bg" class="bg" style="display:none;"></div>
	<div id="header">
    	<div id="top_login">
    		<div class="top_login_left">
					<ul>
						<li><a href="javascript:void(0);"
							onclick="SetHome(this,'http://www.hao23.com');">设为首页</a>
						</li>
						<li><a href="javascript:void(0);"
							onclick="AddFavorite('Navy新闻网',location.href)">点击收藏</a>
						</li>
						<li><a href="javascript:writerToLetter()" title="">联系我们</a>
						</li>
					</ul>
			</div>
    		<div id="top_login_right">
	    		<c:if test="${not empty currentLoginUser}">
    				<font color="#FF0000" size="2">当前用户：${currentLoginUser.uname }</font>&nbsp;&nbsp;
    				<a href="javascript:exitInfo()"><font color="#FF0000" size="2">注销</font></a>
    			</c:if>
    			
    			<c:if test="${empty currentLoginUser }">
    				<a href="javascript:showlogin()"><font color="#FF0000" size="2">请登录</font></a>
					&nbsp;&nbsp;
					<a href="javascript:showzc()"><font color="#FF0000" size="2">免费注册</font></a>
    			</c:if>
			</div>
        </div>
        <div id="nav">
        	<div id="logo"><img src="images/logo.jpg" /></div>
            <div><img src="images/a_b01.gif" style="margin:10px 0px 0px 2px;"/></div>
        </div>
    </div>
    <div id="container">
        <div class="content">
            <div class="new_header">
                <h1>${looknews.title }</h1>
                <span class="new_header_left">作者：${looknews.author }</span>
                <span class="new_header_right">发布日期：${looknews.createDate }</span>
            </div>
            <br />
            <div class="new_content">
            	<c:if test="${not empty looknews.pic }">
            		<img src="${looknews.pic }" title="${looknews.title }" width="620px" height="340px"/>
            	</c:if>
            	<br />
            	${looknews.content }
            </div>
             <br />
        </div>
        <br />
        <div class="picnews">
        	<ul>
        		<c:if test="${not empty picNews }">
	           		<c:forEach items="${picNews}" var="item">
	            		<li><img src="${item.pic }" width="248" height="160" title="${item.title }"/><a href="#">${item.title10 }</a></li>
	            	</c:forEach>
            	</c:if>
            </ul>
        </div>
    </div>
    <div id="friend">
    	<div class="friend_t"><img src="images/friend_ico.gif" /></div>
        <div class="friend_list">
        	<ul>
            	<li><a href="#">中国政府网</a></li>
                <li><a href="#">中国政府网</a></li>
                <li><a href="#">中国政府网</a></li>
                <li><a href="#">中国政府网</a></li>
                <li><a href="#">中国政府网</a></li>
                <li><a href="#">中国政府网</a></li>
                <li><a href="#">中国政府网</a></li>
            </ul>
        </div>
    </div>
    <%@include file="../pages/footer.jsp" %> 
    <%@include file="../pages/loginInfo.jsp" %> 
    <%@include file="../pages/registerInfo.jsp" %>
</body>
</html>
