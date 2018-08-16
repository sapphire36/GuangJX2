<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>  
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<!-- Required Stylesheets -->
<link rel="stylesheet" type="text/css" href="/GuangJX/static/css/reset.css" media="screen" />
<link rel="stylesheet" type="text/css" href="/GuangJX/static/css/text.css" media="screen" />
<link rel="stylesheet" type="text/css" href="/GuangJX/static/css/fonts/ptsans/stylesheet.css" media="screen" />
<link rel="stylesheet" type="text/css" href="/GuangJX/static/css/fluid.css" media="screen" />
<link rel="stylesheet" type="text/css" href="/GuangJX/static/css/mws.style.css" media="screen" />
<link rel="stylesheet" type="text/css" href="/GuangJX/static/css/icons/icons.css" media="screen" />
<!-- Demo and Plugin Stylesheets -->
<link rel="stylesheet" type="text/css" href="/GuangJX/static/plugins/spinner/spinner.css" media="screen" />
<link rel="stylesheet" type="text/css" href="/GuangJX/static/css/jui/jquery.ui.css" media="screen" />
<!-- Theme Stylesheet -->
<link rel="stylesheet" type="text/css" href="/GuangJX/static/css/mws.theme.css" media="screen" />

<!-- JavaScript Plugins -->
<script  src="/GuangJX/static/js/jquery-1.7.1.min.js"></script>
<!--[if lt IE 9]>
<script  src="/GuangJX/static/plugins/flot/excanvas.min.js"></script>
<![endif]-->
<script  src="/GuangJX/static/plugins/flot/jquery.flot.min.js"></script>
<script  src="/GuangJX/static/plugins/flot/jquery.flot.pie.min.js"></script>
<script  src="/GuangJX/static/plugins/flot/jquery.flot.stack.min.js"></script>
<script  src="/GuangJX/static/plugins/flot/jquery.flot.resize.min.js"></script>
<script  src="/GuangJX/static/plugins/colorpicker/colorpicker.js"></script>
<script  src="/GuangJX/static/plugins/spinner/ui.spinner.js"></script>
<script  src="/GuangJX/static/js/mws.js"></script>
<script  src="/GuangJX/static/js/jquery-1.7.1.min.js"></script>
<script  src="/GuangJX/static/js/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="/GuangJX/static/plugins/jgrowl/jquery.jgrowl.css" media="screen" />
<script  src="/GuangJX/static/plugins/jgrowl/jquery.jgrowl.js"></script>
<link href="/GuangJX/static/css/toastr.css" rel="stylesheet" />
<script src="/GuangJX/static/js/toastr.js"></script>
<script type="text/javascript">
function showmessage(message,show) {
	$.jGrowl(message, {
		sticky : show,
		position : "top-right"
	});
}

$(document).ready(function(){		
	getwainning();
	getmessage();
});
function getwainning(){
	//获取报警信息
	$.ajax({
		type : "POST",
		url : "<%=basePath%>/manage/home/getwainning",
		data :"test",
		success : function(data) {
			if(data.data=="true"){
				if(data.size>0){
					$("#wariningsize").show();
					$("#wariningsize").text(data.size);
				}else{
					$("#wariningsize").hide();
				}
				$("#warningmenu").html(data.message);
			} 
		}
	});
	setTimeout(getwainning,1000);
}

function getmessage(){
	//获取报警信息
	$.ajax({
		type : "POST",
		url : "<%=basePath%>/manage/home/getmessage",
		data :"test",
		success : function(data) {
			if(data.data=="true"){
				if(data.size>0){
					$("#messagesize").show();
					$("#messagesize").text(data.size);
				}else{
					$("#messagesize").hide();
				}
				$("#messagemenu").html(data.message);
			}
		}
	});
	setTimeout(getmessage,1000);
}
</script>
<rapid:block name="title"> 
   <title>Home</title>
</rapid:block> 
</head>

<body>
   <%  boolean isbegin = true ;%>
	<!-- Header Wrapper -->
	<div id="mws-header" class="clearfix">
    
    	<!-- Logo Wrapper -->
    	<div id="mws-logo-container">
        	<div id="mws-logo-wrap">
            	<img src="/GuangJX/static/images/mws-logo.png" alt="mws admin" />
			</div>
        </div>
        
        <!-- User Area Wrapper -->
        <div id="mws-user-tools" class="clearfix">
        
        	<!-- User Notifications -->
        	<div id="mws-user-notif" class="mws-dropdown-menu">
            	<a href="#" class="mws-i-24 i-alert-2 mws-dropdown-trigger">Notifications</a>
                <span class="mws-dropdown-notif" style="display:none;" id="wariningsize"></span>
                <div class="mws-dropdown-box">
                	<div class="mws-dropdown-content">
                        <ul class="mws-notifications" id="warningmenu">
                        </ul>
                        <div class="mws-dropdown-viewall">
	                        <a href="#">View All Notifications</a>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- User Messages -->
            <div id="mws-user-message" class="mws-dropdown-menu">
            	<a href="/GuangJX/static/#" class="mws-i-24 i-message mws-dropdown-trigger">Messages</a>
                <span class="mws-dropdown-notif" style="display:none;" id="messagesize"></span>
                <div class="mws-dropdown-box">
                	<div class="mws-dropdown-content">
                        <ul class="mws-messages" id="messagemenu">
                        </ul>
                        <div class="mws-dropdown-viewall">
	                        <a href="#">View All Messages</a>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- User Functions -->
            <div id="mws-user-info" class="mws-inset">
            	<div id="mws-user-photo">
                	<img src="/GuangJX/static/example/profile.jpg" alt="User Photo" />
                </div>
                <div id="mws-user-functions">
                    <div id="mws-username">
                        Hello,test
                    </div>
                    <ul>
                    	<li><a href="#">个人信息</a></li>
                        <li><a href="#">更改密码</a></li>
                        <li><a href="/GuangJX/login/index">注销</a></li>
                    </ul>
                </div>
            </div>
            <!-- End User Functions -->
            
        </div>
    </div>
    
    <!-- Main Wrapper -->
    <div id="mws-wrapper">
    	<!-- Necessary markup, do not remove -->
		<div id="mws-sidebar-stitch"></div>
		<div id="mws-sidebar-bg"></div>
        
        <!-- Sidebar Wrapper -->
        <div id="mws-sidebar">
            <!-- Main Navigation -->
            <div id="mws-navigation">
                <ul>
	 			 <c:forEach var="item" items="${list}">
		            <c:if test="${item.ISLEAF == 0}">
		             <!--如果不是叶子节点 -->
						<c:if test="${item.PARRENTCODE == 0}">
					        <c:set var="flag" value="<%= isbegin %>" scope="page"/>
					        <c:if test="${flag == false}">
						         </ul></li>
       				             <% isbegin = true ;%>
					        </c:if>
					        <li class="active"><a href="${item.URL}" class="mws-i-24 ${item.IMAGE}">${item.NAME}</a></li>
						</c:if>
						<c:if test="${item.PARRENTCODE != 0}">
					        <c:set var="flag" value="<%= isbegin %>" scope="page"/>
					        <c:if test="${flag == true}">
								 <li><a href="#" class="mws-i-24 ${item.IMAGE}">${item.NAME}</a><ul>
					        </c:if>
					        <c:if test="${flag == false}">
							      </ul></li>
							      <li><a href="#" class="mws-i-24 ${item.IMAGE}">${item.NAME}</a><ul>
								  <% isbegin = true ;%>
					        </c:if>
						</c:if>
                    </c:if>
                    <c:if test="${item.ISLEAF == 1}">
                    <!--如果是叶子节点 -->
                         <li><a href="${item.URL}">${item.NAME}</a></li>
                        <% isbegin = false ;%>
                    </c:if>
				 </c:forEach>
               </ul>
            </div>
            <!-- End Navigation -->
            
        </div>
        
        
        <!-- Container Wrapper -->
        <div id="mws-container" class="clearfix">
        
        	<!-- Main Container -->
            <div class="container">
                 <rapid:block name="content">
                 </rapid:block>  
            </div>
            <!-- End Main Container -->
            
            <!-- Footer -->
            <div id="mws-footer">
                 <rapid:block name="footer">
                 </rapid:block>  
            </div>
            <!-- End Footer -->
        </div>
        <!-- End Container Wrapper -->
    </div>
    <!-- End Main Wrapper -->
</body>
</html>
