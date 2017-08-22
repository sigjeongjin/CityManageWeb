<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>회원 리스트</title>
<link rel="stylesheet" type="text/css" href="../css/tableForm.css">
</head>
<body>
<jsp:include page="menuHeader.jsp" flush="true"/>
<h2>회원 리스트</h2>
<div class="container">

<form action="memberSearch.do" method="post">
	<select id="memberSelect" name="memberSelect">
		<option value="all">전체</option>
		<option value="member_authorization">구분</option>
		<option value="member_name">이름</option>
		<option value="member_id">아이디</option>
		<option value="member_email">이메일</option>
		<option value="member_phone">전화번호</option>
		<option value="city_geocode">시/도</option>
		<option value="state_geocode">시/군/구</option>
    </select>
    <input type="text" id="memberInput" placeholder="Search for names.." name="memberInput">  
<button type = submit value="선택">선택</button>
</form> 
<table>
	<tr>
		<th>번호</th>
		<th>구분</th>
		<th>이름</th>
		<th>아이디</th>
		<th>이메일</th>
		<th>전화번호</th>
		<th>시/도</th>
		<th>시/군/구</th>
	</tr>
	
<c:if test="${memberListPage.hasNoMembers()}">
	<tr>
		<td colspan="8">맴버가 없습니다.</td>
	</tr>
</c:if>

<c:forEach var="member" items="${memberListPage.content}" varStatus="status">
	<tr>
		<td>${(status.index + 1) + (memberListPage.currentPage -1) * 10}</td>
		<td>${member.memberAuthorization}</td>
		<td>${member.memberName}</td>
		<td>${member.memberId}</td>
		<td>${member.memberEmail}</td>	
		<td>${member.memberPhone}</td>
		<td>${member.cityGeocode}</td>
		<td>${member.stateGeocode}</td>
	</tr>
</c:forEach>
 <c:if test="${memberListPage.hasMembers()}">
	<tr>
		<td colspan="9">
			<c:if test="${memberListPage.startPage > 5}">
			<a href="memberList.do?pageNo=${memberListPage.startPage - 5}">[이전]</a>
			</c:if>
			<c:forEach var="pNo" 
					   begin="${memberListPage.startPage}" 
					   end="${memberListPage.endPage}">
			<a href="memberList.do?pageNo=${pNo}">[${pNo}]</a>
			</c:forEach>
			<c:if test="${articlePage.endPage < articlePage.totalPages}">
			<a href="memberList.do?pageNo=${memberListPage.startPage + 5}">[다음]</a>
			</c:if>
		</td>
	</tr>
</c:if>
</table>
</div>
</body>
</html>