<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=IDvNBsejl9oqMbPF316iKsXR"></script>
<script type="text/javascript"
	src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
<link rel="stylesheet"
	href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
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
	<c:forEach var="box" items="${llist}">
	{
		id = "${box.ID}", 
		name = "${box.NAME}",
		point = "${box.LOCATION}",
		lockstatus = "${box.LOCKSTATUS}", 
		unlockstatus = "${box.UNLOCKSTATUS}",
		doorstatus = "${box.DOORSTATUS}", 
		vol = "${box.VOLTAGE}",
		temp = "${box.TEMPERATURE}";

		markerArr[i] = {
			id : id,
			name : name,
			point : point,
			lockstatus : lockstatus,
			unlockstatus : unlockstatus,
			doorstatus : doorstatus,
			vol : vol,
			temp : temp
		};
		i++;
	}
	</c:forEach>
	
    
	function map_init() {
		var map = new BMap.Map("map"); // 创建Map实例
		var point = new BMap.Point(108.953196, 34.229055); //地图中心点，西安市
		map.centerAndZoom(point, 14); // 初始化地图,设置中心点坐标和地图级别。
		map.enableScrollWheelZoom(true); //启用滚轮放大缩小
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

		var point = new Array(); //存放标注点经纬信息的数组
		var marker = new Array(); //存放标注点对象的数组
		var info = new Array(); //存放提示信息窗口对象的数组
		var searchInfoWindow = new Array();//存放信息窗口对象的数组
		
		var imageOffset_x = -22;
		var customIcon_red = new BMap.Icon("http://api.map.baidu.com/lbsapi/createmap/images/icon.png", new BMap.Size(22, 26), {
            imageOffset: new BMap.Size(imageOffset_x*2, -20) // 设置图片偏移
        });
		var customIcon_green = new BMap.Icon("http://api.map.baidu.com/lbsapi/createmap/images/icon.png", new BMap.Size(22, 26), {
            imageOffset: new BMap.Size(0, -20) // 设置图片偏移
        });
		var customIcon_yellow = new BMap.Icon("http://api.map.baidu.com/lbsapi/createmap/images/icon.png", new BMap.Size(22, 26), {
            imageOffset: new BMap.Size(imageOffset_x*3, -20) // 设置图片偏移
        });
		var customIcon;
		for (var i = 0; i < markerArr.length; i++) {
			var p0 = markerArr[i].point.split(",")[0]; //
			var p1 = markerArr[i].point.split(",")[1]; //按照原数组的point格式将地图点坐标的经纬度分别提出来
			point[i] = new window.BMap.Point(p0, p1); //循环生成新的地图点
			//if(DOORSTATUS==0){红色 }else if(LOCKSTATUS==DOORSTATUS && UNLOCKSTATUS==0){绿色}else{黄色}
			customIcon = customIcon_yellow;
			if(markerArr[i].doorstatus==0){
				customIcon = customIcon_red;
			}else if(markerArr[i].lockstatus==markerArr[i].doorstatus && markerArr[i].unlockstatus==0){
				customIcon = customIcon_green;
			}
			marker[i] = new window.BMap.Marker(point[i], {icon:customIcon}); //按照地图点坐标生成标记
			map.addOverlay(marker[i]);
			
			if(markerArr[i].lockstatus==markerArr[i].unlockstatus){
				var label = new window.BMap.Label("锁异常", {offset : new window.BMap.Size(20, -10)});
			    label.setStyle({
				    maxWidth: "none"
				});
			}else if(markerArr[i].lockstatus==0){
				var label = new window.BMap.Label("锁开中", {offset : new window.BMap.Size(20, -10)});
				 label.setStyle({
					    maxWidth: "none"
					});
			}else{
				var label = new window.BMap.Label("锁已关", {offset : new window.BMap.Size(20, -10)});
			    label.setStyle({
				    maxWidth: "none"
				});
			}
			marker[i].setLabel(label);
			//显示marker的title，marker多的话可以注释掉
// 			var label = new window.BMap.Label(markerArr[i].status1, {offset : new window.BMap.Size(20, -10)});
// 			label.setStyle({
// 			    maxWidth: "none"
// 			});
// 			marker[i].setLabel(label);
			// 创建信息窗口对象
			info[i] = "<p style=’font-size:12px;lineheight:1.8em;’>" + "锁编号："
					+ markerArr[i].id + "</br>锁名称：" + markerArr[i].name
					+ "</br>锁状态：" + markerArr[i].lockstatus + "</br>电压："
					+ markerArr[i].vol + "</br>温度：" + markerArr[i].temp
					+ "</br></p>";

			//创建信息窗口对象                       
			searchInfoWindow[i] = new BMapLib.SearchInfoWindow(map, info[i], {
				title : "箱柜信息", //标题
				width : 290, //宽度
				height : 158, //高度
				panel : "panel", //检索结果面板
				enableAutoPan : true, //自动平移
			});
			//添加点击事件
			marker[i].addEventListener("click", (function(k) {
				// js 闭包
				return function() {
					//将被点击marker置为中心
					//map.centerAndZoom(point[k], 18);
					//在marker上打开检索信息窗口
					searchInfoWindow[k].open(marker[k]);
				}
			})(i));
		}
	}
	//异步调用百度js
	function map_load() {
		var load = document.createElement("script");
		load.src = "http://api.map.baidu.com/api?v=2.0&ak=IDvNBsejl9oqMbPF316iKsXR&callback=map_init";
		document.body.appendChild(load);
	}
	window.onload = map_load;
</script>
<%@ include file="../home/base.jsp"%>
