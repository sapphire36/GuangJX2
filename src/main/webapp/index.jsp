<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<html lang="en" class="no-js">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<title>login</title>
<link rel="stylesheet" type="text/css" href="/GuangJX/static/css/login/normalize.css" />
<link rel="stylesheet" type="text/css" href="/GuangJX/static/css/login/demo.css" />
<!--必要样式-->
<link rel="stylesheet" type="text/css" href="/GuangJX/static/css/login/component.css" />
<!--[if IE]>
<script src="js/html5.js"></script>
<![endif]-->
<script src="https://code.jquery.com/jquery-latest.js"></script>
<link href="/GuangJX/static/css/toastr.css" rel="stylesheet" />
<script src="/GuangJX/static/js/toastr.js"></script>
<script type="text/javascript">
//script内容需要放在rapid override标签之间
$(document).ready(function(){		
	$("#dologin").click(function(){
		//绑定事件  #xx代表以xx为id的控件
		//参考文档:http://www.w3school.com.cn/jquery/jquery_ref_selectors.asp
	    dologin();
	  })
});
function dologin(){
	//编辑函数
	var username=$("#username").val();//获取id为lightboxname的值
	var passwd=$("#passwd").val();//获取id为lockid的值
	var type=$('input[name="usertype"]:checked').val();
	 
	//根据选择器获取数据
	//参考文档:http://www.w3school.com.cn/jquery/attributes_attr.asp
	$.ajax({
		type : "POST",
		url : "<%=basePath%>/login/dologin",
		data:{"USERNAME":username,
			  "PASSWD":passwd,
			  "USERTYPE":type
			  },
		success : function(data) {
			if(data.data=="1"){
				window.location.href='<%=basePath%>/manage/home/getview/index';
			}
			if(data.data=="2"){
				toastr.error("该用户被禁用,请联系管理员开通!");
			}
			if(data.data=="0"){
				toastr.error("用户名或密码错误!");
			}
		}
	});
}
</script>
</head>
<body background="/GuangJX/static/images/login/demo-1-bg.jpg">
		<div class="container demo-1">
			<div class="content">
				<div id="large-header" class="large-header">
					<canvas id="demo-canvas"></canvas>
					<div class="logo_box">
						<h3>光交箱管控系统</h3>						
							<div class="input_outer">
								<span class="u_user"></span>
								<input id="username" name="logname" class="text" style="color: #FFFFFF !important" type="text" placeholder="请输入账户">
							</div>
							<div class="input_outer">
								<span class="us_uer"></span>
								<input id="passwd" name="logpass" class="text" style="color: #FFFFFF !important; position:absolute; z-index:100;"value="" type="password" placeholder="请输入密码">
							</div>
							<div>
							<input type="radio" name="usertype" value="1" checked>操作人员
							<input type="radio" name="usertype" value="2">业主维护
							<input type="radio" name="usertype" value="3">系统管理员
							</div>
							<div class="mb2"><a  id="dologin" class="act-but submit" href="#" style="color: #FFFFFF">登录</a></div>
					</div>
				</div>
			</div>
		</div><!-- /container -->
		<script src="/GuangJX/static/js/login/TweenLite.min.js"></script>
		<script src="/GuangJX/static/js/login/EasePack.min.js"></script>
		<script src="/GuangJX/static/js/login/rAF.js"></script>
		<script src="/GuangJX/static/js/login/demo-1.js"></script>
	</body>
</html>