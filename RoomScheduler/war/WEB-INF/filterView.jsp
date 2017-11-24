<%@page import="com.google.appengine.api.datastore.Key"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Room Scheduler - Filter By Date</title>
</head>
<body>

<%

	String user = (String)request.getAttribute("user");
	Date formattedDate = (Date)request.getAttribute("formattedDate");
	List<Date> fromDateDB = (List<Date>)request.getAttribute("fromDateDB");
	List<Date> toDateDB = (List<Date>)request.getAttribute("toDateDB");
	List<Key> IDList = (List<Key>)request.getAttribute("IDList");
	List<String> bookingNameDB = (List<String>)request.getAttribute("bookingNameDB");
%>
<h3>Displaying all rooms booked on <%=formattedDate %><br/></h3>
<table border="0">
	<tr>
		<th>Room</th>
		<th>Booking</th>
	</tr>

<%	
for(int i = 0; i<bookingNameDB.size(); i++)
{
	if(!(formattedDate.before(fromDateDB.get(i)) || formattedDate.after(toDateDB.get(i))))	
	{
		%>
		<tr>
			<th><%=IDList.get(i) %></th>
			<th><%=bookingNameDB.get(i) %></th>
		</tr>
		<%
	}
	
}
%>
</table>
<br/><br/>
<form action="/">
	<input type="submit" value="Back">
</form>
</body>
</html>