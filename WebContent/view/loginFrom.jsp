<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>login</title>
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
		<h2>로그인</h2>
		
	<div class="article">
		<fieldset>
		<legend>Login:</legend>
			<form action="login.do" method="post">

				아이디:<br>
				<input type="text" name="memberId" maxlength="20" value="${param.memberId}"><br>
				
				비밀번호:<br>
				<input type="password" name="memberPwd" maxlength="20"><br>

				<input type="submit" value="로그인">
			</form>
		
		</fieldset>
	</div>
	
	</div>
</body>
</html>