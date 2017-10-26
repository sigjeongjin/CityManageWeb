<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>도시가스관리 리스트</title>
<link rel="stylesheet" type="text/css" href="../../css/tableForm.css">
</head>
<body>
<h2>도시가스관리 리스트</h2>
<div class="manageContainer">
<form action="managementarea.do" method="post">
<input type="hidden" id="manageType" name="manageType" value='${manageType}'>
<button type="submit" id="gm" name="gm" value="gm">관리지역등록</button>
</form>
</div>

<div class="container">

<form action="gmList.do" method="post">
	<select id="selectBox" name="selectBox">
		<option value="all">전체</option>
		<option value="manageId">관리ID</option>
		<option value="locationName">지역정보</option>
		<option value="gasDensity">압력정보</option>
		<option value="shockDetection">충격정보</option>
		<option value="operationStatus">센서동작상태</option>
		<option value="memo">비고</option>
    </select>
    <input type="text" id="searchText" placeholder="Search for names.." name="searchText">  
<button type = submit value="조회">조회</button>
</form>

<table>
	<tr>
		<th>번호</th>
		<th>관리ID</th>
		<th colspan="2">지역</th>
		<th>압력정보</th>
		<th>충격정보</th>
		<th>센서 동작상태</th>
		<th colspan="2">좌표값</th>
		<th>비고</th>
		<th>센서추가</th>
	</tr>	
<c:if test="${GmListPage.hasNoSensors()}">
	<tr>
		<td colspan="11">검색 결과가 없습니다.</td>
	</tr>
</c:if>

<c:forEach var="gmManagementInfo" items="${GmListPage.content}" varStatus="status">
	<tr id="gmSensorList" class="gmSensorList">
		<td>${(status.index + 1) + (GmListPage.currentPage -1) * 10}</td>
		<td id="tdManageId">${gmManagementInfo.manageId}</td>
		<td colspan="2">${gmManagementInfo.locationName}</td>
		<td>${gmManagementInfo.gasDensity}</td>
		<td>${gmManagementInfo.shockDetection}</td>
		<td>${gmManagementInfo.operationStatus}</td>
		<td colspan="2" >${gmManagementInfo.coordinate}</td>
		<td>${gmManagementInfo.memo}</td>
		<td onclick="event.cancelBubble=true"><p id="sensorRegister">센서 추가</p></td>
	</tr>
</c:forEach>
 <c:if test="${GmListPage.hasSensors()}">
	<tr>
		<td colspan="11">
			<c:if test="${GmListPage.startPage > 5}">
			<a href="gmList.do?pageNo=${GmListPage.startPage - 5}">[이전]</a>
			</c:if>
			<c:forEach var="pNo" 
					   begin="${GmListPage.startPage}" 
					   end="${GmListPage.endPage}">
			<a href="gmList.do?pageNo=${pNo}">[${pNo}]</a>
			</c:forEach>
			<c:if test="${GmListPage.endPage < GmListPage.totalPages}">
			<a href="gmList.do?pageNo=${GmListPage.startPage + 5}">[다음]</a>
			</c:if>
		</td>
	</tr>
</c:if>
</table>
</div>

<form id="hiddenForm" action="gmInfo.do" method="post">
<input type="hidden" id="manageId" name="manageId">
<input type="hidden" id="cityName" name="cityName">
<input type="hidden" id="stateName" name="stateName">
</form>

<form id="hiddenFormSensor" action="sensorInfo.do" method="post">
<input type="hidden" id="sensorManageId" name="sensorManageId">
</form>

</body>
<script src="../../js/jquery-1.11.0.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    $("tr.gmSensorList").click(function(){
     	$('#manageId').attr('value',$(this).find("td").eq(1).html());
     	$("#hiddenForm").submit();
    });
    
    $("p").click(function(){
		var index =  $("p").index(this);
		var sensorManageId = $("tr.gmSensorList").eq(index).find("td").eq(0).next().html();
		$('#sensorManageId').attr('value', sensorManageId);
		$("#hiddenFormSensor").submit();
    });
});
</script>
</html>