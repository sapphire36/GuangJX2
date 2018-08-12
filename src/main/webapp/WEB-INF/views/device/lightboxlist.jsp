<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path1 = request.getContextPath();
	String basePath1 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path1;
%>
<rapid:override name="title">
	<title>光交箱信息管理</title>
	<script src="/GuangJX/static/plugins/jquery.dataTables.js"></script>
	<script type="text/javascript">
    function addlightbox(obj){
		$("#adddialog").dialog("option", {
			modal : false
		}).dialog("open");
    }
    
    function refresh(obj){
    	location.reload();//刷新界面
    }
	$(document).ready(function() {
		//页面加载时自动执行该函数
		$("#lightboxtable").dataTable({
			sPaginationType : "full_numbers"
		});
		
		$("#adddialog").dialog({
			autoOpen : false,
			title : "添加光交箱",
			modal : true,
			width : "640",
			buttons : [ {
				text : "确定",
				click : function() {
					doaddlightbox();
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
	
	function desclightbox(obj) {
		var id = $(obj).prev().val();
		$.ajax({
			type : "POST",
			url : "<%=basePath1%>/manage/device/getlightbox",
			data : {
				"ID" : id
			},
			success : function(data) {
				if (data.data == "true") {
					$("#editlightboxname").attr("value", data.NAME);
					$("#editlockid").attr("value", data.LOCKID);
					$("#editspec").attr("value", data.SPEC);
					$("#editmadetype").attr("value", data.MADETYPE);
					$("#editlocation").attr("value", data.LOCATION);
					$("#editpeople").attr("value", data.PEOPLE);
					$("#editid").attr("value", id);
				} else {
					toastr.error("数据库连接错误!");
				}

			}
		});
	}
	
	function deletelightbox(obj) {
		var id = $(obj).prev().val();
		if (confirm("确定删除吗")) {
			$.ajax({
				type : "POST",
				url : "<%=basePath1%>/manage/device/deletelightbox",
				data : {
					"ID" : id
				},
				success : function(data) {
					if (data.data == "true") {
						toastr.success("删除设备成功!");
						location.reload();//刷新界面
					} else {
						toastr.error("删除设备失败!");
					}
				}
			});
			return true;
		}
	}

	function doaddlightbox() {
		//添加函数
		var name = $("#lightboxname").val();//获取id为lightboxname的值
		var lid = $("#lockid").val();//获取id为lockid的值
		var area = $("#area").val();//获取id为spec的值
		$.ajax({
			type : "POST",
			url : "<%=basePath1%>/manage/device/addlightbox",
			data : {
				"NAME" : name,
				"LOCKID" : lid,
				"AREA" : area
			},
			success : function(data) {
				if (data.data == "true") {
					toastr.success("设备添加成功!");
					//showmessage("设备添加成功!",false);
					location.reload();//刷新界面
				} else {
					toastr.success("设备添加失败!");
					//showmessage("设备添加失败!",false);
				}
			}
		});
	}
	</script>
</rapid:override>
<rapid:override name="content">
	<div class="mws-panel grid_8">
		<input type="button" id="addlightbox" class="mws-button blue"
			value="添加" onclick="addlightbox(this)" /> <input type="button"
			id="refresh" class="mws-button blue" value="更新"
			onclick="refresh(this)" />

		<div class="mws-panel-header">
			<span class="mws-i-24 i-table-1">光交箱信息表</span>
		</div>
		<div class="mws-panel-body">
			<table id="lightboxtable" class="mws-datatable-fn mws-table">
				<thead>
					<tr>
						<th align="center">箱体名称</th>
						<th align="center">IMEI编号</th>
						<th align="center">施工状态</th>
						<th align="center">锁状态</th>
						<th align="center">在线状态</th>
						<th align="center">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="light" items="${lightlist}">
						<tr class="gradeX">
							<td align="center"><a style="text-decoration:none;" href="/GuangJX/manage/device/getview/detil?id=${light.ID}">${light.NAME}</a></td>
							<td align="center">${light.IEME}</td>
							<td align="center">${light.DOORSTATUS}</td>
							<td align="center">${light.LOCKSTATUS}</td>
							<td align="center">${light.ISONLINE}</td>
							<td align="center">
							<input type="hidden"  value="${light.ID}"> 
							<input type="button" value="删除" class="mws-button red small" onclick="deletelightbox(this)"/>
							<a class="btn btn-primary"" style="text-decoration:none;" href="/GuangJX/manage/device/getview/getreport?IEME=${light.IEME}">上报历史</a>
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
						<label>箱体名称：</label>
						<div class="mws-form-item large">
							<input id="lightboxname" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>IMEI编号：</label>
						<div class="mws-form-item large">
							<input id="lockid" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>所在区域：</label>
						<div class="mws-form-item large">
							<input id="area" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</rapid:override>

<%@ include file="../home/base.jsp"%>