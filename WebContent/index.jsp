<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>CityManageWeb</title>
<link rel="stylesheet" type="text/css" href="../css/mainDash.css">
</head>
<body>
<jsp:include page="view/header/menuHeader.jsp" flush="true"/>
<div class="noticeContainer">
	<div class="registerMember">
		<fieldset>
			<legend>회원정보 리스트</legend>
					<c:forEach var="memberNameList" items="${memberNameList}" varStatus="status">
							<p>${memberNameList.memberName}님이 가입하였습니다.</p>
					</c:forEach>
		</fieldset>
	</div>
	<div class="registerTm">
		<fieldset>
			<legend>쓰레기통관리 리스트</legend>
				<c:forEach var="tmRegisterList" items="${tmRegisterList}" varStatus="status">
							<p>${tmRegisterList.locationName} 지역에 센서${tmRegisterList.sensorId} 추가</p>
				</c:forEach>
		</fieldset>
	</div>
</div>

<div class="noticeContainer">
	<div class="registerWm">
		<fieldset>
			<legend>수질관리 리스트</legend>
				<c:forEach var="wmRegisterList" items="${wmRegisterList}" varStatus="status">
							<p>${wmRegisterList.locationName} 지역에 센서${wmRegisterList.sensorId} 추가</p>
				</c:forEach>
		</fieldset>
	</div>
	<div class="registerGm">
		<fieldset>
			<legend>도시가스관리 리스트</legend>
				<c:forEach var="gmRegisterList" items="${gmRegisterList}" varStatus="status">
							<p>${gmRegisterList.locationName} 지역에 센서${gmRegisterList.sensorId} 추가</p>
				</c:forEach>
		</fieldset>
	</div>
</div>

<div class="noticeContainer">
	<div class="registerSm">
		<fieldset>
			<legend>금연구연관리 리스트</legend>
				<c:forEach var="smRegisterList" items="${smRegisterList}" varStatus="status">
							<p>${smRegisterList.locationName} 지역에 센서${smRegisterList.sensorId} 추가</p>
				</c:forEach>
		</fieldset>
	</div>
	<div class="registerPush">
		<fieldset>
			<legend>push이력 리스트</legend>
		</fieldset>
	</div>
</div>
</body>
</html>