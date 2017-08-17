<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>SignUp</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/form/css/loginForm.css">
</head>

<form action="login.do" method="post">
<h1>IoT System</h1>
<h2>종합 도시관리 시스템</h2>
<fieldset>
  <div class="container">
    <label><b>아이디</b></label>
    <input type="text" placeholder="Enter Username" name="memberId" maxlength="20" value="${param.memberId}" required>
    
    <label><b>비밀번호</b></label>
    <input type="password" placeholder="Enter Password" name="memberPwd" maxlength="20" required>
        
    <button type="submit" value="Login">Login</button>
  </div>
  </fieldset>
</form>
</body>
</html>

</body>
</html>