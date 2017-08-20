<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>회원가입</title>
<link rel="stylesheet" type="text/css" href="../css/textForm.css">
</head>
<body>
<h2>정보 변경</h2>

<form action="changememberInfo.do" method="post" enctype="multipart/form-data">
	<div class="container">
		<label><b>아이디</b></label>
			<input type="text" id="memberId" name="memberId" maxlength="20" value="${authMemberId}"disabled>
		
		<label><b>비밀번호</b></label>
			<input type="password" id="newPwd" name="newPwd" maxlength="20"value="${param.newPwd}" required>
		
		<label><b>비밀번호 확인</b></label>
			<input type="password" id="conMemberPwd" name="conMemberPwd" maxlength="20" required>
		
		<label><b>이름</b></label>
			<input type="text" id="newName" name="newName" maxlength="20" value="${param.newName}" required>
		
		<label><b>휴대폰 번호</b></label>
			<input type="text" id="newPhone" name="newPhone" maxlength="20" value="${param.newPhone}" required>
		
		<label><b>E-mail</b></label>
			<input type="email" id="newEmail" name="newEmail" maxlength="100" value="${param.newEmail}" required>
		
		<label><b>사진</b></label>
			<input type="file" id="mMemberPhone" name="memberPhoto" value="${param.newPhoto}" required>
		
			<div class="btncenter">
    			<button type="submit" value="signup">정보변경</button>
    		</div> 
	</div>
</form>
</html>