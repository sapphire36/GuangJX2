<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript"
	src="https://api.map.baidu.com/api?v=2.0&ak=IDvNBsejl9oqMbPF316iKsXR"></script>
<script type="text/javascript"
	src="https://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
<link rel="stylesheet"
	href="https://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
<rapid:override name="title">
	<title>地图</title>
</rapid:override>
<rapid:override name="content">
	<div class="panel panel-default">
		<div style="min-height: 400px; width: 100%; margin: 0px" id="map">
		</div>
	</div>
</rapid:override>
<script type="text/javascript">
	var markerArr = new Array();
	var i = 0;
	<c:forEach var="box" items="${lightboxlist}">
	{
		id = "${box.ID}", 
		name = "${box.NAME}",
		point = "${box.LOCATION}",
		unlockstatus = "${box.UNLOCKSTATUS}",
		doorstatus = "${box.DOORSTATUS}", 
		constructstatus = "${box.CONSTRUCTSTATUS}", 
		isonline = "${box.ISONLINE}";

		markerArr[i] = {
			id : id,
			name : name,
			location : point,
			unlockstatus : unlockstatus,
			doorstatus : doorstatus,
			constructstatus : constructstatus,
			isonline : isonline
		};
		i++;
	}
	</c:forEach>
	var imageOffset_x = -22;
	var customIcon_red = new BMap.Icon("https://api.map.baidu.com/lbsapi/createmap/images/icon.png", new BMap.Size(22, 26), {
        imageOffset: new BMap.Size(imageOffset_x*2, -20) // 设置图片偏移
    });
	var customIcon_green = new BMap.Icon("https://api.map.baidu.com/lbsapi/createmap/images/icon.png", new BMap.Size(22, 26), {
        imageOffset: new BMap.Size(0, -20) // 设置图片偏移
    });
	var customIcon_yellow = new BMap.Icon("https://api.map.baidu.com/lbsapi/createmap/images/icon.png", new BMap.Size(22, 26), {
        imageOffset: new BMap.Size(imageOffset_x*3, -20) // 设置图片偏移
    });
	
	function addMarker(map,points) {
	    //循环建立标注点
	    for(var i=0, pointsLen = points.length; i<pointsLen; i++) {
	    	try{ 
		    	var customIcon; 
				var p0 = points[i].location.split(",")[0]; //
				var p1 = points[i].location.split(",")[1]; //按照原数组的point格式将地图点坐标的经纬度分别提出来
		        var point = new BMap.Point(p0, p1); //将标注点转化成地图上的点
		        if(points[i].constructstatus==1){
		        	customIcon=customIcon_green;
		        }else{
		        	customIcon=customIcon_red;
		        }
		        var marker = new BMap.Marker(point); //将点转化成标注点
		        //var marker = new BMap.Marker(point,{icon:customIcon}); //将点转化成标注点
		        map.addOverlay(marker);  //将标注点添加到地图上
		        //添加监听事件
		        (function() {
		            var thePoint = points[i];
		            marker.addEventListener("click",
		            //显示信息的方法
		                function() {
		                showInfo(this,thePoint);
		            });
		         })();  
	    	}catch(error){ 
	    		console.log(error)
	    	}
	    }
	}
	
	function showInfo(thisMarker,point) {
	    //获取点的信息
	    var sContent = 
	    '<ul style="margin:0 0 5px 0;padding:0.2em 0">'  
	    +'<li style="line-height: 50px;font-size: 15px;">'  
	    +'<span style="width: 70px;display: inline-block;">箱体名称：</span>' + point.id + '</li>'  
	    +'<li style="line-height: 26px;font-size: 15px;">'  
	    +'<span style="width: 50px;display: inline-block;">IMEI编号：</span>' + point.name + '</li>'  
	    +'<span style="width: 50px;display: inline-block;">IMEI编号：</span>' + point.name + '</li>'  
	    +'<span style="width: 50px;display: inline-block;">IMEI编号：</span>' + point.name + '</li>'  
	    +'<li style="line-height: 26px;font-size: 15px;"><span style="width: 50px;display: inline-block;">查看：</span><a href="'+point.name+'">详情</a></li>'  
	    +'</ul>';
	    var infoWindow = new BMap.InfoWindow(sContent); //创建信息窗口对象
	    thisMarker.openInfoWindow(infoWindow); //图片加载完后重绘infoWindow
	}
	
	function map_init() {
		var map = new BMap.Map("map"); // 创建Map实例
		var mapStyle={  style : "midnight" }  
		map.setMapStyle(mapStyle);
		var point = new BMap.Point(108.953196, 34.229055); //地图中心点，西安市
		map.centerAndZoom(point, 14); // 初始化地图,设置中心点坐标和地图级别。
		map.enableScrollWheelZoom(true); //启用滚轮放大缩小
        //添加缩略图控件
        map.addControl(new BMap.OverviewMapControl({isOpen:false,anchor:BMAP_ANCHOR_BOTTOM_RIGHT}));
        //添加缩放平移控件
        map.addControl(new BMap.NavigationControl());
        //添加比例尺控件
        map.addControl(new BMap.ScaleControl());

		
		// 添加带有定位的导航控件
		var navigationControl = new BMap.NavigationControl({
			// 靠左上角位置
			anchor : BMAP_ANCHOR_TOP_LEFT,
			// LARGE类型
			type : BMAP_NAVIGATION_CONTROL_LARGE,
			// 启用显示定位
			enableGeolocation : true
		});
		map.addControl(navigationControl);
		// 添加定位控件
		var geolocationControl = new BMap.GeolocationControl();
		geolocationControl.addEventListener("locationSuccess", function(e) {
			// 定位成功事件
			var address = '';
			address += e.addressComponent.province;
			address += e.addressComponent.city;
			address += e.addressComponent.district;
			address += e.addressComponent.street;
			address += e.addressComponent.streetNumber;
			alert("当前定位地址为：" + address);
		});
		geolocationControl.addEventListener("locationError", function(e) {
			// 定位失败事件
			alert(e.message);
		});
		map.addControl(geolocationControl);
		addMarker(map,markerArr);
	}
	//异步调用百度js
	function map_load() {
		var load = document.createElement("script");
		load.src = "https://api.map.baidu.com/api?v=2.0&ak=IDvNBsejl9oqMbPF316iKsXR&callback=map_init";
		document.body.appendChild(load);
	}
	window.onload = map_load;
</script>
<%@ include file="../home/base.jsp"%>
