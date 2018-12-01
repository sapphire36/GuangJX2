<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path1 = request.getContextPath();
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1;
%>
<rapid:override name="title">
	<title>系统设置</title>
	restart
<script type="text/javascript">
$(document).ready(function(){
 }); 
</script>
</rapid:override>
<rapid:override name="content">
	<div class="mws-panel grid_8">
		<div class="mws-panel-header">
			<span class="mws-i-24 i-list">报警触发器</span>
		</div>
		<div class="mws-panel-body">
			<form class="mws-form" action="/manage/system/getview/savewarntriggerset" method="post">
				<div class="mws-form-inline">
					<div class="mws-form-row">
						<label>温度最大值</label>
						<div class="mws-form-item small">
							<input name="max_temprature" type="text" class="mws-textinput" value="${ max_temprature }" />
						</div>
						<label>温度最小值</label>
						<div class="mws-form-item small">
							<input name="min_temprature" type="text" class="mws-textinput" value="${ min_temprature }" />
						</div>
					</div>
					<div class="mws-form-row">
						<label>电压最大值</label>
						<div class="mws-form-item small">
							<input name="max_volume" type="text" class="mws-textinput" value="${max_volume}" />
						</div>
						<label>电压最大值</label>
						<div class="mws-form-item small">
							<input name="min_volume" type="text" class="mws-textinput" value="${min_volume}" />
						</div>
					</div>
				</div>
				<div class="mws-button-row">
					<input type="submit" value="保存" class="mws-button blue" /> 
						       <input type="button"
			  class="mws-button green" value="返回"
			onclick="javascript:history.back(-1);" />
				</div>
			</form>
		</div>
	</div>
</rapid:override>
<%@ include file="../home/base.jsp"%>
