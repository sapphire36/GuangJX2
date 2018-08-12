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
	<title>${company.NAME}人员管理</title>
	<script src="/GuangJX/static/plugins/jquery.dataTables.js"></script>
	<script type="text/javascript">
 
    function addconstruct(obj){
		$("#adddialog").dialog("option", {
			modal : false
		}).dialog("open");
    }
	function doaddconstruct() {
		//添加函数
		var name = $("#name").val(); 
		var address = $("#address").val(); 
		var telphone = $("#telphone").val(); 
		var money = $("#money").val(); 
		var belongto = ${company.ID}; 
		$.ajax({
			type : "POST",
			url : "<%=basePath1%>/manage/constructor/addpeople",
			data : {
				"name" : name,
				"address" : address,
				"telphone" : telphone,
				"money" : money,
				"belongto":belongto
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
	function showmessage(message) {
		$.jGrowl(message, {
			sticky : false,
			position : "top-right"
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
					showmessage("no");
					$(this).dialog("close");
				}
			} ]
		});
	});
	</script>
</rapid:override>
<rapid:override name="content">
	<div class="mws-panel grid_8">
	<input type="button"
			  class="mws-button green" value="返回"
			onclick="javascript:history.back(-1);" />
		<input type="button" id="addconstruct" class="mws-button blue"
			value="添加" onclick="addconstruct(this)" />  

		<div class="mws-panel-header">
			<span class="mws-i-24 i-table-1">${company.NAME}</span>
		</div>
		<div class="mws-panel-body">
			<table class="mws-datatable-fn mws-table">
				<thead>
					<tr>
						<th>姓名</th>
						<th>家庭地址</th>
						<th>电话</th>
						<th>押金</th>
						<th>状态</th>
						<th>注册日期</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="people" items="${peoplelist}">
						<tr>
							<td align="center">${people.NAME}</td>
							<td align="center">${people.ADDRESS}</td>
							<td align="center">${people.TEL}</td>
							<td align="center">${people.DEPOSIT}</td>
							<td align="center">${people.STATUS}</td>
							<td align="center">${people.ADDTIME}</td>
							<td align="center">
                             <input type="button" value="编辑" class="mws-button blue small" onclick="editlightbox(this)"/> 
							 <input type="button" value="删除" class="mws-button red small" onclick="deletelightbox(this)"/>
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
						<label>姓名：</label>
						<div class="mws-form-item large">
							<input id="name" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>家庭住址：</label>
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
					<div class="mws-form-row">
						<label>押金：</label>
						<div class="mws-form-item large">
							<input id="money" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</rapid:override>
<%@ include file="../home/base.jsp"%>