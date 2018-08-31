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
</script>
</rapid:override>
<rapid:override name="content">
	<div class="mws-panel grid_8">
		<div class="mws-panel-header">
			<span class="mws-i-24 i-list">${lightbox.NAME}</span>
		</div>
		<div class="mws-panel-body">
			<form class="mws-form" action="/GuangJX/manage/device/savelightbox">
				<div class="mws-form-inline">
					<div class="mws-form-row">
						<label>箱体名称</label>
						<div class="mws-form-item small">
							<input type="text" id="name" class="mws-textinput"
								value="${lightbox.NAME}" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>安装位置</label>
						<div class="mws-form-item small">
							<input id="location" type="text" class="mws-textinput"
								value="${lightbox.LOCATION}" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>锁IMEI编号</label>
						<div class="mws-form-item small">
							<input id="imei" type="text" class="mws-textinput"
								value="${lightbox.IEME}" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>安装人员</label>
						<div class="mws-form-item small">
							<input id="people" type="text" class="mws-textinput"
								value="${lightbox.PEOPLE}" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>施工状态</label>
						<div class="mws-form-item large">
					       <c:if test="${lightbox.CONSTRUCTSTATUS==1}">
                              <label>正在施工</label> 
                           </c:if>
                           <c:if test="${lightbox.CONSTRUCTSTATUS==0}">
                              <label>未施工</label>  
                           </c:if>
						</div>
					</div>
					<div class="mws-form-row">
						<label>门状态</label>
						<div class="mws-form-item large">
					       <c:if test="${lightbox.DOORSTATUS==1}">
                              <label>开</label> 
                           </c:if>
                           <c:if test="${lightbox.DOORSTATUS==0}">
                              <label>关</label>  
                           </c:if>
						</div>
					</div>
					<div class="mws-form-row">
						<label>锁状态</label>
						<div class="mws-form-item large">
					       <c:if test="${lightbox.UNLOCKSTATUS==1}">
                              <label>开</label> 
                           </c:if>
                           <c:if test="${lightbox.UNLOCKSTATUS==0}">
                              <label>关</label>  
                           </c:if>
						</div>
					</div>					
					<div class="mws-form-row">
						<label>操作</label>
						<div class="mws-form-item large">
							<input  id="openLock" type="button" value="手动开锁" class="mws-button green" />
							<input id="closeLock" type="button" value="手动关锁" class="mws-button orange" />
						</div>
					</div>
				</div>
				<div class="mws-button-row">
					<input type="button" value="保存" class="mws-button blue" /> <input
						type="button" class="mws-button green" value="返回"
						onclick="javascript:history.back(-1);" />
				</div>
			</form>
		</div>
	</div>
</rapid:override>
<%@ include file="../home/base.jsp"%>