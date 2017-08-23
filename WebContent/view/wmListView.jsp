<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>수질관리 리스트</title>
<link rel="stylesheet" type="text/css" href="../css/tableForm.css">
</head>
<body>
<jsp:include page="menuHeader.jsp" flush="true"/>
<div class="allContainer">
<h2>수질관리 리스트</h2>
<div class="manageContainer">
<form action="managementarea.do" method="post">
<input type="hidden" id="manageType" name="manageType" value='${manageType}'>
<button type = submit id="wm" name="wm" value="wm">관리지역등록</button>
</form>
</div>

<div class="container">

<form action="wmListSearch.do" method="post">
	<select id="memberSelect" name="memberSelect">
		<option value="allMember">전체</option>
		<option value="member_authorization">구분</option>
		<option value="member_name">이름</option>
		<option value="member_id">아이디</option>
		<option value="member_email">이메일</option>
		<option value="member_phone">전화번호</option>
		<option value="city_code">시/도</option>
		<option value="state_code">시/군/구</option>
    </select>
    <input type="text" id="memberInput" placeholder="Search for names.." name="memberInput">  
<button type = submit value="선택">선택</button>
</form>

<table>
	<tr>
		<th>번호</th>
		<th>관리ID</th>
		<th colspan="2">지역</th>
		<th>수위정보</th>
		<th>수질정보</th>
		<th>센서 동작상태</th>
		<th colspan="2">좌표값</th>
		<th>비고</th>
		<th>센서추가</th>
	</tr>
	
</table>
</div>

</div>
</body>
</html>