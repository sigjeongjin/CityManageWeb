<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>


<script src="../js/jquery-1.11.0.min.js"></script>

<!DOCTYPE html>
<html>
<head>
<style>
	body {
	    font-family: Verdana,sans-serif;
	    font-size: 0.9em;
	    /* text-align: center; */
	}
	div#content {
	    margin: 5px;
	    padding: 10px;
	    background-color: #ff8080;  
	}
	div.article {
    margin: 10px;
    padding: 10px;
    background-color: white;
	}
	table {
    border-collapse: collapse;
    width: 100%;
	}		
	th {
		text-align: center;
		color: black;
		font-size: 20px;
	}	
	td {
		border: 2px solid #dddddd;
		padding: 10px;
		text-align: left;
	}
	input#mName, input#mId, input#mPw, input#mConPw, input#mAge, input#mPhoneNumber {
		width: 180px;
	}
</style>
</head>
<body>
	<jsp:include page="header.jsp" flush="true"/>
	
	<div id="content">
		<h2>register</h2>
	<div class="article">
		<form id="welcome" name="welcome" action="register.do" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<th colspan="2">정보 입력</th>
				</tr>
				<tr>
					<td>sensorId</td>
					<td><input type="text" id="sensorId" name="sensorId" maxlength="20" value="${param.sensorId}"></td>
				</tr>
				<tr>
					<td>cityGeocode</td>
					<td><input type="text" id="cityGeocode" name="cityGeocode" maxlength="20" value="${param.cityGeocode}"></td>
				</tr>
				<tr>
					<td>stateGeocode</td>
					<td><input type="text" id="stateGeocode" name="stateGeocode" maxlength="20" value="${param.stateGeocode}"></td>
				</tr>
				<tr>
					<td>latitude</td>
					<td><input type="text" id="latitude" name="latitude" maxlength="20" value="${param.latitude}"></td>
				</tr>
				<tr>
					<td>longitude</td>
					<td><input type="text" id="longitude" name="longitude" maxlength="20" value="${param.longitude}"></td>
				</tr>
					<tr>
					<td>memo</td>
					<td><input type="text" id="memo" name="memo" value="${param.memo}"></td>
				</tr>
				</tr>
					<tr>
					<td>sensorNoticeStandard</td>
					<td><input type="text" id="memo" name="memo" value="${param.sensorNoticeStandard}"></td>
				</tr>

				<tr>
					<th colspan="2">
						<input id="completeBtn" type="button" value="등록">
					</th>
				</tr>
			</table>
		
		</form>
		
		<div id="upResult"></div>
		
	</div>
	
	</div>
<script type="text/javascript">
$(document).ready(function(){
    $( "#confirmIdCheck" ).click(function() {
       var id = $("#id").val();
       var query = {id:id};         
       $.ajax({
             type : "GET",
             url : "/confirmIdCheck.do",
             data : query,
             error : function(){
			alert("실패");
             },
             success : function(data){
            	 alert("성공");
             }            
		});
	});
});


</script>

</body>

<script type="text/javascript">
	
	var welcomeForm = document.welcome;
	
/* 	var mName = document.getElementById("mName");
	var mId = document.getElementById("mId");
	var mPw = document.getElementById("mPw");
	var mConPw = document.getElementById("mConPw");
	var mPhoneNumber = document.getElementById("mPhoneNumber");
 */

	var completeBtn = document.getElementById("completeBtn");
	completeBtn.addEventListener("click", function(){
/* 		if (mName.value.length == 0){
			alert("이름을 입력해주세요.");
			return;
		}
		if (mId.value.length == 0){
			alert("아이디를 입력해주세요.");
			return;
		}
		if (mConPw.value.length == 0){
			alert("비밀번호를 입력해주세요.");
			return;
		}
		if (mPw.value.length == 0){
			alert("비밀번호 확인을 입력해주세요.");
			return;
		}
		if (mPhoneNumber.value.length == 0){
			alert("핸드폰 번호을 입력해 주세요.");
			return;
		} */
			welcomeForm.submit();	
	});
	
</script>

</html>