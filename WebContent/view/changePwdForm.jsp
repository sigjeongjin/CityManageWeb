<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>change password</title>
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
    text-align: centet;
	}
</style>
</head>
<body>
	<jsp:include page="header.jsp" flush="true"/>
	
	<div id="content">
		<h2>change password</h2>
		
	<div class="article">
		<fieldset>
		<legend>change password:</legend>
			<form action="changePwd.do" method="post">

				현재 비밀번호:<br>
				<input type="password" name="curPwd"><br>
				<c:if test="${errors.curPwd}">아이디를 입력하세요.</c:if>
				<c:if test="${errors.badCurPwd}">아이디를 입력하세요.</c:if>
	
				변경 할 비밀번호:<br>
				<input type="password" name="newPwd"><br>
				<c:if test="${errors.newPwd}">새 비밀번호를 입력하세요.</c:if>
				<br>
				<input type="submit" value="비밀번호 변경">
			</form>
		
		</fieldset>
	</div>
	
	</div>
</body>
</html>