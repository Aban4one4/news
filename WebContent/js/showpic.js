function previewImage(file, id, id2) {
	document.getElementById(id).style.display = "block";
	var MAXWIDTH = 160;
	var MAXHEIGHT = 180;
	var div = document.getElementById(id2);
	/*
	 * alert(file.files); alert(file.files[0]);
	 */
	// IE下file.files为undefined，谷歌和火狐,IE11是Object FileList,Object File,
	if (file.files && file.files[0]) {
		div.innerHTML = '<img id=' + id2 + '>';
		var img = document.getElementById(id2);
		img.onload = function() {
			var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth,
					img.offsetHeight);
			img.width = rect.width;// 给图片赋宽度
			img.height = rect.height;// 给图片赋高度
			img.style.marginLeft = rect.left + 'px';// 设图片距离左边的距离
			img.style.marginTop = rect.top + 'px';// 设置图片距离顶部的距离
		}
		// 要想在页面上显示本地图片，以前我们通常的做法是将选择的图片文件上传至后端服务器，后端对其进行存储，再将图片的URL返回到前端，前端通过这个URL来显示图片
		// 现在HTML5的FileReader接口支持本地预览，FileReader接口主要是将文件读入内存，并提供相应的方法，来读取文件中的数据，当然就能显示本地图片不需上传了
		var reader = new FileReader();
		reader.onload = function(evt) {
			// firefox支持的属性：evt.target,而result表示是结果
			img.src = evt.target.result;
		}
		// 返回选定程序的路径
		reader.readAsDataURL(file.files[0]);
		// FileReader物件的readAsDataURL方法可以将读取到的档案读成Data URL
	} else {
		// 运用IE滤镜获取数据;
		var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
		file.select();// /选定程序
		var src = document.selection.createRange().text;
		div.innerHTML = '<img id=imghead>';
		var img = document.getElementById('imghead');
		img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
		var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth,
				img.offsetHeight);
		//状态值
		status = ('rect:' + rect.top + ',' + rect.left + ',' + rect.width + ',' + rect.height);
		div.innerHTML = "<div id=divhead style='width:" + rect.width
				+ "px;height:" + rect.height + "px;margin-top:" + rect.top
				+ "px;margin-left:" + rect.left + "px;" + sFilter + src
				+ "\"'></div>";
	}
}
function clacImgZoomParam(maxWidth, maxHeight, width, height) {
	var param = {
		top : 0,
		left : 0,
		width : width,
		height : height
	};
	if (width > maxWidth || height > maxHeight) {
		rateWidth = width / maxWidth;
		rateHeight = height / maxHeight;

		if (rateWidth > rateHeight) {
			param.width = maxWidth;
			param.height = Math.round(height / rateWidth);
		} else {
			param.width = Math.round(width / rateHeight);
			param.height = maxHeight;
		}
	}
	param.left = Math.round((maxWidth - param.width) / 2);
	param.top = Math.round((maxHeight - param.height) / 2);
	return param;
}