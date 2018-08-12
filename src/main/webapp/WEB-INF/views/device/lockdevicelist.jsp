<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path1 = request.getContextPath();
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1;
%>
<rapid:override name="title">
<title>NB-IoT锁管理</title>
<script type="text/javascript">
//script内容需要放在rapid override标签之间
$(document).ready(function(){
	//页面加载时自动执行该函数
	$("#doadd").click(function(){
		//绑定事件  #xx代表以xx为id的控件
		//参考文档:http://www.w3school.com.cn/jquery/jquery_ref_selectors.asp
		 doaddlockdevice();//执行添加
	  }); 
	  
	 $("#doedit").click(function(){
		//绑定 id为doedit的控件事件处理
		 doeditlockdevice();//执行编辑
	  });
	
	$("#refresh").click(function(){
		//绑定事件  #xx代表以xx为id的控件
		//参考文档:http://www.w3school.com.cn/jquery/jquery_ref_selectors.asp
	     location.reload();//刷新界面
	  });
	
	$("#body button.btn-primary").click(function(){
		//根据class来选择 编辑
		//var emeielem= $(this).parent().prev().prev().prev().prev();
		//获取emei内容 this代表当前点击的控件
		//详见:https://www.runoob.com/jquery/jquery-traversing-siblings.html
		//var emeitext=emeielem.text();
		var id=$(this).prev().val();
		$.ajax({
			type : "POST",
			url : "<%=basePath1%>/manage/device/getlockdevice",
			data:{"ID":id
			},
			success:function(data) {
                 if(data.data=="true"){
                	 $("#editdevicename").attr("value",data.NAME);
                	 $("#editemeiid").attr("value",data.IEME);
                	 $("#editid").attr("value",id);
                 }else{
                     toastr.error("数据库连接错误!");
                 }
           
			}
		});
	  });
	
	$("#body button.btn-info").click(function(){
		//根据class来选择 获取上报历史
		//var emeielem= $(this).parent().prev().prev().prev().prev();
		//获取emei内容 this代表当前点击的控件
		//详见:https://www.runoob.com/jquery/jquery-traversing-siblings.html
		//var emeitext=emeielem.text();
		var ieme=$(this).parent().prev().prev().prev().prev().prev().text();
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
	
	$("#body button.btn-default").click(function(){
		//根据class来选择 删除
		//var emeielem= $(this).parent().prev().prev().prev().prev();
		//获取emei内容 this代表当前点击的控件
		//详见:https://www.runoob.com/jquery/jquery-traversing-siblings.html
		//var emeitext=emeielem.text();
		var id=$(this).prev().prev().val();
	    if(confirm("确定删除吗")){  
			$.ajax({
				type : "POST",
				url : "<%=basePath1%>/manage/device/deletelockdevice",
				data:{"ID":id
				},
				success : function(data) {
					if(data.data=="true"){
						toastr.success("删除设备成功!");
						location.reload();//刷新界面
					}else{
						toastr.error("删除设备失败!");
					}
				}
			});
	       return true;  
	    }  
	});
});

function doaddlockdevice(){
	//添加函数
	var name=$("#devicename").val();//获取id为devicename的值
	var emei=$("#emeiid").val();//获取id为emeiid的值
	//根据选择器获取数据
	//参考文档:http://www.w3school.com.cn/jquery/attributes_attr.asp
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/device/addlockdevice",
		data:{"NAME":name,
			  "IEME":emei},
		success : function(data) {
			if(data.data=="true"){
				toastr.success("设备添加成功!");
				location.reload();//刷新界面
			}else{
				toastr.error("设备添加失败!");
			}
		}
	});
}

function doeditlockdevice(){
	//编辑函数
	var name=$("#editdevicename").val();//获取id为devicename的值
	var emei=$("#editemeiid").val();//获取id为emeiid的值
	var id=$("#editid").val();
	//根据选择器获取数据
	//参考文档:http://www.w3school.com.cn/jquery/attributes_attr.asp
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/device/editlockdevice",
		data:{"ID":id,
			  "NAME":name,
			  "IEME":emei},
		success : function(data) {
			if(data.data=="true"){
				toastr.success("设备修改成功!");
				location.reload();//刷新界面
			}else{
				toastr.error("设备修改失败!");
			}
		}
	});
}
 
</script>
</rapid:override>
<rapid:override name="content">
	<div class="panel panel-default">
		<div class="panel-heading">
			<button class="btn btn-primary" data-toggle="modal" data-backdrop="static" data-target="#add">添加</button>
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
							<th align="center">设备名称</th>
							<th align="center">IMEI编号</th>
							<th align="center">是否在线</th>
							<th align="center">设备状态</th>
							<th align="center">添加时间</th>
							<th align="center">注册状态</th>
							<th align="center"></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="p" items="${locklist}">
							<tr>
							    <td align="center">${p.ID}</td>
								<td align="center">${p.NAME}</td>
								<td align="center">${p.IEME}</td>
								
								 <c:if test="${p.ISONLINE==1}">
                                 <td align="center">在线</td>
                                 </c:if>
                                 <c:if test="${p.ISONLINE==0}">
                                 <td align="center">离线</td>
                                 </c:if>
                                 
                                 <c:if test="${p.STATUS==1}">
                                 <td align="center">开</td>
                                 </c:if>
                                 <c:if test="${p.STATUS==0}">
                                 <td align="center">关</td>
                                 </c:if>
                                 <td align="center">${p.ADDTIME}</td>
                                 <c:if test="${p.ISREGIST==1}">
                                 <td align="center">已注册</td>
                                 </c:if>
                                 <c:if test="${p.ISREGIST==0}">
                                 <td align="center"><a>未注册</a></td>
                                 </c:if>
                                 
                                 <td align="center">
                                    <input type="hidden" name="field＿name" value="${p.ID}"> 
									<button class="btn btn-primary" data-toggle="modal" data-backdrop="static" data-target="#edit">
										<i class="fa fa-edit "></i> 编辑
									</button>
									<button class="btn btn-default">
										<i class="fa fa-pencil"></i> 删除
									</button>
									<button class="btn btn-info" data-toggle="modal" data-backdrop="static" data-target="#report">上报历史</button>
								 </td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	
<div class="modal fade" id="add" tabindex="-1" role="dialog" aria-labelledby="add" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    添加NB-IoT锁
                </h4>
            </div>
            <div class="modal-body">
                    <table class="table table-striped">
                        <tr>
                            <td align="right">
                                设备名称：
                            </td>
                            <td align="left">
                                <input id="devicename" type="text" name="NAME" placeholder=""/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">
                                IMEI编号：
                            </td>
                            <td align="left">
                                <input id="emeiid" type="text" name="EMEI" placeholder=""/>
                            </td>
                        </tr>                                                                                       
                        <tr>
                            <td align="right">
                                <button id="doadd" class="btn btn-default" data-dismiss="modal">添加</button>
                            </td>
                            <td align="left">
                                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            </td>
                        </tr>
                    </table>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<div class="modal fade" id="edit" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel1">
                    编辑NB-IoT锁
                </h4>
            </div>
            <div class="modal-body">
                    <table class="table table-striped">
                        <tr>
                            <td align="right">
                                设备名称：
                            </td>
                            <td align="left">
                                <input id="editdevicename" type="text" name="NAME" placeholder=""/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">
                                IMEI编号：
                            </td>
                            <td align="left">
                                <input id="editemeiid" type="text" name="EMEI" placeholder=""/>
                            </td>
                        </tr>                                                                                       
                        <tr>
                            <td align="right">
                                <input id="editid" type="hidden" name="field＿name" value=""> 
                                <button id="doedit" class="btn btn-default" data-dismiss="modal">确认编辑</button>
                            </td>
                            <td align="left">
                                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            </td>
                        </tr>
                    </table>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>


<div class="modal fade text-center" id="report" tabindex="-1" role="dialog" aria-labelledby="report" aria-hidden="true">
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
             <div class="tableWrap" style="margin:20px;">
            
   				<table class="table-thead table-striped table-bordered table-hover">
	   				<colgroup> 
			            <col width="80px">
			            <col width="122px">
			            <col width="80px">
			            <col width="80px">
			            <col width="60px">
			            <col width="60px">
			            <col width="86px">
			            <col width="17px">
			        </colgroup>
					<thead>
						<tr>
						    <th align="center">箱体编号</th>
							<th align="center">设备IEMI编号</th>
							<th align="center">电池电压</th>
							<th align="center">机箱温度</th>
							<th align="center">门状态</th>
							<th align="center">锁状态</th>
							<th align="center">上报时间</th>
							<th></th>													
						</tr>
					</thead>
				</table>
	            <div class="modal-body"  style="height:300px; overflow-y:scroll; padding:0px;">
	   				<table class="table table-striped table-bordered table-hover" >
		   				<colgroup> 
					            <col width="80px">
					            <col width="122px">
					            <col width="80px">
					            <col width="80px">
					            <col width="60px">
					            <col width="60px">
					            <col width="80px">
					        </colgroup>
						<tbody id="reportcontent" >
					</table>
	            </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</rapid:override>

<%@ include file="../home/base.jsp"%>