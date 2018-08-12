<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path1 = request.getContextPath();
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
%>
<rapid:override name="title">
	<title>用户管理</title>
	<script src="/GuangJX/static/plugins/jquery.dataTables.js"></script>
	<script type="text/javascript">
function refresh(obj){
	location.reload();//刷新界面
}

function adduser(obj){
	$("#adddialog").dialog("option", {
		modal : false
	}).dialog("open");
}

function deleteuser(obj){
	var id = $(obj).prev().prev().pre().val();
	if (confirm("确定删除吗")) {
		$.ajax({
			type : "POST",
			url : "<%=basePath1%>/manage/user/dodelete",
			data : {
				"ID" : id
			},
			success : function(data) {
				if (data.data == "true") {
					toastr.success("删除用户成功!");
					location.reload();//刷新界面
				} else {
					toastr.error("删除用户失败!");
				}
			}
		});
		return true;
	}
}

function edituser(obj){
	var username=$(obj).parent().prev().prev().prev().prev().prev().text();
	var id = $(obj).prev().val();
	$("#edituserid").val(id);
	$("#editusername").val(username);
	$("#editdialog").dialog("option", {
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
		title : "添加用户",
		modal : true,
		width : "640",
		buttons : [ {
			text : "确定",
			click : function() {
				doadduser();
				$(this).dialog("close");
			}
		}, {
			text : "返回",
			click : function() {
				$(this).dialog("close");
			}
		} ]
	});
	
	$("#editdialog").dialog({
		autoOpen : false,
		title : "编辑用户",
		modal : true,
		width : "640",
		buttons : [ {
			text : "确定",
			click : function() {
				doedituser();
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

function doadduser() {
	//添加函数
	var username = $("#username").val(); 
	var passwd = $("#passwd").val(); 
	var area = $("#area").val(); 
	var usertype = $("#usertype").val();
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/user/doadd",
		data : {
			"username" : username,
			"passwd" : passwd,
			"area" : area,
			"usertype" : usertype
		},
		success : function(data) {
			if (data.data == "true") {
				toastr.success("用户添加成功!");
				location.reload();//刷新界面
			} else {
				toastr.success(data.message);
			}
		}
	});
}

function doedituser() {
	//添加函数
	var username = $("#editusername").val(); 
	var area = $("#editarea").val(); 
	var usertype = $("#editusertype").val();
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/user/doupdate",
		data : {
			"ID":$("#edituserid").val(),
			"username" : username,
			"area" : area,
			"usertype" : usertype
		},
		success : function(data) {
			if (data.data == "true") {
				toastr.success("用户更新成功!");
				location.reload();//刷新界面
			} else {
				toastr.success("用户更新失败!");
			}
		}
	});
}

function disableuse(obj){
	var id = $(obj).prev().prev().val();
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/user/disableuse",
		data : {
			"ID" : id
		},
		success : function(data) {
			if (data.data == "true") {
				toastr.success("用户已禁用!");
				location.reload();//刷新界面
			} else {
				toastr.error("用户禁用失败!");
			}
		}
	});
}

function openuse(obj){
	var id = $(obj).prev().prev().val();
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/user/openuse",
		data : {
			"ID" : id
		},
		success : function(data) {
			if (data.data == "true") {
				toastr.success("用户已启用!");
				location.reload();//刷新界面
			} else {
				toastr.error(data.message);
			}
		}
	});
}
</script>
</rapid:override>
<rapid:override name="content">
	<div class="mws-panel grid_8">
		<input type="button" id="addlightbox" class="mws-button blue"
			value="添加" onclick="adduser(this)" /> <input type="button"
			id="refresh" class="mws-button blue" value="更新"
			onclick="refresh(this)" />

		<div class="mws-panel-header">
			<span class="mws-i-24 i-table-1">用户信息表</span>
		</div>
		<div class="mws-panel-body">
			<table class="mws-datatable-fn mws-table">
				<thead>
					<tr>
						<th align="center">用户编号</th>
						<th align="center">用户名</th>
						<th align="center">用户类型</th>
						<th align="center">所属区域</th>
						<th align="center">状态</th>
						<th align="center">创建时间</th>
						<th align="center">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${userlist}">
						<tr class="gradeX">
							<td align="center">${user.ID}</td>
							<td align="center">${user.USERNAME}</td>
							<td align="center">${user.ROLEID}</td>
							<td align="center">${user.AREANAME}</td>
						    <c:if test="${user.STATUS==1}">   
                               <td align="center">已启用</td>
							</c:if>
							<c:if test="${user.STATUS==0}">   
						       <td align="center">已禁用</td>
							</c:if>
							<td align="center">${user.ADDTIME}</td>
							<td align="center">
							<input type="hidden"  value="${user.ID}">
							<input type="button" value="编辑"
								class="mws-button blue small" onclick="edituser(this)" /> 
							<c:if test="${user.STATUS==1}">   
                                 <input type="button" value="禁用"
							   class="mws-button gray small" onclick="disableuse(this)" /> 
							</c:if>
							<c:if test="${user.STATUS==0}">   
						      <input type="button" value="启用"
							  class="mws-button green small" onclick="openuse(this)" /> 
							</c:if>
							<input
								type="button" value="删除" class="mws-button red small"
								onclick="deleteuser(this)" /></td>
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
						<label>用户名：</label>
						<div class="mws-form-item large">
							<input id="username" type="text" class="mws-textinput"
								title="input your name" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>登录密码：</label>
						<div class="mws-form-item large">
							<input id="passwd" type="text" class="mws-textinput" title="passwd" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>重复输入登录密码：</label>
						<div class="mws-form-item large">
							<input id="repasswd" type="text" class="mws-textinput" title="passwd" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>所在区域：</label>
						<div class="mws-form-item small">
							<select id="area">
							   <c:forEach var="area" items="${arealist}">
								<option>${area.AREANAME}</option>
							   </c:forEach>
							</select>
						</div>
					</div>
					
					<div class="mws-form-row">
						<label>用户类型：</label>
						<div class="mws-form-item small">
							<select id="usertype">
							   <c:forEach var="role" items="${rolelist}">
								<option>${role.NAME}</option>
							   </c:forEach>
							</select>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	
	<div id="editdialog">
		<div class="mws-panel">
			<form class="mws-form" action="#">
				<div class="mws-form-inline">
					<div class="mws-form-row">
						<label>用户名：</label>
						<div class="mws-form-item large">
						    <input id="edituserid" type="hidden" name="field_name" value="value" />
							<input id="editusername" type="text" class="mws-textinput"
								title="input your name" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>所在区域：</label>
						<div class="mws-form-item small">
							<select id="editarea">
							   <c:forEach var="area" items="${arealist}">
								<option>${area.AREANAME}</option>
							   </c:forEach>
							</select>
						</div>
					</div>
					
					<div class="mws-form-row">
						<label>用户类型：</label>
						<div class="mws-form-item small">
							<select id="editusertype">
							   <c:forEach var="role" items="${rolelist}">
								<option>${role.NAME}</option>
							   </c:forEach>
							</select>
						</div>
					</div>
					
				</div>
			</form>
		</div>
	</div>
</rapid:override>
<%@ include file="../home/base.jsp"%>