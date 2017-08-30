<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>관리코드 상세 정보</title>

<link rel="stylesheet" type="text/css" href="../../css/ChangeTextForm.css">
</head>
<body>
<jsp:include page="../header/menuHeader.jsp" flush="true"/>

<div id="modifyContainer">	
<form action="manageLocationUpdate.do" method="post">

	<table>
	<tr>
		<td colspan="2"><label><b>관리ID :</b></label></td>
		<td colspan="3"><input type="text" id="manageId" name="manageId" value="${manageInfo.manageId}" disabled="disabled" readonly="readonly"></td>
	</tr>
	<tr>
		<td colspan="2"><label><b>지역선택 :</b></label></td>
		<td colspan="1">
			<select id="cityCode" name="cityCode" onchange="javascript:selectEvent(this)">
				<option value="${manageInfo.cityCode}" selected disabled="disabled" >${manageInfo.cityName} </option>
				<c:forEach var="address" items="${addressCityList}" varStatus="status">
				<option value="${address.cityCode}">${address.cityName}</option>
				</c:forEach>
	    	</select></td>
	    	
	    <td colspan="1">
	    	 <select id="stateCode" name="stateCode">
			 <option value="${manageInfo.stateCode}" selected disabled="disabled">${manageInfo.stateName}</option>
	    	</select></td>
	</tr>	
	<tr>
		<td colspan="2"><label><b>좌표값 :</b></label></td>
		<td colspan="1"><input type="text" placeholder="위도" id="latitude" name="latitude" value="${manageInfo.latitude}" required disabled="disabled"></td>
		<td colspan="1"><input type="text" placeholder="경도" id="longitude" name="longitude" value="${manageInfo.longitude}" required disabled="disabled"></td>
	</tr>
	<tr>
		<td colspan="2"><label><b>비고 :</b></label></td>
		<td colspan="2">
		<textarea rows="4" cols="55" id="memo" name="memo" disabled="disabled">${manageInfo.memo}</textarea>
		</td>
	</tr>
<!-- *****관리 시스템 선택***** -->	
	<c:if test="${manageType == 'tm'}">
		<tr>
			<td colspan="2"><label><b>센서종류 :</b></label></td> 
			<td colspan="1"><input type="checkbox" id="sensorTypes" name="sensorTypes" value="g" disabled="disabled"><label><b>만적센서</b></label></td>
			<td colspan="1"><input type="checkbox" id="sensorTypes" name="sensorTypes" value="s" disabled="disabled"><label><b>악취센서</b></label></td>
		</tr>
		<tr>
			<td colspan="2"></td> 
			<td colspan="1"><input type="checkbox" id="sensorTypes" name="sensorTypes" value="fd" disabled="disabled"><label><b>불꽃감지센서</b></label></td>
			<td colspan="1"><input type="checkbox" id="sensorTypes" name="sensorTypes" value="l" disabled="disabled"><label><b>잠금</b></label></td>
		</tr>		
	</c:if>
	<c:if test="${manageType == 'wm'}">
		<tr>
			<td colspan="2"><label><b>센서종류 :</b></label></td> 
			<td colspan="1"><input type="checkbox" id="sensorTypes" name="sensorTypes" value="wq" disabled="disabled"><label><b>수질센서</b></label></td>
			<td colspan="1"><input type="checkbox" id="sensorTypes" name="sensorTypes" value="wl" disabled="disabled"><label><b>수위센서</b></label></td>
		</tr>	
	</c:if>
	<c:if test="${manageType == 'gm'}">
		<tr>
			<td colspan="2"><label><b>센서종류 :</b></label></td> 
			<td colspan="1"><input type="checkbox" id="sensorTypes" name="sensorTypes" value="sd" disabled="disabled"><label><b>충격감지센서</b></label></td>
			<td colspan="1"><input type="checkbox" id="sensorTypes" name="sensorTypes" value="gd" disabled="disabled"><label><b>압력농도센서</b></label></td>
		</tr>	
	</c:if>
	<c:if test="${manageType == 'sm'}">
		<tr>
			<td colspan="2"><label><b>센서종류 :</b></label></td> 
			<td colspan="1"><input type="checkbox" id="sensorTypes" name="sensorTypes" value="fd" disabled="disabled"><label><b>불꽃감지센서</b></label></td>
			<td colspan="1"><input type="checkbox" id="sensorTypes" name="sensorTypes" value="sd" disabled="disabled"><label><b>연기감지센서</b></label></td>
		</tr>	
	</c:if>
</table>

		<div id="managementToggleUpdate" class="managementToggleUpdate" style="display:block">
    		<button type="button" value="managementModify" onclick="managementModify()">수정</button>
    	</div>
    	
    	<div id="managementToggleModify" class="managementToggleModify" style="display:none">
    		<button type="submit" value="managementUpdate" onclick="managementUpdate()">변경</button>
    	</div>
</form>	
    	<div id="managementDelete" class="managementDelete">
	    	<form action="manageLocationDelete.do" method="post">
	    		<button type="button" value="managementUpdate">삭제</button>
	    	</form>
    	</div>
    	    	<div id="sensorUpdate" class="sensorUpdate">
	    	<form action="/manageLocationDelete.do" method="post">
	    		<button type="button" value="managementUpdate">삭제</button>
	    	</form>
    	</div>
</div>	
    
<input type="hidden" id="systemSensor" name="systemSensor" value="${manageInfo.sensorTypes}">
</body>

<script src="../../js/jquery-1.11.0.min.js"></script>
<script type="text/javascript">

function managementModify() {
	$('#memo').attr("disabled", false); 
	$('input').attr("disabled", false);
    var x = document.getElementById('managementToggleUpdate');
    var y = document.getElementById('managementToggleModify');
    if (x.style.display === 'none') {
    	x.style.display = 'block';
    	y.style.display = 'none';
    } else {
        x.style.display = 'none';
        y.style.display = 'block';
    }
} 

function managementUpdate() {
	var x = document.getElementById('managementToggleModify');
    var y = document.getElementById('managementToggleUpdate');
    if (y.style.display === 'none') {
        y.style.display = 'block';
        x.style.display = 'none';

    } else {
        y.style.display = 'none';
        x.style.display = 'block';
    }
}

</script>

<script>
<script type="text/javascript">
var selectSensorTypes = $('#systemSensor').val();
console.log(sensorTypes);

if (selectSensorTypes != null && selectSensorTypes != "") {	
	var sensorArray = selectSensorTypes.split(', ');
	
	console.log(sensorArray);
	
	for(i=0;i<sensorArray.length;i++){
		console.log(sensorArray[i]);
		$("input:checkbox[value=" + sensorArray[i] + "]").attr("checked", true);
	}
}
</script>

<script type="text/javascript">

function selectEvent() {
    var id = $("#cityCode").val();
    var query = {cityCode:id};         
    $.ajax({
          type : "GET",
          dataType:"json",
          url : "/managementareaState.ajax",
          data : query,
          error : function(){
             $("#confirmText").html(data);
          },
          success : function(data){
		  var options = $('#stateCode');
						
		  //options.html("<option>시/군/구</option>");
		  options.html("<option value = ${wmManageInfo.stateCode} selected>${stateName}</option>");
			
		  for (var i = 0; i < data.state.length; i++) {

				options.append("<option value=" + data.state[i].stateCode + ">" 
						+ data.state[i].stateName +"</option>");
			}
         }   
    });
}
</script>

</html>