<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
<title>IotSystem</title>
<link rel="stylesheet" type="text/css" href="../css/tabsBody.css">

</head>
<body>

<u:isLogin>
<div class="tab">
<!--   <button class="tablinks" onclick="openCity(event, 'London')" id="defaultOpen">회원 관리</button> -->
  <button class="tablinks" onclick="openCity(event, 'memberManage')">회원관리</button>
  <button class="tablinks" onclick="openCity(event, 'tmSensorManage')">쓰레기통관리</button>
  <button class="tablinks" onclick="openCity(event, 'wmSensorManage')">수질관리</button>
  <button class="tablinks" onclick="openCity(event, 'gmSensorManage')">도시가스 관리</button>
  <button class="tablinks" onclick="openCity(event, 'smSensorManage')">금연구역관리</button>
  <button class="tablinks" onclick="openCity(event, 'pushHistoryManage')">PUSH이력관리</button>
</div>


<div id="memberManage" class="tabcontent">
  <h3>회원관리</h3>
  <p>화원이다 이자식아</p>
</div>

<div id="tmSensorManage" class="tabcontent">
  <h3>쓰레기통관리</h3>
  <p>쓰레기통이다 이자식아</p> 
</div>

<div id="wmSensorManage" class="tabcontent">
  <h3>수질관리</h3>
  <p>수질이다 이자식아</p>
</div>

<div id="gmSensorManage" class="tabcontent">
  <h3>도시가스</h3>
  <p>도시가스다 이자식아</p>
</div>

<div id="smSensorManage" class="tabcontent">
  <h3>금연구역관리</h3>
  <p>금연구역이다 이자식아</p>
</div>

<div id="pushHistoryManage" class="tabcontent">
  <h3>PUSH이력관리</h3>
  <p>PUSH다 이자식아</p>
</div>

	<div class="logout.doArticle">
		안녕하세요?<br>
		${authMemberName}님.<br>
		<a href = "logout.do">[로그아웃]</a>
		<a href = "changeMemberInfo.do">[회원정보 변경]</a>
	</div>
</u:isLogin>

<u:notLogin>
	<jsp:include page="/view/loginForm.jsp" flush="true"/>
	<div class="loginArticle">
		안녕하세요?<br>
		<a href = "register.do">[회원가입]</a>
	</div>
</u:notLogin>
	
<script>
function openCity(evt, cityName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
}

// Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();
</script>
	
</body>
</html>