<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path1 = request.getContextPath();
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
%>
<rapid:override name="title">
	<title>角色管理</title>
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
		<input type="button" id="addlightbox" class="mws-button blue"
			value="添加" onclick="addlightbox(this)" /> <input type="button"
			id="refresh" class="mws-button blue" value="更新"
			onclick="refresh(this)" />

		<div class="mws-panel-header">
			<span class="mws-i-24 i-table-1">角色信息表</span>
		</div>
		<div class="mws-panel-body">
			<table class="mws-datatable-fn mws-table">
				<thead>
					<tr>
						<th align="center">角色编号</th>
						<th align="center">角色名称</th>
						<th align="center">创建时间</th>
						<th align="center">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="role" items="${rolelist}">
						<tr class="gradeX">
							<td align="center">${role.ID}</td>
							<td align="center">${role.NAME}</td>
							<td align="center">${role.ADDTIME}</td>
							<td align="center">
							<input type="button" value="权限设置"
								class="mws-button blue small" onclick="editlightbox(this)" />
							<input type="button" value="编辑"
								class="mws-button blue small" onclick="editlightbox(this)" /> <input
								type="button" value="删除" class="mws-button red small"
								onclick="deletelightbox(this)" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</rapid:override>
<%@ include file="../home/base.jsp"%>