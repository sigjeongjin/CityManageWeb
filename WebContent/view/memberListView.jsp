<%@page import="java.util.ArrayList"%>
<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>도시가스관리</title>
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
		<th>관리지역</th>
		<th>관리지역</th>
	</tr>
	
<%-- 		<td>1</td>
		<td><%=request.getAttribute("memberList") %></td>
		<td><%=request.getAttribute("memberName") %></td>
		<td><%=request.getAttribute("memberId") %></td>
		<td><%=request.getAttribute("memberEmail") %></td>
		<td><%=request.getAttribute("memberPhone") %></td>
		<td><%=request.getAttribute("cityGeocode") %></td>
		<td><%=request.getAttribute("stateGeocode") %></td> --%>
		<% for(int i=0;i<30;i++) {
		%>
		<tr>
		<td>${memberList.memberAuthorization}</td>
		<td>${memberList.memberName}</td>
		<td>${memberList.memberId}</td>
		<td>${memberList.memberEmail}</td>
		<td>${memberList.memberPhone}</td>
		<td>${memberList.cityGeocode}</td>
		<td>${memberList.stateGeocode}</td>
	</tr>
	<%}%>
</table>
</body>
</html>