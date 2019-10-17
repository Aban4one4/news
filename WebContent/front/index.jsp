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
	
	<link rel="stylesheet" href="css/main.css" />
	<link rel="stylesheet" href="css/login.css" />
	
	<script src="js/jquery-1.9.1.js" charset="utf-8"></script>
	<script src="js/login.js" charset="utf-8"></script>
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
        	
        <div id="nav">
        	<div id="logo"><img src="images/logo.jpg" /></div>
            <div><img src="images/a_b01.gif" style="margin:10px 0px 0px 2px;"/></div>
        </div>
        
       </div>
       
    </div>
    <div id="container">
    	<div class="sidebar">
        	<div class="side_list">
          		<img src="images/title_1.gif" />
                <ul class="left_new">
                	<c:forEach items="${guoneiNews}" var="item">
            			<li><a href="newsServlet?op=look&nid=${item.nid }" title="${item.title}" target="_blank">${item.title10 }</a></li>
            		</c:forEach>
                </ul>
            </div>
            <div class="side_list">
          		<img src="images/title_2.gif" />
                <ul class="left_new">
                	<c:forEach items="${guojiNews}" var="item">
            			<li><a href="newsServlet?op=look&nid=${item.nid }" title="${item.title}" target="_blank">${item.title10 }</a></li>
            		</c:forEach>
                </ul>
            </div>
            <div class="side_list">
          		<img src="images/title_3.gif" />
                 <ul class="left_new">
                	<c:forEach items="${yuleNews}" var="item">
            			<li><a href="newsServlet?op=look&nid=${item.nid }" title="${item.title}" target="_blank">${item.title10 }</a></li>
            		</c:forEach>
                </ul>
            </div>
        </div>
        <div class="main">
        	<div class="class_type">
            	<img src="images/class_type.gif"/>
           	</div>
            <div class="content">
            	<div class="class_date" id="class_month">
            		<a href="javascript:findNewsByTid('0')" class="selected" id="t0">全部</a>
            		<c:forEach items="${topics}" var="item">
            			<a id="t${item.tid }" href="javascript:findNewsByTid('${item.tid }')">${item.tname }</a>
            		</c:forEach>
               </div>
               <div class="classlist">
               		<ul id="front_news">
               			<c:if test="${not empty frontNewsInfo}">
               				<c:forEach items="${frontNewsInfo}" var="item" varStatus="i">
               					<c:if test="${(i.count-1)%5==0 and i.count!=1}">
               						<li class="space"></li>
               					</c:if>
               					<li><a href="newsServlet?op=look&nid=${item.nid }" title="${item.title}" target="_blank">${item.title20 }_${item.tname }
               						<c:if test="${not empty item.pic}">
               							<font color="#FCC006"> [有图]</font>
               						</c:if>
               						</a><span>${item.createDate }</span></li>
               				</c:forEach>
               			</c:if>
               			<li class="space"></li>
                    </ul>
                    <ul>
                    	 <li class="space"><span>
                        	<a href="javascript:frontOverPage('1')">首页</a> &nbsp;&nbsp;
                        	<a href="javascript:frontOverPage('2')">上一页</a> &nbsp;&nbsp;
                        	<a href="javascript:frontOverPage('3')">下一页</a> &nbsp;&nbsp;
                        	<a href="javascript:frontOverPage('4')">末页</a> &nbsp;
                        	<span id="front_page">当前页数[<span id="now_pageno">${frontPageUtil.pageNo }</span>/<span id="now_totalPages">${frontPageUtil.totalPages }</span>] 总共${frontPageUtil.totalSize }条</span></span>
                        </li>
                    </ul>
               </div>
            </div>
            <div class="picnews">
            	<ul>
            		<c:forEach items="${picNews}" var="item">
            			<li><img src="../${item.pic }" width="248" height="160" title="${item.title }"/>
            			<a href="newsServlet?op=look&nid=${item.nid }" title="${item.title}" target="_blank">${item.title10 }</a></li>
            		</c:forEach>
                </ul>
            </div>
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
