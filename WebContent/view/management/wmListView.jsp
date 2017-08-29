<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>수질관리 리스트</title>
<link rel="stylesheet" type="text/css" href="../../css/tableForm.css">
</head>
<body>
<jsp:include page="../header/menuHeader.jsp" flush="true"/>
<h2>수질관리 리스트</h2>
<div class="manageContainer">
<form action="managementarea.do" method="post">
<input type="hidden" id="manageType" name="manageType" value='${manageType}'>
<button type = submit id="wm" name="wm" value="wm">관리지역등록</button>
</form>
</div>

<div class="container">

<form action="wmListSearch.do" method="post">
	<select id="lcationManagementSelect" name="lcationManagementSelect">
		<option value="alllcationManagement">전체</option>
		<option value="lcationManagement_authorization">구분</option>
		<option value="lcationManagement_name">이름</option>
		<option value="lcationManagement_id">아이디</option>
		<option value="lcationManagement_email">이메일</option>
		<option value="lcationManagement_phone">전화번호</option>
		<option value="city_code">시/도</option>
		<option value="state_code">시/군/구</option>
    </select>
    <input type="text" id="lcationManagementInput" placeholder="Search for names.." name="lcationManagementInput">  
<button type = submit value="선택">선택</button>
</form>

<table>
	<tr>
		<th>번호</th>
		<th>관리ID</th>
		<th colspan="2">지역정보</th>
		<th>수위정보</th>
		<th>수질정보</th>
		<th>센서 동작상태</th>
		<th colspan="2">좌표값</th>
		<th>비고</th>
		<th>센서추가</th>
	</tr>	
<c:if test="${WmListPage.hasNoSensors()}">
	<tr>
		<td colspan="11">검색 결과가 없습니다.</td>
	</tr>
</c:if>

<c:forEach var="wmManagementInfo" items="${WmListPage.content}" varStatus="status">
	<tr id="wmSensorList" class="wmSensorList">
		<td>${(status.index + 1) + (WmListPage.currentPage -1) * 10}</td>
		<td id="tdManageId">${wmManagementInfo.manageId}</td>
		<td colspan="2">${wmManagementInfo.locationName}</td>
		<td>${wmManagementInfo.waterQuality}</td>
		<td>${wmManagementInfo.waterLevel}</td>
		<td>${wmManagementInfo.operationStatus}</td>
		<td colspan="2" >${wmManagementInfo.coordinate}</td>
		<td>${wmManagementInfo.memo}</td>
		<td onclick="event.cancelBubble=true"><p id="sensorRegister">센서 추가</p></td>
	</tr>
</c:forEach>
 <c:if test="${WmListPage.hasSensors()}">
	<tr>
		<td colspan="11">
			<c:if test="${WmListPage.startPage > 5}">
			<a href="wmList.do?pageNo=${WmListPage.startPage - 5}">[이전]</a>
			</c:if>
			<c:forEach var="pNo" 
					   begin="${WmListPage.startPage}" 
					   end="${WmListPage.endPage}">
			<a href="wmList.do?pageNo=${pNo}">[${pNo}]</a>
			</c:forEach>
			<c:if test="${WmListPage.endPage < WmListPage.totalPages}">
			<a href="wmList.do?pageNo=${WmListPage.startPage + 5}">[다음]</a>
			</c:if>
		</td>
	</tr>
</c:if>
</table>
</div>

<form id="hiddenForm" action="wmInfo.do" method="post">
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
$(wmSensorList).ready(function(){
    $("tr.wmSensorList").click(function(){
     	$('#manageId').attr('value',$(this).find("td").eq(1).html());
     	$('#cityName').attr('value',$(this).find("td").eq(2).html());
     	$('#stateName').attr('value',$(this).find("td").eq(3).html());	
     	$("#hiddenForm").submit();
    });
});

$(wmSensorList).ready(function(){
    $("p").click(function(){
		var index =  $("p").index(this);
		var sensorManageId = $("tr.wmSensorList").eq(index).find("td").eq(0).next().html();
		$('#sensorManageId').attr('value', sensorManageId);
		$("#hiddenFormSensor").submit();
    });
});
</script>
</html>