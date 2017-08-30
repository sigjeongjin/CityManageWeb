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
<jsp:include page="../header/menuHeader.jsp" flush="true"/>
<h2>PUSH이력 리스트</h2>
<div class="container">

<form action="memberSearch.do" method="post">
	<select id="memberSelect" name="memberSelect">
		<option value="all">전체</option>
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
		<th>지역</th>
		<th>PUSH내용</th>
		<th>PUSH발송 시간</th>
		<th>센서 설치 날짜</th>
		<th>좌표값</th>
	</tr>
	
<c:if test="${pushHistoryListPage.hasNoPushHistorys()}">
	<tr>
		<td colspan="7">PUSH 조회 건이 없습니다.</td>
	</tr>
</c:if>

<c:forEach var="pushHistory" items="${pushHistoryListPage.content}" varStatus="status">
	<tr id="pushHistory" class="memberList">
		<td>${(status.index + 1) + (pushHistoryListPage.currentPage -1) * 10}</td>
		<td id="1">${pushHistory.manageId}</td>
		<td id="2">${pushHistory.locationName}</td>
		<td id="3">${pushHistory.pushContents}</td>
		<td id="4">${pushHistory.pushSendTime}</td>	
		<td id="5">${pushHistory.installationDateTime}</td>
		<td id="6">${pushHistory.location}</td>
	</tr>
</c:forEach>
 <c:if test="${pushHistoryListPage.hasPushHistorys()}">
	<tr>
		<td colspan="9">
			<c:if test="${pushHistoryListPage.startPage > 5}">
			<a href="pushHistoryList.do?pageNo=${pushHistoryListPage.startPage - 5}">[이전]</a>
			</c:if>
			<c:forEach var="pNo" 
					   begin="${pushHistoryListPage.startPage}" 
					   end="${pushHistoryListPage.endPage}">
			<a href="pushHistoryList.do?pageNo=${pNo}">[${pNo}]</a>
			</c:forEach>
			<c:if test="${pushHistoryListPage.endPage < pushHistoryListPage.totalPages}">
			<a href="pushHistoryList.do?pageNo=${pushHistoryListPage.startPage + 5}">[다음]</a>
			</c:if>
		</td>
	</tr>
</c:if>
</table>
</div>

<form id="hiddenForm" action="pushHistoryList.do" method="post">
<input type="hidden" id="memberId" name="memberId">
</form>

</body>
<script src="../../js/jquery-1.11.0.min.js"></script>
<script type="text/javascript">
$(doucument).ready(function(){
    $("tr.memberList").click(function(){
     	$('#memberId').attr('value',$(this).find("td").eq(3).html());
     		console.log(memberId);
     	$("#hiddenForm").submit();
    });
});
</script>
</html>