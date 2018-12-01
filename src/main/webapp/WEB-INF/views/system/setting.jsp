<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path1 = request.getContextPath();
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1;
%>
<rapid:override name="title">
	<title>系统设置</title>
	restart
<script type="text/javascript">
//script内容需要放在rapid override标签之间
var type="true";
$(document).ready(function(){
	//页面加载时自动执行该函数
    $("#stop").click(function(){
		//绑定事件  #xx代表以xx为id的控件
		//参考文档:http://www.w3school.com.cn/jquery/jquery_ref_selectors.asp
	    if(confirm("确定关闭监听吗?")){  
			$.ajax({
				type : "POST",
				url : "<%=basePath1%>/manage/system/stop",
				data:{"ISALL":"tt" //测试,没有意义
				},
				success : function(data) {
					if(data.data=="true"){
						showmessage("关闭成功!",false);
						//location.reload();//刷新界面
					}else{
						showmessage("关闭失败!",false);
					}
				}
			});
	       return true;  
	    }  
	}); 
	
	$("#restart").click(function(){
		//绑定事件  #xx代表以xx为id的控件
		//参考文档:http://www.w3school.com.cn/jquery/jquery_ref_selectors.asp
	    if(confirm("确定重启吗")){  
			$.ajax({
				type : "POST",
				url : "<%=basePath1%>/manage/system/restart",
				data:{"ISALL":type //测试,没有意义
				},
				success : function(data) {
					if(data.data=="true"){
						showmessage("重启成功!",false);
						//location.reload();//刷新界面
					}else{
						showmessage("重启失败!",false);
					}
				}
			});
	       return true;  
	    }  
	}); 
	$("#NOTALL").click(function(){
		//绑定事件  #xx代表以xx为id的控件
		//参考文档:http://www.w3school.com.cn/jquery/jquery_ref_selectors.asp
		type="false"
	    showmessage("监听系统登记的设备!",false);
	}); 
	$("#ALL").click(function(){
		//绑定事件  #xx代表以xx为id的控件
		//参考文档:http://www.w3school.com.cn/jquery/jquery_ref_selectors.asp
		type="true"
		showmessage("监听用户下所有设备!",false);
	}); 
	
}); 
</script>
</rapid:override>
<rapid:override name="content">
	<div class="mws-panel grid_8">
		<div class="mws-panel-header">
			<span class="mws-i-24 i-table-1">系统设置 <small>系统参数设置</small></span>
		</div>
	</div>
</rapid:override>
<%@ include file="../home/base.jsp"%>
