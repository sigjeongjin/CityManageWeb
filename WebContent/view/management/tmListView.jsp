<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>쓰레기통관리 리스트</title>
<link rel="stylesheet" type="text/css" href="../../css/tableForm.css">
</head>
<body>
<jsp:include page="../header/menuHeader.jsp" flush="true"/>
<div class="allContainer">
<h2>쓰레기통관리 리스트</h2>
<div class="manageContainer">
<form action="managementarea.do" method="post">
<input type="hidden" id="manageType" name="manageType" value='${manageType}'>
<button type = submit id="tm" name="tm" value="tm">관리지역등록</button>
</form>
</div>

<div class="container">

<form action="tmListSearch.do" method="post">
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
		<th>만적정보</th>
		<th>악취정보</th>
		<th>불꽃정보</th>
		<th>잠김정보</th>
		<th>센서 동작상태</th>
		<th colspan="2">좌표값</th>
		<th>비고</th>
		<th>센서추가</th>
	</tr>	
<c:if test="${tmSensorListPage.hasNoSensors()}">
	<tr>
		<td colspan="13">검색 결과가 없습니다.</td>
	</tr>
</c:if>

<c:forEach var="lcationManagement" items="${tmSensorListPage.content}" varStatus="status">
	<tr id="sensorList" class="sensorList">
		<td>${(status.index + 1) + (tmSensorListPage.currentPage -1) * 10}</td>
		<td id="1">${lcationManagement.manageId}</td>
		<td id="2">${lcationManagement.cityCode}</td>
		<td id="3">${lcationManagement.stateCode}</td>
		<td id="4">등록X</td>
		<td id="5">등록X</td>	
		<td id="6">등록X</td>
		<td id="7">등록X</td>	
		<td id="8">등록X</td>
		<td id="9">${lcationManagement.latitude}</td>
		<td id="10">${lcationManagement.longitude}</td>
		<td id="11">${lcationManagement.memo}</td>
		<td id="12">센서 추가</td>
	</tr>
</c:forEach>
 <c:if test="${tmSensorListPage.hasSensors()}">
	<tr>
		<td colspan="13">
			<c:if test="${tmSensorListPage.startPage > 5}">
			<a href="tmList.do?pageNo=${tmSensorListPage.startPage - 5}">[이전]</a>
			</c:if>
			<c:forEach var="pNo" 
					   begin="${tmSensorListPage.startPage}" 
					   end="${tmSensorListPage.endPage}">
			<a href="tmList.do?pageNo=${pNo}">[${pNo}]</a>
			</c:forEach>
			<c:if test="${tmSensorListPage.endPage < tmSensorListPage.totalPages}">
			<a href="tmList.do?pageNo=${tmSensorListPage.startPage + 5}">[다음]</a>
			</c:if>
		</td>
	</tr>
</c:if>
</table>
</div>

</div>
</body>
</html>