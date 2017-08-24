<%@page language = "java" contentType="text/html; charset = UTF-8"
pageEncoding = "UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>관리지역 등록</title>

<link rel="stylesheet" type="text/css" href="../css/textForm.css">
</head>
<body>
<jsp:include page="../header/menuHeader.jsp" flush="true"/>
<h2>관리 지역 등록</h2>

	 <form action="managementareaRegister.do" method="post"> 
	<div class="container">
	<table>
	<tr>
		<td colspan="2"><label><b>관리ID :</b></label></td>
		<td colspan="3"><input type="text" placeholder="M00001" id="manageId" name="manageId" value="${param.manageId}" disabled></td>
	</tr>
	<tr>
		<td colspan="2"><label><b>지역선택 :</b></label></td>
		<td colspan="1">
			<select id="cityCode" name="cityCode" onchange="javascript:selectEvent(this)">
				<option>시/도</option>
				<c:forEach var="address" items="${addressCityList}" varStatus="status">
				<option value="${address.cityCode}">${address.cityName}</option>
				</c:forEach>
	    	</select></td>
	    	
	    <td colspan="1">
	    	 <select id="stateCode" name="stateCode">
			 <option>시/군/구</option>
	    	</select></td>
	</tr>	
	<tr>
		<td colspan="2"><label><b>좌표값 :</b></label></td>
		<td colspan="1"><input type="text" placeholder="위도" id="latitude" name="latitude" value="${param.latitude}" required></td>
		<td colspan="1"><input type="text" placeholder="경도" id="longitude" name="longitude" value="${param.longitude}" required></td>
	</tr>
	<tr>
		<td colspan="2"><label><b>비고 :</b></label></td>
		<td colspan="2">
		<textarea rows="4" cols="70"id="memo" name="memo"></textarea>
		</td>
	</tr>
	<tr>
	<td colspan="2"><label><b>센서종류 :</b></label></td> 
		<td colspan="1"><input type="checkbox" id="sensorTypes" name="sensorTypes" value="wq"><label><b>수질센서</b></label></td>
		<td colspan="1"><input type="checkbox" id="sensorTypes" name="sensorTypes" value="wl"><label><b>수위센서</b></label></td>
	</tr>
	<tr>
		<td colspan="4">	
		<div class="btncenter">
    		<button type="submit" value="register">등록</button>
    	</div>
    	</td>
    </tr>
		</table>
	</div>
</form>

</body>
<script src="../../js/jquery-1.11.0.min.js"></script>
<script type="text/javascript">

function selectEvent() {
    var id = $("#cityCode").val();
    var query = {cityCode:id};         
    $.ajax({
          type : "GET",
          dataType:"json",
          url : "/managementareaState.ajax",
          data : query,
          error : function(){
             $("#confirmText").html(data);
          },
          success : function(data){
		  var options = $('#stateCode');
						
		  options.html("<option>시/군/구</option>");
			
		  for (var i = 0; i < data.state.length; i++) {
				
				console.log("park");
				options.append("<option value=" + data.state[i].stateCode + ">" 
						+ data.state[i].stateName +"</option>");
			}	

         }   
    });
}
</script>
</html>