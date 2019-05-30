<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String lng=request.getParameter("lng");
	String lat=request.getParameter("lat");
	String type=request.getParameter("type");
	String username=request.getParameter("username");
	String mobile=request.getParameter("mobile");
	lng = lng==null?"":lng;
	lat = lat==null?"":lat;
	username=username==null?"":username;
	mobile=mobile==null?"":mobile;
	String t = String.valueOf((new Date()).getTime());
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">		
		<title>坐标采集</title>
		<script src="./libs/jquery-1.12.3.min.js"></script>
		<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.13&key=f61615c9a7e37c37058b284ec4d95130"></script> 
		<style type="text/css">
			html,body{
				height: 100%;
			}
			.input-card {
			  width: auto;
			  height: 2.4rem;
			  border-width: 0;
			  border-radius: 0.4rem;
			  box-shadow: 0 2px 6px 0 rgba(114, 124, 245, .5);
			  position: fixed;
			  bottom: 1rem;
			  right: 1rem;
			  display:flex; 
			  align-items: center;
			  justify-content: center;
			}
			.position{
				margin-left: 0.2rem;
				display: flex;
				flex-direction: column;
				margin-right: 0.2rem;
			}
			.position-container{
				display: flex;
				flex-wrap: wrap;
			}
			.btn{
				width:4rem;
				height:1.8rem;
				margin-left: 0.2rem;
			}
		</style>
	</head>
	<body style="margin:0px; padding:0px;overflow:hidden;">
		<div id="map" style="width:100%;height: 100%;">
		</div>
		<div class="input-card">
			<div class="position" id="div_position">
				<div class="position-container">
					<label>经度:</label>
					<label id="lbl_lng"></label>
				</div>
				<div class="position-container">
					<label>纬度:</label>
					<label id="lbl_lat"></label>
				</div>
			</div>
		    <button class="btn" id="btn_makesure" style="margin-right:1rem;">确定</button>
		    <button class="btn" id="btn_cancel" style="margin-right:1rem;">取消</button>
		</div>
	</body>
	<script>
		var lng="<%=lng%>";
		var lat="<%=lat%>";
		var type="<%=type%>";
		var username="<%=username%>";
		var mobile="<%=mobile%>";
	</script>
	<script src="./js/ddclient.js?t=<%=t %>"></script>
	<script src="./js/map.js?t=<%=t %>"></script>
</html>

