<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>IotSystem</title>
<link rel="stylesheet" type="text/css" href="../../css/tabsBody.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
<div class="allContainer">
<u:isLogin>
<div class="tab">
	<a href="memberList.do"><button>회원관리</button></a>
	<a href="tmList.do"><button>쓰레기통관리</button></a>
	<a href="wmList.do"><button>수질관리</button></a>
	<a href="gmList.do"><button>도시가스관리</button></a>
	<a href="smList.do"><button>금연구역관리</button></a>
	<a href="/pushHistoryList.do"><button>Push이력관리</button></a>
	<a href="myUpdate.do"><button><i class="material-icons" style="font-size:20px" onclick="openCity(event, 'settings')">settings</i></button></a>
	
	<div class="SettringsArticle">
		안녕하세요?<br>
 		${authMemberName}님
	</div>
</div>
	<div class="logoutArticle">
		<a href = "logout.do">☆로그아웃</a>
	</div>
	
	<div class="main">
	</div>

</u:isLogin>

<u:notLogin>
 	<jsp:include page="/view/member/loginForm.jsp" flush="true"/>
</u:notLogin>
</div>
</body>
</html>