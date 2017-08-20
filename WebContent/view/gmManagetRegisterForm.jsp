<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>관리지역 등록</title>

<link rel="stylesheet" type="text/css" href="../css/mangeForm.css">
</head>
<body>
<h2>관리 지역 등록</h2>

	 <form action="managementarea.do" method="post"> 
	<div class="container">
	<table>
	<tr>
		<td colspan="1"><label><b>관리ID :</b></label></td>
		<td colspan="3"><input type="text" placeholder="S0001" id="manageId" name="manageId" value="${param.manageId}" disabled></td>
	</tr>
	<tr>
		<td colspan="1"><label><b>지역선택 :</b></label></td>
		<td colspan="1.5"><input type="text" placeholder="시/도" id="cityGeocode" name="cityGeocode" value="${param.cityGeocode}" required></td>
		<td colspan="1.5"><input type="text" placeholder="시/군/구" id="stateGeocode" name="stateGeocode" value="${param.stateGeocode}" required></td>
	</tr>	
	<tr>
		<td colspan="1"><label><b>좌표값 :</b></label></td>
		<td colspan="1.5"><input type="text" placeholder="위도" id="latitude" name="latitude" value="${param.latitude}" required></td>
		<td colspan="1.5"><input type="text" placeholder="경도" id="longitude" name="longitude" value="${param.longitude}" required></td>
	</tr>
	<tr>
		<td colspan="1"><label><b>비고 :</b></label></td>
		<td colspan="3">
		<textarea rows="4" cols="80"id="memo" name="memo"></textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2"><input type="checkbox" id="memberId" name="memberId" value="sensorType"><label><b>불꽃감지센서</b></label></td>
		<td colspan="2"><input type="checkbox" id="memberId" name="memberId" value="sensorType"><label><b>압력센서</b></label></td>
	</tr>
	<tr>
		<td colspan="2"><input type="checkbox" id="memberId" name="memberId" value="sensorType"><label><b>불꽃감지센서</b></label></td>
		<td colspan="2"><input type="checkbox" id="memberId" name="memberId" value="sensorType"><label><b>압력센서</b></label></td>
	</tr>
	<tr>
		<td colspan="4">	
		<div class="btncenter">
    		<button type="submit" value="signup">가입</button>
    	</div>
    	</td>
    </tr>
		</table>
	</div>
</form>
</body>
</html>