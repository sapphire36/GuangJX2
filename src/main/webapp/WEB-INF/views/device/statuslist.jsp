<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path1 = request.getContextPath();
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1;
%>
<rapid:override name="title">
<title>光交箱状态管理</title>
<script type="text/javascript">
//script内容需要放在rapid override标签之间
$(document).ready(function(){		
	$("#refresh").click(function(){
		//绑定事件  #xx代表以xx为id的控件
		//参考文档:http://www.w3school.com.cn/jquery/jquery_ref_selectors.asp
	     location.reload();//刷新界面
	  })
});

$("#body button.btn-info").click(function(){
	//根据class来选择 获取上报历史
	//var emeielem= $(this).parent().prev().prev().prev().prev();
	//获取emei内容 this代表当前点击的控件
	//详见:https://www.runoob.com/jquery/jquery-traversing-siblings.html
	//var emeitext=emeielem.text();
	var ieme=$(this).parent().prev().prev().prev().prev().prev().prev().prev().text();
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/device/getstatuslist",
		data:{"IEME":ieme
		},
		success:function(data) {
             if(data.data=="true"){
            	 $("#reportcontent").html(data.content); 
             }else{
                 toastr.error("数据库连接错误!");
             }
       
		}
	});
  });
</script>
</rapid:override>
<rapid:override name="content">
	<div class="panel panel-default">
		<div class="panel-heading">
			<button id="refresh" class="btn btn-default">
				<i class=" fa fa-refresh "></i>更新
			</button>
		</div>
		<div class="panel-body" id="body">
			<div class="table-responsive">
				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
						    <th align="center">设备ID</th>
							<th align="center">设备IEMI编号</th>
							<th align="center">电池电压</th>
							<th align="center">机箱温度</th>
							<th align="center">门状态</th>
							<th align="center">锁状态</th>
							<th align="center">上报时间</th>	
							<th align="center"></th>												 						
						</tr>
					</thead>
					<tbody>
						<c:forEach var="sta" items="${statuslist}">
							<tr>
							    <td align="center">${sta.ID}</td>
								<td align="center">${sta.IEME}</td>
								<td align="center">${sta.VOLTAGE}</td>
								<td align="center">${sta.TEMPERATURE}</td>
								<c:if test="${sta.DOORSTATUS==1}">
                                <td align="center">开</td>
                                </c:if>
                                <c:if test="${sta.DOORSTATUS==0}">
                                <td align="center">关</td>
                                </c:if>
							    <c:if test="${sta.UNLOCKSTATUS==1}">
                                <td align="center">开</td>
                                </c:if>
                                <c:if test="${sta.UNLOCKSTATUS==0}">
                                <td align="center">关</td>
                                </c:if>
								<td align="center">${sta.ADDTIME}</td>	
								
								<td align="center">                               
								<button class="btn btn-info" data-toggle="modal" data-backdrop="static" data-target="#reportsta">上报历史</button>
								</td>							                                                                
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	<div class="modal fade text-center" id="reportsta" tabindex="-1" role="dialog" aria-labelledby="report" aria-hidden="true">
    <div class="modal-dialog" style="display: inline-block; width: auto;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel2">
                                                          上报历史
                </h4>
            </div>
            <div class="modal-body">
   				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
						    <th align="center">设备ID编号</th>
							<th align="center">设备IEMI编号</th>
							<th align="center">电池电压</th>
							<th align="center">机箱温度</th>
							<th align="center">门状态</th>
							<th align="center">锁状态</th>
							<th align="center">上报时间</th>							
						</tr>
					</thead>
					<tbody id="reportcontent">
 
				</table>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</rapid:override>

<%@ include file="../home/base.jsp"%>