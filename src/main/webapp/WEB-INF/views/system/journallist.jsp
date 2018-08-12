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
	<title>系统日志</title>
	<script src="/GuangJX/static/plugins/jquery.dataTables.js"></script>
	<script type="text/javascript">
function refresh(obj){
	location.reload();//刷新界面
}

function deleteAll(obj){
    if(confirm("确定清空日志吗")){  
		$.ajax({
			type : "POST",
			url : "<%=basePath1%>/manage/system/deletealljournal",
					data : {
						"ID" : "tt"
					},
					success : function(data) {
						if (data.ret == "true") {
							toastr.success("清空日志成功!");
							location.reload();//刷新界面
						} else {
							toastr.error("清空日志失败!");
						}
					}
				});
				return true;
	     }
}

$(document).ready(function(){
	//页面加载时自动执行该函数
    $(".mws-datatable-fn").dataTable({sPaginationType: "full_numbers"});
 });
</script>
</rapid:override>
<rapid:override name="content">
	<div class="mws-panel grid_8">
		<input type="button"   class="mws-button red" value="清空"
			onclick="deleteAll(this)" /> <input type="button" id="refresh"
			class="mws-button blue" value="更新" onclick="refresh(this)" />
		<div class="mws-panel-header">
			<span class="mws-i-24 i-table-1">系统日志信息表</span>
		</div>
		<div class="mws-panel-body">
			<table class="mws-datatable-fn mws-table">
				<thead>
					<tr>
						<th>日志类型</th>
						<th>日志内容</th>
						<th>地区</th>
						<th>时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="jour" items="${journallist}">
						<tr class="gradeX">
							<c:if test="${jour.TYPE==1}">
								<td align="center">消息</td>
							</c:if>
							<c:if test="${jour.TYPE==2}">
								<td align="center">警告</td>
							</c:if>
							<c:if test="${jour.TYPE==3}">
								<td align="center">故障</td>
							</c:if>
		                    <td align="center">${jour.CONTENT}</td>
		                    <td align="center">${jour.AREANAME}</td>
							<td align="center">${jour.ADDTIME}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</rapid:override>
<%@ include file="../home/base.jsp"%>