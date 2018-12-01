<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@page import="org.kzcw.service.LightboxService"%>
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
	<title>安装审核</title>
	<script type="text/javascript">
	function showdialog(obj){
		var text=$(obj).text();
		var data = text.split(':');
		$("#editlightboxname").attr("value",data[0]);
		$("#editlockid").attr("value",data[1]);
		$("#editlocation").attr("value",data[2]);
		$("#editarea").attr("value",data[3]);
		
		$("#opendialog").dialog("option", {
			modal : false
		}).dialog("open");
	}
$(document).ready(function(e) {
	 getchecklist();
	 $("#opendialog").dialog({
		autoOpen : false,
		title : "安装审核",
		modal : true,
		width : "640",
		buttons : [ {
			text : "通过审核",
			click : function() {
				dopass();//执行编辑
				$(this).dialog("close");
			}
		}, {
			text : "拒绝审核",
			click : function() {
				doreject();//执行编辑
				$(this).dialog("close");
			}
		} ]
	});
});
function dopass(){
	//通过审核 editlightboxname editlockid editspec editmadetype editlocation editpeople
	var editlightboxname=$("#editlightboxname").val();//获取id为lightboxname的值
	var editlockid=$("#editlockid").val();//获取id为lockid的值
	var editlocation=$("#editlocation").val();//获取id为location的值
	var editpeople=$("#editpeople").val();//获取id为people的值
	//根据选择器获取数据
	//参考文档:http://www.w3school.com.cn/jquery/attributes_attr.asp
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/device/doPassCheck",
		data:{"editlightboxname":editlightboxname,
			  "editlockid":editlockid,
			  "editlocation":editlocation,
			  "editpeople":editpeople
			  },
		success : function(data) {
			if(data.data=="true"){
				toastr.success("审核成功!");
			}else{
				toastr.error("服务器发生异常!");
			}
		}
	});
}

function doreject(){
	//拒绝审核
	var editlockid=$("#editlockid").val();//获取id为lockid的值
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/device/doRejectCheck",
		data:{
			  "editlockid":editlockid
			  },
		success : function(data) {
			if(data.data=="true"){
				toastr.success("已拒绝该申请!");
			}else{
				toastr.error("服务器发生异常!");
			}
		}
	});
}
function getchecklist(){
	//获取审核队列
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/device/getchecklist",
				data : "test",
				success : function(data) {
					$("#applylist").html(data.data);
				}
			});
			setTimeout(getchecklist, 1000); //设置定时器,每1000ms执行一次
		}
	</script>
</rapid:override>
<rapid:override name="content">
	<div class="mws-panel grid_12">
		<div class="mws-panel-header">
			<span class="mws-i-24 i-books-2">申请审核队列</span>
		</div>
		<div class="mws-panel-body">
			<ul class="mws-summary" id="applylist">
			</ul>
		</div>
	</div>

	<div id="opendialog">
		<div class="mws-panel">
			<form class="mws-form" action="#">
				<div class="mws-form-inline">
					<div class="mws-form-row">
						<img id="newpic" src="/manage/device/showImage"
							width="220" height="300" alt="Applied picture" />
					</div>
					<div class="mws-form-row">
						<label>箱体名称：</label>
						<div class="mws-form-item large">
							<input id="editlightboxname" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>IMEI编号：</label>
						<div class="mws-form-item large">
							<input id="editlockid" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>安装位置：</label>
						<div class="mws-form-item large">
							<input id="editlocation" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>安装人员：</label>
						<div class="mws-form-item large">
							<input id="editpeople" type="text" class="mws-textinput"
								title="input your email" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>安装区域：</label>
						<div class="mws-form-item large">
							<input id="editarea" type="text" class="mws-textinput"
								title="input your area" disabled="disabled"/>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</rapid:override>
<%@ include file="../home/base.jsp"%>
