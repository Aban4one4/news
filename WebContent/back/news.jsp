<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="myNewsInfo">
	<div id="newsInfos">
		<c:if test="${not empty newsInfo}">
			<ul class="classlist">
				<c:forEach items="${newsInfo}" var="item" begin="0" end="4">
					<li>${item.title }_${item.tname }
						<c:if test="${not empty item.pic}">
							<font color="#FCC006">[有图]</font>
						</c:if>
					  <span>日期：${item.createDate }&nbsp;&nbsp; 作者：${item.author }&nbsp;&nbsp;&nbsp;&nbsp;
					  <a href="javascript:updateNews('${item.nid }')">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:delNewsInfo('${item.nid }')">删除</a></span></li>
				</c:forEach>
				<li class="space"></li>
			</ul>
			
			<ul class="classlist">
				<c:forEach items="${newsInfo}" var="item" begin="5" end="9">
					<li>${item.title }_${item.tname }
						<c:if test="${not empty item.pic}">
							<font color="#FCC006">[有图]</font>
						</c:if>
					  <span>日期：${item.createDate }&nbsp;&nbsp; 作者：${item.author }&nbsp;&nbsp;&nbsp;&nbsp;
					  <a href="javascript:updateNews('${item.nid }')">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:delNewsInfo('${item.nid }')">删除</a></span></li>
				</c:forEach>
				<li class="space"></li>
			</ul>
			
			<ul class="classlist">
				<c:forEach items="${newsInfo}" var="item" begin="10" end="14">
					<li>${item.title }_${item.tname }
						<c:if test="${not empty item.pic}">
							<font color="#FCC006">[有图]</font>
						</c:if>
					  <span>日期：${item.createDate }&nbsp;&nbsp; 作者：${item.author }&nbsp;&nbsp;&nbsp;&nbsp;
					  <a href="javascript:updateNews('${item.nid }')">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:delNewsInfo('${item.nid }')">删除</a></span></li>
				</c:forEach>
				<li class="space"></li>
			</ul>
		</c:if>
	</div>
	<div class="show_page_info">
		<ul>
			<li><a href="javascript:overPage('1')">首页</a></li>
			<li><a href="javascript:overPage('2')">上一页</a></li>
			<li><a href="javascript:overPage('3')">下一页</a></li>
			<li><a href="javascript:overPage('4')">尾页</a></li>
			<li><span id="show_page_info">当前第<span id="now_no">${backPageUtil.pageNo }</span>/<span id="now_total">${backPageUtil.totalPages }</span>页 <span>总共${backPageUtil.totalSize }条</span></span></li>
		</ul>
	</div>
</div>