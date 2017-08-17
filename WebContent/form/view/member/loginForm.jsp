<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>SignUp</title>
<link rel = "stylesheet" type="text/css" href="../../css/loginForm.css">
</head>
<form action="login.do" method="post">
<h1>IoT System</h1>
<fieldset>
  <div class="container">
    <label><b>아이디<b></label><br>
    <input type="text" placeholder="Enter Username" name="memberId" maxlength="20" required><br>
    
    <label><b>비밀번호</b></label><br>
    <input type="password" placeholder="Enter Password" name="memberPwd" maxlength="20" required><br><br>
        
    <button type="submit" value="aa">Login</button>
  </div>
  </fieldset>
  

</form>

</body>
</html>