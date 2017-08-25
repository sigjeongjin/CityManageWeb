<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>센서 등록</title>

<link rel="stylesheet" type="text/css" href="../../css/textFormSmall.css">
</head>
<body>
<jsp:include page="../header/menuHeader.jsp" flush="true"/>
<h2>센서 등록</h2>
<form action="register.do" method="post">

	<table>
	<tr>
		<td><label><b>관리ID</b></label></td>
			<td><input type="text" id="manageId" name="manageId" value="${param.manageId}" readonly="readonly"></td>
	</tr>	
	<tr>
		<td><label><b>센서ID</b></label></td>
		<td>	<input type="text" id="sensorId" name="sensorId" value="${param.sensorId}" readonly="readonly"></td>
	</tr>	
	<tr>
		<td><label><b>센서종류:</b></label></td>
		<td><select id="sensorType" name="sensorType">
			 <option>시/군/구</option>
	    </select></td>
	</tr>
	<tr>
		<td><label><b>PUSH알림 기준값</b></label></td>
		<td>	<input type="text" id="sensor_notice_standard" name="sensor_notice_standard" value="${param.sensor_notice_standard}" required></td>
	</tr>	
		
		</table>
			<div class="btncenter">
    			<button type="submit" value="register">등록</button>
			</div>
			
</form>
</html>