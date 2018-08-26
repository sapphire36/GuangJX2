<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path1 = request.getContextPath();
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1;
%>
<rapid:override name="title">
	<title>角色管理</title>
<script src="/GuangJX/static/plugins/jquery.dataTables.js"></script>
<script type="text/javascript">
function refresh(obj){
	location.reload();//刷新界面
}

function editright(obj){
	var id=$(obj).prev().val();
	$("#roleID").attr("value",id);
	getrights(id);
	$("#editright").dialog("option", {
		modal : false
	}).dialog("open");
}

$(document).ready(function() {
	//页面加载时自动执行该函数
	$(".mws-datatable-fn").dataTable({
		sPaginationType : "full_numbers"
	});
	
	$("#editright").dialog({
		autoOpen : false,
		title : "权限编辑",
		modal : true,
		width : "960",
		buttons : [ {
			text : "确定",
			click : function() {
				doeditright();
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

function doeditright() {
    var checkboxArray = [];//初始化空数组，用来存放checkbox对象。
    var inputs = document.getElementsByTagName("input");//获取所有的input标签对象
    for(var i=0;i<inputs.length;i++){
        var obj = inputs[i];
        if(obj.type=='checkbox'){
            if(obj.checked==true){
               checkboxArray.push(obj.value);
            }    
        }
     }
    var rights=""
    for(var j = 0,len = checkboxArray.length; j < len; j++){
    	if(j==(len-1))
    		rights=rights+checkboxArray[j].toString()
    	else
    		rights=rights+checkboxArray[j].toString()+','
    }
	var id = $("#roleID").val();
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/role/doeditright",
		data : {
			"ID" : id,
			"RIHGT" :rights
		},
		success : function(data) {
			if (data.data == "true") {
				toastr.success("权限更新成功!");
			} else {
				toastr.error("权限更新失败!");
			}
		}
	});
}

function getrights(obj) {
	$.ajax({
		type : "POST",
		url : "<%=basePath1%>/manage/role/getrights",
		data : {
			"ID" : obj
		},
		success : function(data) {
			if(data.data=="true"){
				$("#rightcontent").html(data.content); 
			}else{
				toastr.error("error");
			}
		}
	});
}

function pcheck(j)
{
    var ch=document.getElementsByName(j);
    if(document.getElementsByName(j)[0].checked==true)
    {
        for(var i=0;i<ch.length;i++)
        {
            ch[i].checked=true;
        }
    }else{
            for(var i=0;i<ch.length;i++)
            {
                ch[i].checked=false;
            }
     }
}

function ccheck(obj)
{
   var ch=document.getElementsByName($(obj).attr("name"));
   if(obj.checked==true){
        ch[0].checked=true;
    }else{
         var flag=false;
         for(var i=1;i<ch.length;i++){
	           if(ch[i].checked==true){
	               flag=true;
	               break;
	           }
          }
           if(flag){
                ch[0].checked=true;
           }else{
                ch[0].checked=false;
           }

    }
} 
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
							    <input type="hidden"  value="${role.ID}"> 
								<input type="button" value="权限设置"
									class="mws-button blue small" onclick="editright(this)" />
								<input type="button" value="编辑"
									class="mws-button blue small" onclick="editlightbox(this)" /> 
								<input
									type="button" value="删除" class="mws-button red small"
									onclick="deletelightbox(this)" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	
	<div id="editright">
		<div class="mws-panel">
			<form class="mws-form" action="#">
			    <input id="roleID" type="hidden"  value=""> 
				<div class="mws-form-inline">
                    <div id="rightcontent"></div>
				</div>
			</form>
		</div>
	</div>
</rapid:override>
<%@ include file="../home/base.jsp"%>