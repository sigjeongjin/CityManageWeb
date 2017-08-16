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
					<td>아이디</td>
					<td><input type="text" id="mMemberId" name="memberId" maxlength="20" value="${param.memberId}"></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" id="mMemberPwd" name="memberPwd" maxlength="20"value="${param.memberPwd}"></td>
				</tr>
				<tr>
					<td>비밀번호 확인</td>
					<td><input type="password" id="mConMemberPwd" name="conMemberPwd" maxlength="20"></td>
				</tr>
				<tr>
					<td>이름</td>
					<td><input type="text" id="mMemberName" name="memberName" maxlength="20" value="${param.memberName}"></td>
	
				</tr>
				<tr>
					<td>휴대폰 번호</td>
					<td><input type="text" id="mMemberPhone" name="memberPhone" maxlength="20" value="${param.memberPhone}"></td>
				</tr>
				<tr>
					<td>E-mail</td>
					<td><input type="email" id="mMemberEmail" name="memberEmail" maxlength="20" value="${param.memberEmail}"></td>
				</tr>	
				
				<tr>
					<td>사진</td>
					<td><input type="file" id="mMemberPhone" name="memberPhoto" maxlength="20" value="${param.memberPhoto}"></td>
				</tr>
 				<tr>
					<td>11</td>
					<td><input type="text" id="mMemberAuthorization" name="memberAuthorization" value="${param.memberAuthorization}"></td>
				</tr>
				<tr>
					<td>코드1</td>
					<td><input type="text" id="mCityGeocode" name="cityGeocode" value="${param.cityGeocode}"></td>
				</tr>
				<tr>
					<td>코드2</td>
					<td><input type="text" id="mStateGeocode" name="stateGeocode" value="${param.stateGeocode}"></td>
				</tr>
	
				<tr>
					<th colspan="2">
						<input id="completeBtn" type="button" value="가입">
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