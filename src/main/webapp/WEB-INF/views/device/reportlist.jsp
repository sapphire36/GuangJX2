<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path1 = request.getContextPath();
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1;
%>
<rapid:override name="title">
	<title>上报历史</title>
	<script src="/GuangJX/static/plugins/jquery.dataTables.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		//页面加载时自动执行该函数
		$("#reporttable").dataTable({
			sPaginationType : "full_numbers"
		});
	});
	
	function deleteAll(){
	    if(confirm("确定清空上报数据吗")){  
			$.ajax({
				type : "POST",
				url : "<%=basePath1%>/manage/device/deleteAllStatus",
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
</script>
</rapid:override>
<rapid:override name="content">
	<div class="mws-panel grid_8">
	       <input type="button"
			id="refresh" class="mws-button green" value="返回"
			onclick="javascript:history.back(-1);" />
            <input type="button"
			id="deleteAll" class="mws-button red" value="清空"
			onclick="deleteAll()" />

		<div class="mws-panel-header">
			<span class="mws-i-24 i-table-1">上报历史</span>
		</div>
		<div class="mws-panel-body">
			<table id="reporttable" class="mws-datatable-fn mws-table">
				<thead>
					<tr>
						<th align="center">IEMI</th>
						<th align="center">电压</th>
						<th align="center">温度</th>
						<th align="center">门状态</th>
	                    <th align="center">锁开状态</th>
					    <th align="center">锁关状态</th>
						<th align="center">时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="status" items="${statuslist}">
						<tr class="gradeX">
							<td align="center">${status.IEME}</td>
							<td align="center">${status.VOLTAGE}</td>
							<td align="center">${status.TEMPERATURE}</td>
							 <c:if test="${status.DOORSTATUS==1}">   
							     <td align="center">关</td>
							 </c:if>
							 <c:if test="${status.DOORSTATUS==0}">   
							     <td align="center">开</td>
							 </c:if>
							 <c:if test="${status.LOCKSTATUS==1}">
                                <td align="center">开</td>
                             </c:if>
                             <c:if test="${status.LOCKSTATUS==0}">
                                <td align="center">关</td>
                             </c:if>
							 <c:if test="${status.UNLOCKSTATUS==1}">
                                <td align="center">开</td>
                             </c:if>
                             <c:if test="${status.UNLOCKSTATUS==0}">
                                <td align="center">关</td>
                             </c:if>
							<td align="center">${status.ADDTIME}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</rapid:override>
<%@ include file="../home/base.jsp"%>