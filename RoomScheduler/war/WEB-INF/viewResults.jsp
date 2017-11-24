<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Room Scheduler - View Bookings</title>
</head>
<body>
<%

	String u = (String)request.getAttribute("user");
	String roomName = (String)request.getAttribute("roomDB");
	List<String> bookNameDB = (List<String>)request.getAttribute("bookNameDB");
	List<Date> fromDateDB = (List<Date>)request.getAttribute("fromDateDB");
	List<Date> toDateDB = (List<Date>)request.getAttribute("toDateDB");
	List<String> userDB = (List<String>)request.getAttribute("userDB");
	String bookingID;
	Date fromDate;
	Date toDate;
	String user;
	
%>
<p>
<form action="/deleteBooking" method="post">
	<table border="0">
		<tr>
			<th>Booking ID</th>
			<th>Reservation from</th>
			<th>Reservation till</th>
			<th>Reserved by</th>
			<th>Delete?</th>
		</tr>
		
		<tr>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
		</tr>
			<%
			for(int i = 0; i < bookNameDB.size(); i++)
			{
				bookingID = bookNameDB.get(i);
				fromDate = fromDateDB.get(i);
				toDate = toDateDB.get(i);
				user = userDB.get(i);
				%>
				<tr>
					<th><%=bookingID%></th>
					<th><%=fromDate%></th>
					<th><%=toDate%></th>
					<th><%=user%></th>
					<th>
					<input type="hidden" name=<%=roomName%>>
					<input type="submit" name=<%=bookingID%> value="delete"/> </th>
				</tr>
				<%
			}
			%>
		
	</table>
	</form>
	<form action="/">
		<br/><input type="submit" value="back">
	</form>
</p>
</body>
</html>