//JavaScript Document
$(function(){
	$('#content_info').tabs('add',{   
		title:'查看新闻',   
		href:'news.jsp',
		closable:true  
	});  

	$('#menu_tree').tree({
		onClick: function(node){
		if(node.id=="look_news"){
			var obj=$('#content_info');
			if(obj.tabs('exists','查看新闻')){ //如果面板标题'查看新闻'存在，则选择
				obj.tabs('select','查看新闻');
			}else{
				$('#content_info').tabs('add',{   
					title:'查看新闻',   
					href:'news.jsp',
					closable:true  
				});  
			}
		}else if(node.id=="add_news"){
			var obj=$('#content_info');
			if(obj.tabs('exists','添加新闻')){ //如果面板标题'查看新闻'存在，则选择
				obj.tabs('select','添加新闻');
			}else{
				$('#content_info').tabs('add',{   
					title:'添加新闻',   
					href:'addNews.html', 
					closable:true,   
				});  
			}
		}else if(node.id=="manager_type"){
			var obj=$('#content_info');
			if(obj.tabs('exists','新闻类型管理')){ //如果面板标题'查看新闻'存在，则选择
				obj.tabs('select','新闻类型管理');
			}else{
				$('#content_info').tabs('add',{   
					title:'新闻类型管理',   
					href:'showInfo1.html',   
					closable:true,   
				}); 
			}
		}
	}
	});
});

//管理员退出
function adminExit(){
	$.post("../loginServlet?time="+new Date().getTime(),{op:"exitLogin"},function(data){
		localStorage.clear();
		sessionStorage.clear();
		
		$.messager.show({
			title:'注销提示',
			msg:'系统即将退出...',
			timeout:2000,
			showType:'slide'
		});
		location.href="../admin/login.jsp";
	});
}

//删除新闻
function delNewsInfo(nid){
	$.messager.confirm("确认提示","数据一旦删除，将不能恢复，您确定要删除选定数据吗?",function(rt){
		if(rt){
			$.post("../newsServlet?time="+new Date().getTime(),{op:"delNews",nid:$.trim(nid)},function(data){
				if(data!=null){
					var str="<ul class=\"classlist\">";
					$.each(data.newsInfo,function(index,item){
						if(index%5==0&&index!=0){
							str+="<li class=\"space\"></li></ul><ul class=\"classlist\">";
						}
						str+="<li>"+item.title+"_"+item.tname;
						if(item.pic!=""&&item.pic!=null){
							str+="<font color=\"#FCC006\">[有图]</font>";
						}
						str+="<span>日期："+item.createDate+"&nbsp;&nbsp; 作者："+item.author+"&nbsp;&nbsp;&nbsp;&nbsp;";
						str+="<a href=\"javascript:updateNews('"+item.nid+"')\">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:delNewsInfo('"+item.nid+"')\">删除</a></span></li>";
					});
					str+="<li class=\"space\"></li></ul>";
					$("#newsInfos").html(str);
					
					$("#show_page_info").html("当前第<span id=\"now_no\">"+data.pageInfo.pageNo+"</span>/<span id=\"now_total\">"+data.pageInfo.totalPages+"</span>页 <span>总共"+data.pageInfo.totalSize+"条</span>");

					$.messager.show({
						title:'成功提示',
						msg:'新闻信息删除成功...',
						timeout:2000,
						showType:'slide'
					});
				}else{
					$.messager.show({
						title:'失败提示',
						msg:'新闻信息删除失败...',
						timeout:2000,
						showType:'slide'
					});
				}
			},"json");
		}else{
			return;
		}
	});
}

//翻页
function overPage(num){
	if($.trim(num)==1||$.trim(num)==2){
		var now=$.trim($("#now_no").text());
		if(now==1){
			return;
		}
	}
	if($.trim(num)==4||$.trim(num)==3){
		var now=$.trim($("#now_no").text());
		var end=$.trim($("#now_total").text());
		if(now==end){
			return;
		}
	}
	$.post("../newsServlet?time="+new Date().getTime(),{op:"overPage",flag:num},function(data){
		var str="<ul class=\"classlist\">";
		$.each(data.newsInfo,function(index,item){
			if(index%5==0&&index!=0){
				str+="<li class=\"space\"></li></ul><ul class=\"classlist\">";
			}
			str+="<li>"+item.title+"_"+item.tname;
			if(item.pic!=""&&item.pic!=null){
				str+="<font color=\"#FCC006\">[有图]</font>";
			}
			str+="<span>日期："+item.createDate+"&nbsp;&nbsp; 作者："+item.author+"&nbsp;&nbsp;&nbsp;&nbsp;";
			str+="<a href=\"javascript:updateNews('"+item.nid+"')\">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:delNewsInfo('"+item.nid+"')\">删除</a></span></li>";
		});
		str+="<li class=\"space\"></li></ul>";
		$("#newsInfos").html(str);
		$("#show_page_info").html("当前第<span id=\"now_no\">"+data.pageInfo.pageNo+"</span>/<span id=\"now_total\">"+data.pageInfo.totalPages+"</span>页 <span>总共"+data.pageInfo.totalSize+"条</span>");
	},"json");
}

//修改新闻
function updateNews(nid){
	$.post("../newsServlet?time="+new Date().getTime(),{op:"showUpdateNews",nid:nid},function(data){
		if(parseInt($.trim(data))==1){
			var obj=$('#content_info');
			if(obj.tabs('exists','修改新闻')){
				obj.tabs('select','修改新闻');
			}else{
				$('#content_info').tabs('add',{   
					title:'修改新闻',   
					href:'updateNews.jsp', 
					closable:true,   
				});  
			}
		}else{
			$.messager.show({
				title:'失败提示',
				msg:'获取数据失败，请稍后重试...',
				timeout:2000,
				showType:'slide'
			});
		}
	});
}
