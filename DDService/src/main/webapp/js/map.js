$(function() {
	var startCenter = [108, 34];
	if(lat && lng){
		startCenter[0]=parseFloat(lng);
		startCenter[1]=parseFloat(lat);
	}	
	var map = new AMap.Map('map', {
		zoom:18,//级别
		center: startCenter,//中心点坐标
		viewMode:'2D'//
	});
	
	//定义中心位置的点标
	var startMarker = new AMap.CircleMarker({
        center:startCenter,
        radius:10,//3D视图下，CircleMarker半径不要超过64px
        strokeColor:'white',
        strokeWeight:2,
        strokeOpacity:0.5,
        fillColor:'rgba(140,3,3,1)',
        fillOpacity:0.5,
        zIndex:10,
        bubble:true,
        cursor:'pointer',
        clickable: true
      })
	map.add(startMarker);
	
	map.plugin(["AMap.MapType"],function(){
	    //地图类型切换
	    var type= new AMap.MapType({
	    defaultType:0 //使用2D地图
	    });
	    map.addControl(type);
	});
	
	if(type=="showpoints"){
		$(".input-card").hide();
		showAllPoints(map);
		map.setZoomAndCenter(8,startCenter);
	}
	else{
		initTakePoint(map,startCenter);
	}
});

//初始化点展示页面
function showAllPoints(map){
	var item = {};
	if(username){
		item.creatername=username;
	}
	if(mobile){
		item.mobile=mobile;
	}
	$.ajax({ 
		url: "/api/poientity/findAll",
		type:"POST",
		dataType : 'json',
		contentType : "application/json",
		data:JSON.stringify(item), 
		success: function(r){
			console.log(r);
			if(r && r instanceof Array){
			      for(var i=0;i<r.length;i+=1){
			          var item = r[i];
			          var lng = parseFloat(item.lng);
			          var lat = parseFloat(item.lat);
			          if(!lng || !lat){
			        	  continue;
			          }
			          var center = [lng,lat];
			          var circleMarker = new AMap.CircleMarker({
			            center:center,
			            radius:10,
			            strokeColor:'white',
			            strokeWeight:2,
			            strokeOpacity:0.5,
			            fillColor:'rgba(0,0,255,1)',
			            fillOpacity:0.5,
			            zIndex:10,
			            bubble:true,
			            cursor:'pointer',
			            clickable: true
			          });
			          circleMarker.setMap(map)
			        }
			}
      }});
}

//初始化采点页面
function initTakePoint(map,startCenter){
	$("#lbl_lng").html(startCenter[0]);
	$("#lbl_lat").html(startCenter[1]);
	
	//定义中心位置的点标
	var centerMarker = new AMap.Marker({
	    position:startCenter,
	    title: '采点',
	    anchor:'bottom-center',
	    offset: new AMap.Pixel(0, 7),
	    icon:'./image/position.png?time='+(new Date()).getTime()
	});
	map.add(centerMarker);
	
	//监听地图滑动事件
	map.on("mapmove",function(e){
		var currentCenter = map.getCenter(); 
		changeCurrentPosition([currentCenter.lng,currentCenter.lat])
	});
	
//	//定位插件使用
//	map.plugin('AMap.Geolocation', function() {
//		var geolocation = new AMap.Geolocation({
//			enableHighAccuracy: true,
//			timeout: 10000,
//			buttonOffset: new AMap.Pixel(10, 20),
//			zoomToAccuracy: true,     
//			buttonPosition: 'LB'
//		});
//	
//		geolocation.getCurrentPosition();
//		map.addControl(geolocation);
//		AMap.event.addListener(geolocation, 'complete', onComplete);
//		AMap.event.addListener(geolocation, 'error', onError);
//	
//		function onComplete (data) {
//			console.log(data);
//		}
//	
//		function onError (data) {
//			console.log(data);
//		}
//	});
	
	//修改当前位置，掺入[经度,纬度]数组，
	function changeCurrentPosition(point){
		centerMarker.setPosition(point);
		console.log(point);
		$("#lbl_lng").html(point[0]);
		$("#lbl_lat").html(point[1]);
	}
	
	$("#btn_makesure").click(function(e){
		var currentCenter = map.getCenter(); 
		if (navigator.userAgent.toLowerCase().indexOf('dingtalk') > -1) {
			jumpToDetail(currentCenter);
		}
		else{
			alert(currentCenter);
		}
	});
	$("#btn_cancel").click(function(e){
		if (navigator.userAgent.toLowerCase().indexOf('dingtalk') > -1) {
			jumpToDetail({lng:startCenter[0],lat:startCenter[1]});
		}
		else{
			alert(startCenter);
		}
	});
}

//逆编码  输入坐标获取地址
function regeoCode(lnglat,callback) {
	if(!geocoder){
		geocoder = new AMap.Geocoder({
		});
	}
	geocoder.getAddress(lnglat, function(status, result) {
		if (status === 'complete'&&result.regeocode) {
			var address = result.regeocode.formattedAddress;
			if(callback){
				callback(address);
			}
		}else{alert(JSON.stringify(result))}
	});
}
