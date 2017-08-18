<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>IotSystem</title>
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
	<jsp:include page="/view/header.jsp" flush="true"/>
	
	<div id="content">
		<h2>index</h2>
		
		<u:isLogin>
			<div class="article">
				안녕하세요?<br>
				${authMemberName}님.<br>
 				<a href = "logout.do">[로그아웃]</a>
				<a href = "changeMemberInfo.do">[회원정보 변경]</a>
			</div>
		</u:isLogin>
		
		<u:notLogin>
			<div class="article">
				안녕하세요?<br>
				<a href = "register.do">[회원가입]</a>
				<a href = "login.do">[로그인]</a>
			</div>
		</u:notLogin>
		
	</div>
	
</body>
</html>