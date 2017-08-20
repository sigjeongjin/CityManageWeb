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
<h2>회원 가입</h2>

<form action="register.do" method="post" enctype="multipart/form-data">
	<div class="container">
		<label><b>아이디</b></label>
			<input type="text" placeholder="아이디를 입력해주세요." id="memberId" name="memberId" maxlength="20" value="${param.memberId}" required>
		
		<label><b>비밀번호</b></label>
			<input type="password" placeholder="비밀번호  입력해주세요." id="memberPwd" name="memberPwd" maxlength="20"value="${param.memberPwd}" required>
		
		<label><b>비밀번호 확인</b></label>
			<input type="password" placeholder="비밀번호 확인을 입력해주세요." id="conMemberPwd" name="conMemberPwd" maxlength="20" required>
		
		<label><b>이름</b></label>
			<input type="text" placeholder="이름을 입력해주세요." id="memberName" name="memberName" maxlength="20" value="${param.memberName}" required>
		
		<label><b>휴대폰 번호</b></label>
			<input type="text" placeholder="휴대폰 번호를 입력해주세요." id="memberPhone" name="memberPhone" maxlength="20" value="${param.memberPhone}" required>
		
		<label><b>E-mail</b></label>
			<input type="email" placeholder="E-mail을 입력해주세요." id="memberEmail" name="memberEmail" maxlength="20" value="${param.memberEmail}" required>
		
		<label><b>사진</b></label>
			<input type="file" id="mMemberPhone" name="memberPhoto" maxlength="20" value="${param.memberPhoto}" required>
		
		<label><b>시/도 Code</b></label>
			<input type="text" id="mCityGeocode" name="cityGeocode" value="${param.cityGeocode}" required>
		
			<div class="btncenter">
    			<button type="submit" value="signup">가입</button>
    		</div> 
	</div>
</form>
</html>