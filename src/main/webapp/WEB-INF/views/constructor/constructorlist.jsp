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
	<title>施工方管理</title>
	<script src="/GuangJX/static/plugins/jquery.dataTables.js"></script>
	<script type="text/javascript">
	function refresh(obj){
		location.reload();//刷新界面
	}
	
    function openWeixin(obj){
		$("#Weixin").dialog("option", {
			modal : false
		}).dialog("open");
    }
	
    function addconstruct(obj){
		$("#adddialog").dialog("option", {
			modal : false
		}).dialog("open");
    }
	function doaddconstruct() {
		//添加函数
		var name = $("#name").val();//获取id为name的值
		var address = $("#address").val();//获取id为address的值
		var telphone = $("#telphone").val();//获取id为telphone的值
		$.ajax({
			type : "POST",
			url : "<%=basePath1%>/manage/constructor/addconstructor",
			data : {
				"name" : name,
				"address" : address,
				"telphone" : telphone
			},
			success : function(data) {
				if (data.data == "true") {
					showmessage("添加成功!");
					location.reload();//刷新界面
				} else {
					showmessage("添加失败!");
				}
			}
		});
	}
 
	$(document).ready(function() {
		//页面加载时自动执行该函数
		$(".mws-datatable-fn").dataTable({
			sPaginationType : "full_numbers"
		});
		
		$("#adddialog").dialog({
			autoOpen : false,
			title : "添加施工方",
			modal : true,
			width : "640",
			buttons : [ {
				text : "确定",
				click : function() {
					doaddconstruct();
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
			title : "编辑施工方",
			modal : true,
			width : "640",
			buttons : [ {
				text : "确定",
				click : function() {
					doeditorgnization();
					$(this).dialog("close");
				}
			}, {
				text : "返回",
				click : function() {
					$(this).dialog("close");
				}
			} ]
		});
		
		$("#Weixin").dialog({
			autoOpen : false,
			title : "小程序二维码",
			modal : true,
			width : "520",
			buttons : [ {
				text : "确定",
				click : function() {
					$(this).dialog("close");
				}
			} ]
		});
	});
	
	function deleteorgnization(obj){
		var id = $(obj).prev().prev().val();
		if (confirm("确定删除吗")) {
			$.ajax({
				type : "POST",
				url : "<%=basePath1%>/manage/constructor/deleteorgnization",
				data : {
					"ID" : id
				},
				success : function(data) {
					if (data.data == "true") {
						toastr.success("删除单位成功!");
						location.reload();//刷新界面
					} else {
						toastr.error("删除单位失败!");
					}
				}
			});
			return true;
		}
	}
	
	function editorgnization(obj){
		var editname=$(obj).parent().prev().prev().prev().text();
		var editaddress=$(obj).parent().prev().prev().text();
		var edittelphone=$(obj).parent().prev().text();
		var id = $(obj).prev().val();
		$("#editorgid").val(id);
		$("#editname").val(editname);
		$("#editaddress").val(editaddress);
		$("#edittelphone").val(edittelphone);
		$("#editdialog").dialog("option", {
			modal : false
		}).dialog("open");
	}
	
	function doeditorgnization() {
		//edit函数
		var editname = $("#editname").val(); 
		var editaddress = $("#editaddress").val(); 
		var edittelphone = $("#edittelphone").val();
		var id = $("#editorgid").val();
		$.ajax({
			type : "POST",
			url : "<%=basePath1%>/manage/constructor/updateorgnization",
			data : {
				"ID": id,
				"editname" : editname,
				"editaddress" : editaddress,
				"edittelphone" : edittelphone
			},
			success : function(data) {
				if (data.data == "true") {
					toastr.success("单位更新成功!");
					location.reload();//刷新界面
				} else {
					toastr.success("单位更新失败!");
				}
			}
		});
	}
	</script>
</rapid:override>
<rapid:override name="content">
	<div class="mws-panel grid_8">
		<input type="button" id="addconstruct" class="mws-button blue"
			value="添加" onclick="addconstruct(this)" /> <input type="button"
			id="refresh" class="mws-button blue" value="更新"
			onclick="refresh(this)" />
			<input type="button"
			id="weixin" class="mws-button blue" value="小程序入口"
			onclick="openWeixin(this)" />

		<div class="mws-panel-header">
			<span class="mws-i-24 i-table-1">光交箱信息表</span>
		</div>
		<div class="mws-panel-body">
			<table class="mws-datatable-fn mws-table">
				<thead>
					<tr>
						<th>单位名称</th>
						<th>公司地址</th>
						<th>电话</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="org" items="${constructorllist}">
						<tr>
							<td align="center"><a style="text-decoration:none;" href="/GuangJX/manage/constructor/getview/constructordeteil?id=${org.ID}">${org.NAME}</a></td>
							<td align="center">${org.ADDRESS}</td>
							<td align="center">${org.TEL}</td>
							<td align="center">
								 <input type="hidden"  value="${org.ID}">
	                             <input type="button" value="编辑" class="mws-button blue small" onclick="editorgnization(this)"/> 
								 <input type="button" value="删除" class="mws-button red small" onclick="deleteorgnization(this)"/>
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
						<label>单位名称：</label>
						<div class="mws-form-item large">
							<input id="name" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>公司地址：</label>
						<div class="mws-form-item large">
							<input id="address" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>电话：</label>
						<div class="mws-form-item large">
							<input id="telphone" type="text" class="mws-textinput"
								title="input your email" />
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
						<label>单位名称：</label>
						<div class="mws-form-item large">
						    <input id="editorgid" type="hidden" name="field_name" value="value" />
							<input id="editname" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>公司地址：</label>
						<div class="mws-form-item large">
							<input id="editaddress" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>电话：</label>
						<div class="mws-form-item large">
							<input id="edittelphone" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	
	<div id="Weixin">
		<div class="mws-panel">
 			<img src="/GuangJX/static/images/app.jpg"/>
		</div>
	</div>
</rapid:override>
<%@ include file="../home/base.jsp"%>