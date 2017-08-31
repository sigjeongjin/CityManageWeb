<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<link rel="stylesheet" type="text/css" href="../../css/textFormSmall.css">
</head>
<body>

<h2>센서 등록</h2>

<div>

<form action="sensorRegister.do" method="post">
<c:forEach var="sensorInfo" items="${sensorInfoList}" varStatus="status">
	<table>
	<tr>
		<td><label><b>관리ID</b></label></td>
			<td><input type="text" id="manageId" name="manageId" value="${sensorInfo.manageId}"></td>
	</tr>	
	<tr>
		<td><label><b>센서ID</b></label></td>
		<td>	<input type="text" id="sensorId" name="sensorId" value="${sensorInfo.sensorId}" ></td>
	</tr>	
	<tr>
 		<td><label><b>센서종류:</b></label></td>
		<td><select id="sensorType" name="sensorType">
			 <option>센서선택</option>
	    </select></td>
	</tr>
	<tr>
		<td><label><b>PUSH알림 기준값</b></label></td>
		<td>	<input type="text" id="sensorNoticeStandard" name="sensorNoticeStandard" value="${sensorInfo.sensorNoticeStandard}" required></td>
	</tr>	
		
		</table>
</c:forEach>
			<div class="btncenter">
    			<button type="submit" value="register">등록</button>
			</div>		
</form>
</div>

<input type="hidden" id="arraySensor" name="arraySensor" value="${sensorTypes}">
<input type="hidden" id="arraySensorThis" name="arraySensorThis" value="${sensorTypeTemp}">
</body>


<script src="../../js/jquery-1.11.0.min.js"></script>

<script type="text/javascript">

var sensorTypes = $('#arraySensor').val();
console.log(sensorTypes);

var array = $('#arraySensorThis').val();
	array =  array.substring(0, array.length-2);
var sensorTypeThis = array.split(', ');
console.log("sensorTypeThis : " + sensorTypeThis);

if (sensorTypes != null && sensorTypes != "") {
	
	var sensorArray = sensorTypes.split(', ');
	
	console.log(sensorArray);
	
	var options = $('#sensorType');
	
	options.html("<option>센서선택</option>");
	
	for(i=0;i<sensorArray.length;i++){
		// 쓰레기통 관리
		if (sensorArray[i]==="g") {
			options.append("<option value=" + sensorArray[i] + ">" + "만적센서" +"</option>");
		}
		if (sensorArray[i]==="s") {
			options.append("<option value=" + sensorArray[i] + ">" + "악취센서" +"</option>");
		}
		if (sensorArray[i]==="fd") {
			options.append("<option value=" + sensorArray[i] + ">" + "불꽃감지센서" +"</option>");
		}
		if (sensorArray[i]==="lr") {
			options.append("<option value=" + sensorArray[i] + ">" + "짐금" +"</option>");
		}
		// 수질 관리
		if (sensorArray[i]==="wq") {
			options.append("<option value=" + sensorArray[i] + ">" + "수질센서" +"</option>");
		}
		if (sensorArray[i]==="wl") {
			options.append("<option value=" + sensorArray[i] + ">" + "수위센서" +"</option>");
		}
	}
	
	for(i=0;i<sensorTypeThis.length;i++){
		console.log(sensorTypeThis[i]);
		if (sensorTypeThis[i]==="wq") {
			$("option[value='wq']").remove();
		}
		if (sensorTypeThis[i]==="wl") {
			$("option[value='wl']").remove();
		}
	}
}




</script>
</html>