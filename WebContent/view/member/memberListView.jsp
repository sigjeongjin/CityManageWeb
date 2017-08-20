<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>회원 리스트</title>
</head>
<body>

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

<c:forEach var="member" items="${memberListPage.content}">
	<tr>
		<td>1</td>
		<td>${member.memberAuthorization}</td>
		<td>${member.memberName}</td>
		<td>${member.memberId}</td>
		<td>${member.memberEmail}</td>
		<td>${member.memberPhone}</td>
		<td>${member.cityGeocode}</td>
		<td>${member.stateGeocode}</td>
	</tr>
</c:forEach>
<%-- <c:if test="${memberListPage.hasMembers()}">
	<tr>
		<td colspan="9">
			<c:if test="${articlePage.startPage > 5}">
			<a href="list.do?pageNo=${articlePage.startPage - 5}">[이전]</a>
			</c:if>
			<c:forEach var="pNo" 
					   begin="${articlePage.startPage}" 
					   end="${articlePage.endPage}">
			<a href="list.do?pageNo=${pNo}">[${pNo}]</a>
			</c:forEach>
			<c:if test="${articlePage.endPage < articlePage.totalPages}">
			<a href="list.do?pageNo=${articlePage.startPage + 5}">[다음]</a>
			</c:if>
		</td>
	</tr>
</c:if> --%>
</table>
</body>
</html>