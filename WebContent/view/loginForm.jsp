<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>SignIn</title>
<link rel="stylesheet" type="text/css" href="../css/loginForm.css">
</head>

<form action="login.do" method="post">
<h1>IoT System</h1>
<h2>종합 도시관리 시스템</h2>
<fieldset>
	<div class="container">
    	<label><b>아이디</b></label>
    	<input type="text" placeholder="아이디를 입력해주세요." name="memberId" maxlength="20" value="${param.memberId}" required>
    
    	<label><b>비밀번호</b></label>
    	<input type="password" placeholder="비밀번호  입력해주세요." name="memberPwd" maxlength="20" required>
	    	
	    <div class="btncenter">
	    	<button type="submit" value="signin">로그인</button>
	    </div> 
	    <div class="registerArticle">
			<a href = "register.do">★회원가입</a>
		</div>
	</div>
</fieldset>
</form>
</body>
</html>