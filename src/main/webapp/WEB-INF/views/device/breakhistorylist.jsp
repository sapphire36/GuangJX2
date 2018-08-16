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

function handle(obj){
	var id = $(obj).prev().val();
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/device/handleBreak",
		data : {
			"ID" : id
		},
		success : function(data) {
			if (data.data == "true") {
				toastr.success("处理成功!");
				location.reload();//刷新界面
			} else {
				toastr.error("处理失败!");
			}
		}
	});
}

function deleteAll(){
    if(confirm("确定清空上报数据吗")){  
		$.ajax({
			type : "POST",
			url : "<%=basePath1%>/manage/device/deleteAllBreakhistorylist",
					data : {
						"ID" : "tt"
					},
					success : function(data) {
						if (data.ret == "true") {
							toastr.success("清空成功!");
							location.reload();//刷新界面
						} else {
							toastr.error("清空失败!");
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
			id="refresh" class="mws-button blue" value="更新"
			onclick="refresh(this)" />
            <input type="button"
			id="deleteAll" class="mws-button red" value="清空"
			onclick="deleteAll()" />
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
						<th align="center">处理状态</th>
						<th align="center">时间</th>	
						<th align="center">操作</th>		
					</tr>
				</thead>
				<tbody>
					<c:forEach var="bre" items="${breaklist}">
						<tr>
							<td align="center">${bre.ID}</td>
							<td align="center">${bre.IEME}</td>
							<td align="center">${bre.TYPE}</td>
							<c:if test="${bre.ISHADND==0}">   
							     <td align="center">未处理</td>
							 </c:if>
							 <c:if test="${bre.ISHADND==1}">
                                <td align="center">已处理</td>
                             </c:if>
							<td align="center">${bre.ADDTIME}</td>								                                                                
							<td align="center">
								<input type="hidden"  value="${bre.ID}"> 
								<c:if test="${bre.ISHADND==0}"> 
									<input type="button" value="处理" class="mws-button blue small" onclick="handle(this)"/>
						        </c:if>
						    </td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</rapid:override>

<%@ include file="../home/base.jsp"%>