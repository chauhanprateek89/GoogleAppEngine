<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Room Scheduler - Bookings</title>
</head>
<body>
<%String user = request.getUserPrincipal().getName();
				System.out.print(user);
%>
	<%
	@SuppressWarnings("unchecked")
	ArrayList<String> list = (ArrayList<String>)request.getAttribute("roomName");
	System.out.println(list);
	String message = (String)request.getAttribute("message");
	if(message!=null)
	{
		%><%=message%><br/>
		<form action="/booking"><input type="submit" value="Back"></form><%
	}
	if(list != null)
	{
		%>
		<h3>Select a room for booking</h3><br/>
		<form action="/booking" method="post">
		<%for(String results : list) 
		{
		%>
		<input type="radio" name="roomSelect" value="<%=results%>" required/>&nbsp;<%=results%><br/>
		<%
		}%>
		<br/>
		<table border="0">
			<tr>
				<th>From date</th>
				<th>To date</th>
			</tr>
			<tr>
				<th><input type="datetime-local" name="fromDate" required="required"/></th>
				<th><input type="datetime-local" name="toDate" required="required"/></th>
			</tr>
		</table>
		<br/>
		<input type="hidden" name="user" value="<%=user%>"/>
		<input type="submit" name="submitBooking" value="Book"/>
		</form>
		<%}	%>	
</body>
</html>