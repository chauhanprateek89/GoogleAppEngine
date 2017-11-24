<%@page import="java.util.List"%>
<%@page import="pc.Rooms"%>
<%@page import="javax.jdo.PersistenceManager"%>
<%@page import="javax.jdo.Query"%>
<%@page import="pc.PMF"%>
<%@page import="javax.jdo.PersistenceManagerFactory"%>
<%@page import="java.util.ArrayList"%>
<%@page import="pc.Bookings"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Room Scheduler</title>
</head>
<body>
<!-- if the user is logged in we need to render one version of the 
page, consequently if the user is logged out we need to render a different 
version of the page -->

	<c:choose>
		<c:when test="${user != null}">
			<p>
				<h2>Welcome ${user.email}</h2><br/>
				<%
				PersistenceManagerFactory pmf = PMF.get();
				PersistenceManager pm = pmf.getPersistenceManager();
				String user = request.getUserPrincipal().getName();
				System.out.println(user);
				List<String> jspVal = new ArrayList<String>();
				try
				{
					Query q = pm.newQuery(Rooms.class);
					q.compile();
					@SuppressWarnings("unchecked")
					List<Rooms> results = (List<Rooms>)q.execute();
					
					for(Rooms res : results)
					{
						jspVal.add(res.getRoomName());
					}
				}
				catch(Exception e){}
				finally{pm.close();}
					
					
					if(jspVal.size() != 0)
					{
						%>
						<h3>Displaying all the rooms</h3><br/>
						<form action="/viewResults" method="post">
						Select a Room: &nbsp;
						<select name="room">
							<%
							for(String results : jspVal)
							{
								%>
								<option value="<%=results%>"><%=results%></option>
								<%
							}
							%>
							
						</select><br/><br/>
						<input type="hidden" name="hidden" value="<%=user%>"/>
						<input type="submit" name="viewBookings" value="View Bookings"/>
						
						<br/> 
						</form>
						<br/>
						<form action="/filterView" method="post">
						Select a date to apply filter on Rooms &nbsp;&nbsp;
						<input type="date" name="filter" required="required"/>&nbsp;
						<input type="hidden" name="user" value="<%=user %>"/>
						<input type="submit" name="filterSubmit" value="Filter">
						</form>
													
						<%} %>
				<h3>Add a new room</h3><br/>
				<form action="/" method="post">
					Room Number:&nbsp;
					<input type="text" name="roomNo" autofocus="autofocus" required="required"/>
				<br/><br/>
				Room type: Smoking? &nbsp;
				<select name="smoking" required="required">
					<option value="TRUE">Yes</option>
					<option value="FALSE">No</option>
				</select>
				<br/>
				<br/>
				
				<input type="submit" name="add" value="Add a room"/> &nbsp;&nbsp;
				</form>
				<form action="/booking">
					<input type="submit" name="booking" value="Make a booking">
				</form>	
				<br/>You can signout <a href="${logout_url}">here</a><br/>
			</p>
		</c:when>
		<c:otherwise>
			<p>
				Welcome! <br/>
				<a href="${login_url}">Sign in or register</a>
			</p>
		</c:otherwise>
	</c:choose>
</body>
</html>