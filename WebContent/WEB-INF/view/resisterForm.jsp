<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>
</head>
<body>
	<form action="register.do" method="post">
	아이디 : <br><input type="text" name="userId" value="${param.userId}"><br>
	비밀번호 : <br><input type="password" name="password" value="${param.password}"><br>
	이름 : <br><input type="text" name="userName" value="${param.userName}"><br>
	핸드폰번호 : <br><input type="text" name="phoneNumber" value="${param.phoneNumber}"><br>
	<input type="submit" value="가입">
	</form>
</body>
</html>