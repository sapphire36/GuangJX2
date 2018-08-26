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
    
    function edititem(obj){
    	var id = $(obj).prev().val();
    	getpeople(id);
    	$("#editid").val(id);
		$("#editdialog").dialog("option", {
			modal : false
		}).dialog("open");
    }
    
    function deleteitem(obj){
		var id = $(obj).prev().prev().val();
		if (confirm("确定删除吗")) {
			$.ajax({
				type : "POST",
				url : "<%=basePath1%>/manage/constructor/deletepeople",
				data : {
					"ID" : id
				},
				success : function(data) {
					if (data.data == "true") {
						toastr.success("该成员删除成功!");
						location.reload();//刷新界面
					} else {
						toastr.error("该成员删除失败!");
					}
				}
			});
			return true;
		}
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
					doeditconstruct();
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
	
	function getpeople(id) {
		//添加函数
		$.ajax({
			type : "POST",
			url : "<%=basePath1%>/manage/constructor/getpeople",
			data : {"ID" : id },
			success : function(data) {
				if (data.data == "true") {
					//console.log(data.content)
					//address addtime belongto deposit id name loginname operation tel status passwd utype type
					$("#editname").val(data.content.name);
		            $("#editaddress").val(data.content.address); 
		 			$("#edittelphone").val(data.content.tel); 
					 $("#editusertype").val(data.content.utype);
					 $("#editmoney").val(data.content.deposit);
					 $("#editloginname").val(data.content.loginname);
					 $("#editloginpasswd").val(data.content.passwd); 
					//toastr.success(data.content);
				} else {
					toastr.error("获取用户 :"+id+"失败!");
				}
			}
		});
	}
	
	function doaddconstruct() {
		//添加函数
		var name = $("#name").val(); 
		var address = $("#address").val(); 
		var telphone = $("#telphone").val(); 
		var usertype = $("#usertype").val();
		var money = $("#money").val();
		var loginname = $("#loginname").val();
		var loginpasswd = $("#loginpasswd").val(); 
		var area = $("#area").val();
		var belongto = ${company.ID}; 
		$.ajax({
			type : "POST",
			url : "<%=basePath1%>/manage/constructor/addpeople",
			data : {
				"name" : name,
				"address" : address,
				"telphone" : telphone,
				"usertype":usertype,
				"money" : money,
				"loginname" : loginname,
				"loginpasswd" : loginpasswd,
				"belongto":belongto,
				"area":area
			},
			success : function(data) {
				if (data.data == "true") {
					toastr.success("添加成功!");
					location.reload();//刷新界面
				} else {
					toastr.error("添加失败!");
				}
			}
		});
	}
	
	function doeditconstruct() {
		//添加函数
		var id = $("#editid").val();
		var name = $("#editname").val(); 
		var address = $("#editaddress").val(); 
		var telphone = $("#edittelphone").val(); 
		var usertype = $("#editusertype").val();
		var money = $("#editmoney").val();
		var area = $("#editarea").val();
		var loginname = $("#editloginname").val();
		var loginpasswd = $("#editloginpasswd").val(); 
		var belongto = ${company.ID}; 
		$.ajax({
			type : "POST",
			url : "<%=basePath1%>/manage/constructor/editpeople",
			data : {
				"id":id,
				"editname" : name,
				"editaddress" : address,
				"edittelphone" : telphone,
				"editusertype":usertype,
				"editmoney" : money,
				"editloginname" : loginname,
				"editloginpasswd" : loginpasswd,
				"editbelongto":belongto,
				"editarea":area
			},
			success : function(data) {
				if (data.data == "true") {
					toastr.success("修改成功!");
					location.reload();//刷新界面
				} else {
					toastr.error("修改失败!");
				}
			}
		});
	}
	
	function disableuse(obj){
		var id = $(obj).prev().prev().prev().val();
		$.ajax({
			type : "POST",
			url : "<%=basePath1%>/manage/constructor/disableuse",
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
		var id = $(obj).prev().prev().prev().val();
		$.ajax({
			type : "POST",
			url : "<%=basePath1%>/manage/constructor/openuse",
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
		<input type="button" id="addconstruct" class="mws-button blue"
			value="添加" onclick="addconstruct(this)" />  
	     <input type="button"
			  class="mws-button green" value="返回"
			onclick="javascript:history.back(-1);" />
		<div class="mws-panel-header">
			<span class="mws-i-24 i-table-1">${company.NAME}</span>
		</div>
		<div class="mws-panel-body">
			<table class="mws-datatable-fn mws-table">
				<thead>
					<tr>
						<th>姓名</th>
						<th>地址</th>
						<th>电话</th>
						<th>押金</th>
						<th>区域</th>
						<th>类型</th>
						<th>小程序登录名</th>
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
							<td align="center">${people.AREANAME}</td>
							<c:if test="${people.UTYPE!=1}">   
						       <td align="center">施工人员</td>
							</c:if>
							<c:if test="${people.UTYPE==1}">   
                               <td align="center">安装人员</td>
							</c:if>
							<td align="center">${people.LOGINNAME}</td>
							<td align="center">${people.ADDTIME}</td>
							<td align="center">
							<input type="hidden"  value="${people.ID}"> 
                            <input type="button" value="编辑" class="mws-button blue small" onclick="edititem(this)"/> 
						    <input type="button" value="删除" class="mws-button red small" onclick="deleteitem(this)"/>
						    <c:if test="${people.STATUS==1}">   
                                 <input type="button" value="禁用"
							   class="mws-button gray small" onclick="disableuse(this)" /> 
							</c:if>
							<c:if test="${people.STATUS==0}">   
						      <input type="button" value="启用"
							  class="mws-button green small" onclick="openuse(this)" /> 
							</c:if>
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
						<label>用户类型：</label>
						<div class="mws-form-item large">
						   <select id="usertype">
								<option value="1">安装人员</option>
								<option value="2">施工人员</option>
							</select>
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
						<label>小程序登录名：</label>
						<div class="mws-form-item large">
							<input id="loginname" type="text" class="mws-textinput"
								title="input your loginname" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>小程序登录密码：</label>
						<div class="mws-form-item large">
							<input id="loginpasswd" type="text" class="mws-textinput"
								title="input your passwd" />
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
	
	<div id="editdialog">
		<div class="mws-panel">
			<form class="mws-form" action="#">
				<div class="mws-form-inline">
					<div class="mws-form-row">
						<label>姓名：</label>
						<div class="mws-form-item large">
						    <input id="editid" type="hidden"  value=""> 
							<input id="editname" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>用户类型：</label>
						<div class="mws-form-item large">
						   <select id="editusertype">
								<option value="1">安装人员</option>
								<option value="2">施工人员</option>
							</select>
						</div>
					</div>					
					<div class="mws-form-row">
						<label>家庭住址：</label>
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
						<label>小程序登录名：</label>
						<div class="mws-form-item large">
							<input id="editloginname" type="text" class="mws-textinput"
								title="input your loginname" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>小程序登录密码：</label>
						<div class="mws-form-item large">
							<input id="editloginpasswd" type="text" class="mws-textinput"
								title="input your passwd" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>押金：</label>
						<div class="mws-form-item large">
							<input id="editmoney" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</rapid:override>
<%@ include file="../home/base.jsp"%>