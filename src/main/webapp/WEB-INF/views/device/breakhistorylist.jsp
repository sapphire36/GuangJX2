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
<title>故障历史表</title>
<script src="/GuangJX/static/plugins/jquery.dataTables.js"></script>
<script type="text/javascript">
function refresh(obj){
	location.reload();//刷新界面
}
$(document).ready(function() {
	//页面加载时自动执行该函数
	$(".mws-datatable-fn").dataTable({
		sPaginationType : "full_numbers"
	});
});
</script>
</rapid:override>
<rapid:override name="content">
	<div class="mws-panel grid_8">
		<input type="button"
			id="refresh" class="mws-button blue" value="更新"
			onclick="refresh(this)" />

		<div class="mws-panel-header">
			<span class="mws-i-24 i-table-1">光交箱信息表</span>
		</div>
		<div class="mws-panel-body">
			<table class="mws-datatable-fn mws-table">
				<thead>
					<tr>
						<th align="center">设备ID</th>						
						<th align="center">设备IEMI编号</th>
						<th align="center">故障原因</th>	
						<th align="center">时间</th>		
					</tr>
				</thead>
				<tbody>
					<c:forEach var="bre" items="${breaklist}">
						<tr>
							<td align="center">${bre.ID}</td>
							<td align="center">${bre.IEME}</td>
							<td align="center">${bre.TYPE}</td>
							<td align="center">${bre.ADDTIME}</td>								                                                                
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</rapid:override>

<%@ include file="../home/base.jsp"%>