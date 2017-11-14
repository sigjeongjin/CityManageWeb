<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>로그인</title>
<link rel="stylesheet" type="text/css" href="../../css/login.css">
</head>
<div class="loginFromContainer">
<form action="login.do" method="post">
<P class="mainText"><b>IoT System</b></P>
<P class="subText"><b>종합 도시관리 시스템</b></P>
<fieldset>
	<div class="loginContainer">
    	<label class="loginLable"><b>아이디</b></label>
    	<input type="text" placeholder="아이디를 입력해주세요." name="memberId" maxlength="20" value="${param.memberId}" required>
    
    	<label class="loginLable"><b>비밀번호</b></label>
    	<input type="password" placeholder="비밀번호  입력해주세요." name="memberPwd" maxlength="20" required>
	    	
	    <div class="btncenter">
	    	<button class="loginBtn" type="submit" value="loginBtn">로그인</button>
	    </div> 
	    <div class="registerContainer">
			<a href = "register.do">★회원가입</a>
		</div>
	</div>
</fieldset>
</form>
</div>
<input type="hidden" value="${error}" id="error"/>
</body>
<script src="../../js/jquery-1.11.0.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	var err = $("#error").val();
	
	console.log(err);
	
	if(err.length > 0){
		alert($("#error").val());	
	}
	
});
</script>
</html>