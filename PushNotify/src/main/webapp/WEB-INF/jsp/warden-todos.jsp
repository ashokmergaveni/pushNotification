<%@ page language="java"%>
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<style><%@include file="common/sample.css"%></style>
	<html>
	<head>
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style type="text/css">
	.bs-example{
		margin: 20px;
	}
</style>
	
	
	<script type="text/javascript">
	function approvePopup() {
	    var strconfirm = confirm("Are you sure you want to delete?");
	    if (strconfirm == true) {
	         approve();
	    }else return false;
	}
	function rejectPopup() {
	    var strconfirm = confirm("Are you sure you want to delete?");
	    if (strconfirm == true) {
	         approve();
	    }else return false;
	}
	
	
 function approve() {
			 // setTimeout(function(){
		       document.getElementById('contents').style.visibility="visible";
		         document.getElementById('load').style.display="block";
			 // },1000);
		  return true;
		}
</script>
	</head>
	<body style="background-color:white;">
	
	<form  action="/update-todo" method ="get" >
	
	<div id="load" style="display:none; text-align: center;"><h1>Please wait while loading</h1></div>	<div class="container" id="contents" >
		<table class="table table-fit" style="background-color:white;border: 1px solid black ;">
			<caption><b>Warden Info</b>		</caption>
			<thead>
			<tr style="background-color:white;">
			<td style="width:20%;"><input id="submit" name="submit" type="submit" value="Add" onclick="return approvePopup();" style="width:60%;background-color:MediumSeaGreen;"/>
			</td>
			<td style="width:20%;"><input id="submit" name="submit" type="submit" value="Delete" onclick="return approvePopup();" style="width:60%;background-color:MediumSeaGreen;"/>
			</td>
			</tr>
				<tr>
				<th style="width:12%;">Select</th>
				<th> warden id </th>
					<th style="width:15%;" >warden name</th>
					<th> floor </th>
					<th>division</th>
					<th>muster location</th>
					<th>EDIT</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${todos}" var="tod">
					<tr>
					<td ><input type="checkbox" style="zoom:1.5;" name="eventId" value="${tod.eventId}" /></td>
						<td>${tod.eventId}</td>
						<td>${tod.name}</td>
					<%-- 	<td><fmt:formatDate value="${todo.targetDate}" pattern="dd/MM/yyyy"/></td> --%>
						 <td>${tod.venue}</td>
						 <td>${tod.alert_type}</td>
						 <td width="30%">${tod.description}</td>
						 
						 
						 <td ><a href="/add-warden?eventId=${tod.eventId}" >Edit</a>	</td>
						<%--  <td>${todo.eventId}</td> --%>
						<%-- <td><a type="button" class="btn btn-success"
							href="/update-todo?eventId=${todo.eventId}">Approve</a></td>
						<td><a type="button" class="btn btn-warning"
							href="/delete-todo?eventId=${todo.eventId}">Reject</a></td> --%>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	</form>
	</body>
	</html>
<%@ include file="common/footer.jspf" %>
