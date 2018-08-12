<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@page import="org.kzcw.service.LightboxService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path1 = request.getContextPath();
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1;
%>
<rapid:override name="title">
	<title>业务控制</title>
	<script type="text/javascript">
// 测试引用是否成功
window.onbeforeunload=function(){
	return "是否要离开";
}
function doRefresh(){
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/control/dorefresh",
		data :"test",
		success : function(data) {
			if(data.data=="true"){
			}else{
				return "是否要离开";
			}
		}
	});
}

$(document).ready(function(e) {
	doRefresh();//刷新页面
	gettodolist();
	getoperatelist();
	
	$("#opendialog").dialog({
		autoOpen : false,
		title : "请求开锁",
		modal : true,
		width : "640",
		buttons : [ {
			text : "确定开锁",
			click : function() {
				doopen();
				$(this).dialog("close");
			}
		}, {
			text : "拒绝开锁",
			click : function() {
				doorejectperate();
				$(this).dialog("close");
			}
		} ]
	});
  //toastr.success("ok");
});
//var waittime=1000; //等待时间
function gettodolist(){
	//获取开箱队列
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/control/gettodolist",
		data :"test",
		success : function(data) {
			if(data.data=="true"){
				$("#todolist").html(data.content); 
			}
		}
	});
	setTimeout(gettodolist,500);
}

function showdialog(obj){
	var text=$(obj).text();
	var data = text.split(':');
	$("#lightboxname").attr("value",data[0]);
	$("#lockid").attr("value",data[2]);
	$("#opendialog").dialog("option", {
		modal : false
	}).dialog("open");
}

function getoperatelist(){
	//获取开箱队列
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/control/getoperatelist",
		data :"test",
		success : function(data) {
			if(data.data=="true"){
				$("#operatelist").html(data.content);
			}
		}
	});
	setTimeout(getoperatelist,500);
}

function doorejectperate(){
	var imei = $("#lockid").val();
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/control/doorejectperate",
		data:{"IMEI":imei}, 
		success : function(data) {
			if(data.data=="true"){
				toastr.success("已取消任务!");
			}else{
				toastr.error("取消失败!");
			}
		}
     });
}

function doopen(){
	var imei = $("#lockid").val();
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/control/dooperatelock",
		data:{"IMEI":imei}, 
		success : function(data) {
			if(data.data=="true"){
				toastr.success("已提交到待执行队列!");
			}else{
				toastr.error("提交失败!");
			}
		}
     });
}
</script>
</rapid:override>
<rapid:override name="content">
    <input id="operateid" type="hidden" name="field_name" value="value" />
	<div class="mws-panel grid_12">
		<div class="mws-panel-header">
			<span class="mws-i-24 i-books-2">待执行队列</span>
		</div>
		<div class="mws-panel-body">
			<ul class="mws-summary" id="todolist">
			</ul>
		</div>
	</div>
	<div class="mws-panel grid_12">
		<div class="mws-panel-header">
			<span class="mws-i-24 i-books-2">请求操作队列</span>
		</div>
		<input id="operateIMEI" type="hidden"  value=""> 
		<div class="mws-panel-body">
			<ul class="mws-summary" id="operatelist">
			</ul>
		</div>
	</div>
 
 	<div id="opendialog">
		<div class="mws-panel">
			<form class="mws-form" action="#">
				<div class="mws-form-inline">
					<div class="mws-form-row">
						<label>请求单位/个人：</label>
						<div class="mws-form-item large">
							<input id="lightboxname" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>NB-IoT锁IMEI编号：</label>
						<div class="mws-form-item large">
							<input id="lockid" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</rapid:override>
<%@ include file="../home/base.jsp"%>
