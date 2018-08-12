<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path1 = request.getContextPath();
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
%>
<rapid:override name="title">
	<title>区域管理</title>
	<script src="/GuangJX/static/plugins/jquery.dataTables.js"></script>
	<script type="text/javascript">
function refresh(obj){
	location.reload();//刷新界面
}
function addarea(obj){
	$("#adddialog").dialog("option", {
		modal : false
	}).dialog("open");
}
$(document).ready(function() {
	//页面加载时自动执行该函数
	$(".mws-datatable-fn").dataTable({
		sPaginationType : "full_numbers"
	});
	
	$("#adddialog").dialog({
		autoOpen : false,
		title : "添加区域",
		modal : true,
		width : "640",
		buttons : [ {
			text : "确定",
			click : function() {
				doaddarea();
				$(this).dialog("close");
			}
		}, {
			text : "返回",
			click : function() {
				$(this).dialog("close");
			}
		} ]
	});
});

function doaddarea() {
	//添加函数
	var areaname = $("#areaname").val(); 
	var loginname = $("#loginname").val(); 
	var passwd = $("#passwd").val(); 
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/area/addarea",
		data : {
			"areaname" : areaname,
			"loginname" : loginname,
			"passwd" : passwd
		},
		success : function(data) {
			if (data.data == "true") {
				toastr.success("设备成功!");
				location.reload();//刷新界面
			} else {
				toastr.success(data.message);
			}
		}
	});
}

function cancelsubscribe(obj){
	var id = $(obj).next().val();
	if (confirm("确定取消订阅该区域内所有设备吗")) {
		$.ajax({
			type : "POST",
			url : "<%=basePath1%>/manage/area/cancelsubscribe",
			data : {
				"ID" : id
			},
			success : function(data) {
				if (data.data == "true") {
					toastr.success("取消订阅成功!");
					location.reload();//刷新界面
				} else {
					toastr.error(data.message);
				}
			}
		});
		return true;
	}
}

function subscribe(obj){
	var id = $(obj).next().val();
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/area/subscribe",
		data : {
			"ID" : id
		},
		success : function(data) {
			if (data.data == "true") {
				toastr.success("订阅成功!");
				location.reload();//刷新界面
			} else {
				toastr.error(data.message);
			}
		}
	});
}

function deletearea(obj){
	var id = $(obj).next().next().val();
	toastr.success(id);
	if (confirm("确定删除吗")) {
		$.ajax({
			type : "POST",
			url : "<%=basePath1%>/manage/area/deletearea",
			data : {
				"ID" : id
			},
			success : function(data) {
				if (data.data == "true") {
					toastr.success("删除成功!");
					location.reload();//刷新界面
				} else {
					toastr.error(data.message);
				}
			}
		});
		return true;
	}
}
</script>
</rapid:override>
<rapid:override name="content">
	<div class="mws-panel grid_8">
		<input type="button" id="addlightbox" class="mws-button blue"
			value="添加" onclick="addarea(this)" /> <input type="button"
			id="refresh" class="mws-button blue" value="更新"
			onclick="refresh(this)" />

		<div class="mws-panel-header">
			<span class="mws-i-24 i-table-1">区域管理表</span>
		</div>
		<div class="mws-panel-body">
			<table class="mws-datatable-fn mws-table">
				<thead>
					<tr>
						<th align="center">区域编号</th>
						<th align="center">区域名称</th>
						<th align="center">有人网登录用户名</th>
						<th align="center">有人网登录密码</th>
						<th align="center">创建日期</th>
						<th align="center">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="area" items="${arealist}">
						<tr class="gradeX">
							<td align="center">${area.ID}</td>
							<td align="center">${area.AREANAME}</td>
							<td align="center">${area.LOGINNAME}</td>
							<td align="center">${area.PASSWD}</td>
							<td align="center">${area.ADDTIME}</td>
							<td align="center">
							   <input type="button" value="删除" class="mws-button red small"
								onclick="deletearea(this)" />
							    <c:if test="${area.ISSUBSCRIBE==1}">   
                                  <input type="button" value="取消订阅"
								   class="mws-button red small" onclick="cancelsubscribe(this)" /> 
								</c:if>
								<c:if test="${area.ISSUBSCRIBE==0}">   
							      <input type="button" value="订阅"
								  class="mws-button blue small" onclick="subscribe(this)" /> 
								</c:if>
								<input type="hidden"  value="${area.ID}"> 
								</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	
	<div id="adddialog">
		<div class="mws-panel">
			<form class="mws-form" action="#">
				<div class="mws-form-inline">
					<div class="mws-form-row">
						<label>区域名：</label>
						<div class="mws-form-item large">
							<input id="areaname" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>有人网登录名：</label>
						<div class="mws-form-item large">
							<input id="loginname" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>有人网登录密码：</label>
						<div class="mws-form-item large">
							<input id="passwd" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</rapid:override>
<%@ include file="../home/base.jsp"%>