<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>
</head>
<body><form action="index.jsp">
			
			안녕하세요?<br>
			${param.userName}님.<br>
			가입축하드립니다.<br>
			정보확인 부탁드립니다.<br>
			<table>
			<tr>
				<th colspan = "2">회원 정보</th>
			</tr>
			<tr>
				<td>이름</td>
				<td>${param.userName}</td>
			</tr>
			<tr>
				<td>아이디</td>
				<td>${param.userId}</td>
			</tr>
			<tr>
				<td>휴대폰 번호</td>
				<td>${param.phoneNumber}</td>
			</tr>
			</table>
			<input type="submit" value="완료">
	</form>
</body>
</html>