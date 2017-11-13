<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>회원 리스트</title>
<link rel="stylesheet" type="text/css" href="../../css/tableForm.css">
</head>
<body>

<h2>회원 리스트</h2>
<div class="container">

<form action="memberList.do" method="post">
	<select id="selectBox" name="selectBox">
		<option value="all">전체</option>
		<option value="member_authorization">구분</option>
		<option value="member_name">이름</option>
		<option value="member_id">아이디</option>
		<option value="member_email">이메일</option>
		<option value="member_phone">전화번호</option>
		<option value="city_code">시/도</option>
		<option value="state_code">시/군/구</option>
    </select>
    <input type="text" id="searchText" placeholder="Search for names.." name="searchText">  
<button type ="submit" value="선택">선택</button>
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
		<td colspan="9">맴버가 없습니다.</td>
	</tr>
</c:if>

<c:forEach var="member" items="${memberListPage.content}" varStatus="status">
	<tr id="memberList" class="memberList">
		<td>${(status.index + 1) + (memberListPage.currentPage -1) * 10}</td>
		<td id="1">${member.memberAuthorization}</td>
		<td id="2">${member.memberName}</td>
		<td id="3">${member.memberId}</td>
		<td id="4">${member.memberEmail}</td>	
		<td id="5">${member.memberPhone}</td>
		<td id="6">${member.cityCode}</td>
		<td id="7">${member.stateCode}</td>
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
			<c:if test="${memberListPage.endPage < memberListPage.totalPages}">
			<a href="memberList.do?pageNo=${memberListPage.startPage + 5}">[다음]</a>
			</c:if>
		</td>
	</tr>
</c:if>
</table>
</div>

<form id="hiddenForm" action="memberUpdate.do" method="post">
<input type="hidden" id="memberId" name="memberId">
</form>

</body>
<script src="../../js/jquery-1.11.0.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    $("tr.memberList").click(function(){

     	memberId:$(this).find("td").eq(3).html();
     	$('#memberId').attr('value',$(this).find("td").eq(3).html());
     	$("#hiddenForm").submit();
    });
});
</script>
</html>