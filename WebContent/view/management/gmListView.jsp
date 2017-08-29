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
<jsp:include page="../header/menuHeader.jsp" flush="true"/>
<h2>수질관리 리스트</h2>
<div class="manageContainer">
<form action="managementarea.do" method="post">
<input type="hidden" id="manageType" name="manageType" value='${manageType}'>
<button type = submit id="gm" name="gm" value="gm">관리지역등록</button>
</form>
</div>

<div class="container">

<form action="gmListSearch.do" method="post">
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
		<th colspan="2">지역</th>
		<th>압력정보</th>
		<th>충격정보</th>
		<th>센서 동작상태</th>
		<th colspan="2">좌표값</th>
		<th>비고</th>
		<th>센서추가</th>
	</tr>	
<c:if test="${gmSensorListPage.hasNoSensors()}">
	<tr>
		<td colspan="11">검색 결과가 없습니다.</td>
	</tr>
</c:if>

<c:forEach var="lcationManagement" items="${gmSensorListPage.content}" varStatus="status">
	<tr id="gmSensorList" class="gmSensorList">
		<td>${(status.index + 1) + (gmSensorListPage.currentPage -1) * 10}</td>
		<td id="1">${lcationManagement.manageId}</td>
		<td id="2">${lcationManagement.cityCode}</td>
		<td id="3">${lcationManagement.stateCode}</td>
		<td id="4">등록X</td>
		<td id="5">등록X</td>	
		<td id="6">등록X</td>
		<td id="7">${lcationManagement.latitude}</td>
		<td id="8">${lcationManagement.longitude}</td>
		<td id="9">${lcationManagement.memo}</td>
		<td id="10" onclick="event.cancelBubble=true"><p id="sensorRegister">센서 추가</p></td>
	</tr>
</c:forEach>
 <c:if test="${gmSensorListPage.hasSensors()}">
	<tr>
		<td colspan="11">
			<c:if test="${gmSensorListPage.startPage > 5}">
			<a href="gmList.do?pageNo=${gmSensorListPage.startPage - 5}">[이전]</a>
			</c:if>
			<c:forEach var="pNo" 
					   begin="${gmSensorListPage.startPage}" 
					   end="${gmSensorListPage.endPage}">
			<a href="gmList.do?pageNo=${pNo}">[${pNo}]</a>
			</c:forEach>
			<c:if test="${gmSensorListPage.endPage < gmSensorListPage.totalPages}">
			<a href="gmList.do?pageNo=${gmSensorListPage.startPage + 5}">[다음]</a>
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
$(gmSensorList).ready(function(){
    $("tr.gmSensorList").click(function(){
     	$('#manageId').attr('value',$(this).find("td").eq(1).html());
     	$('#cityName').attr('value',$(this).find("td").eq(2).html());
     	$('#stateName').attr('value',$(this).find("td").eq(3).html());	
     	$("#hiddenForm").submit();
    });
});

$(gmSensorList).ready(function(){
    $("p").click(function(){
		var index =  $("p").index(this);
		var sensorManageId = $("tr.gmSensorList").eq(index).find("td").eq(0).next().html();
		$('#sensorManageId').attr('value', sensorManageId);
		$("#hiddenFormSensor").submit();
    });
});
</script>
</html>