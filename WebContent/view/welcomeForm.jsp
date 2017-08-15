<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<style>
	body {
	    font-family: Verdana,sans-serif;
	    font-size: 0.9em;
	    text-align: center;
	}
	div#content {
	    margin: 5px;
	    padding: 10px;
	    background-color: #ff8080;  
	}
	div.article {
    margin: 10px;
    padding: 10px;
    background-color: white;
	}
	table {
    border-collapse: collapse;
    width: 100%;
	}	
	th {
		text-align: center;
		color: black;
		font-size: 20px;
	}	
	td {
		border: 2px solid #dddddd;
		padding: 10px;
		text-align: left;
	}
</style>
<title>welcome</title>
</head>
<body>
	<jsp:include page="header.jsp" flush="true"/>
	
	<div id="content">
	<h2>welcome</h2>
		
	<div class="article">
	
		<form action="index.jsp">
			
			안녕하세요?<br>
			${memberJoin.memberName}님.<br>
			가입축하드립니다.<br>
			정보확인 부탁드립니다.<br>
			<table>
			<tr>
				<th colspan = "2">회원 정보</th>
			</tr>
			<tr>
				<td>이름</td>
				<td>${memberJoin.memberName}</td>
			</tr>
			<tr>
				<td>아이디</td>
				<td>${memberJoin.memberId}</td>
			</tr>
			<tr>
				<td>휴대폰 번호</td>
				<td>${memberJoin.memberPhone}</td>
			</tr>
			</table>
			<input type="submit" value="완료">
	</form>
	</div>
	
	</div>
</body>
</html>