<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="add_news">
	<div style="width:100%;height:280px">
	<form action="../newsServlet?op=updateNews&nid=${updateNews.nid }" method="post" enctype="multipart/form-data" onsubmit="return checkAddNews()">
	<ul>
		<li>
			<label>新闻类型：</label>
			<select name="ntid" id="ntid" class="myinput1">
				<c:if test="${not empty newsTopics}">
					<c:forEach items="${newsTopics}" var="item">
						<c:choose>
							<c:when test="${item.tid==updateNews.tid}">
								<option value="${item.tid }" selected="selected">${item.tname }</option>
							</c:when>
							<c:otherwise>
								<option value="${item.tid }">${item.tname }</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:if>
			</select>
			<input type="submit" value=" 修改 " class="mybtn1" />
		</li>
		<li>
			<label>新闻标题：</label>
			<input type="text" name="ntitle" id="ntitle" class="myinput1" value="${updateNews.title }"/>
		</li>
		<li>
			<label>作&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;者：</label>
			<input type="text" name="nauthor" id="nauthor" class="myinput1" value="${updateNews.author }"/>
		</li>
		<li>
			<label>发布日期：</label>
			<input type="text" name="ncreatedate" id="ncreatedate" class="myinput1" value="${updateNews.createDate }" onclick="fPopCalendar(event,this,this)" onfocus="this.select()" readonly="readonly" />
		</li>
		<li>
			<label>新闻图片：</label>
			<input type="file" name="npicpath" id="npicpath" class="myinput1" onchange="previewImage(this,'preview1','show_img')"/>
		</li>
		<li>
			<label>新闻内容：</label>
			<textarea rows="5" cols="200" name="ncontent" id="ncontent" style="display:none"></textarea>
		</li>
	</ul>
	</form>
	</div>
	<div style="margin-left:40px">
		<script id="container" name="content" type="text/plain">${updateNews.content }</script>
	</div>
	<div class="show_pic" id="preview1">
		<img src="../${updateNews.pic }" width="160px" height="180px" id="show_img"/>
	</div>
</div>

<script type="text/javascript">
	var ue = UE.getEditor('container');
	
	function checkAddNews(){
		var title=$.trim( $("#ntitle").val() );
		$("#ncontent").val(ue.getContent()).css("display","block");
		$("#container").remove();
		if(title==""){
			alert("请输入新闻标题...");
			return false;
		}else{
			return true;
		}
	}
</script>