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

<form action="sensorRegister.do" method="post">
	<table>
	<tr>
		<td><label><b>관리ID</b></label></td>
			<td><input type="text" id="manageId" name="manageId" value="${sensorManageId}"></td>
	</tr>	
	<tr>
		<td><label><b>센서ID</b></label></td>
		<td>	<input type="text" id="sensorId" name="sensorId" value="${sensorId}" ></td>
	</tr>	
	<tr>
 		<td><label><b>센서종류:</b></label></td>
		<td><select id="sensorType" name="sensorType">
			 <option>센서선택</option>
	    </select></td>
	</tr>
	<tr>
		<td><label><b>PUSH알림 기준값</b></label></td>
		<td>	<input type="text" id="sensorNoticeStandard" name="sensorNoticeStandard" value="${param.sensorNoticeStandard}" required></td>
	</tr>	
		
		</table>
			<div class="btncenter">
    			<button type="submit" value="register">등록</button>
			</div>		
</form>

<input type="hidden" id="systemSensor" name="systemSensor" value="${manageSensorTypes}">
<input type="hidden" id="systemSensorThis" name="systemSensorThis" value="${sensorTypeList}">
</body>

<script src="../../js/jquery-1.11.0.min.js"></script>

<script type="text/javascript">


var array = $('#systemSensorThis').val();
var systemSensorThis = array.split(', ');
console.log("systemSensorThis : " + systemSensorThis);

var manageSensorTypes = $('#systemSensor').val();
console.log(manageSensorTypes);

if (manageSensorTypes != null && manageSensorTypes != "") {
	
	var manageSensorTypes = manageSensorTypes.split(', ');
	var options = $('#sensorType');
	
	options.html("<option>센서선택</option>");
	
	for(i=0;i<manageSensorTypes.length;i++){
		// 쓰레기통 관리
		if (manageSensorTypes[i]==="g") {
			options.append("<option value=" + manageSensorTypes[i] + ">" + "만적센서" +"</option>");
		}
		else if (manageSensorTypes[i]==="s") {
			options.append("<option value=" + manageSensorTypes[i] + ">" + "악취센서" +"</option>");
		}
		else if (manageSensorTypes[i]==="fd") {
			options.append("<option value=" + manageSensorTypes[i] + ">" + "불꽃감지센서" +"</option>");
		}
		else if (manageSensorTypes[i]==="l") {
			options.append("<option value=" + manageSensorTypes[i] + ">" + "짐금" +"</option>");
		}
		// 수질 관리
		else if (manageSensorTypes[i]==="wq") {
			options.append("<option value=" + manageSensorTypes[i] + ">" + "수질센서" +"</option>");
		}
		else if (manageSensorTypes[i]==="wl") {
			options.append("<option value=" + manageSensorTypes[i] + ">" + "수위센서" +"</option>");
		}	
		// 도시가스 관리
		else if (manageSensorTypes[i]==="sd") {
			options.append("<option value=" + manageSensorTypes[i] + ">" + "충격감지센서" +"</option>");
		}
		else if (manageSensorTypes[i]==="gd") {
			options.append("<option value=" + manageSensorTypes[i] + ">" + "압력농도센서" +"</option>");
		}
		// 금연구역 관리
		else if (manageSensorTypes[i]==="sm") {
			options.append("<option value=" + manageSensorTypes[i] + ">" + "연기감지센서" +"</option>");
		}
	}
	
	for(i=0;i<systemSensorThis.length;i++){
		console.log(systemSensorThis[i]);
		// 쓰레기통 관리
		if (systemSensorThis[i]==="g") {
			$("option[value='g']").remove();
		}
		else if (systemSensorThis[i]==="s") {
			$("option[value='s']").remove();
		}
		else if (systemSensorThis[i]==="fd") {
			$("option[value='fd']").remove();
		}
		else if (systemSensorThis[i]==="l") {
			$("option[value='d']").remove();
		}
		// 수질 관리
		else if (systemSensorThis[i]==="wq") {
			$("option[value='wq']").remove();
		}
		else if (systemSensorThis[i]==="wl") {
			$("option[value='wl']").remove();
		}
		// 도시가스 관리
		else if (systemSensorThis[i]==="sd") {
			$("option[value='sd']").remove();
		}
		else if (systemSensorThis[i]==="gd") {
			$("option[value='gd']").remove();
		}
		// 금연구역 관리
		else if (systemSensorThis[i]==="sm") {
			$("option[value='sm']").remove();
		}
	}
}
</script>
</html>