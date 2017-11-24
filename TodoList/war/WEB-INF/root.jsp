<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@	taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
	<head>
	<script type="text/javascript">
	    function func() { 
	            document.getElementById("newTask").style.display="block";
	    }
	</script>
	<meta charset="UTF-8">
	<title>Task Manager</title>
	</head>
	<body>
	
	
		<c:choose>
			<c:when test = "${user != null}">
				<h1> Task Manager </h1>
				<p> User email: ${user.email}<br/>
				
				<h2>Task List</h2>					
					<table border="1">
					  <tr>
					    <th>Task Name</th>
					    <th>Actions</th>
					  </tr>
					  <c:forEach items="${tasks}" var="todo" varStatus="loop">
					    <tr>		
					      <c:choose>
							   <c:when test="${todo.complete}">
							     <td><del><c:out value="${todo.name}"/></del></td>
							   </c:when>
							   <c:otherwise>
							     <td><c:out value="${todo.name}"/></td>
							   </c:otherwise>
							</c:choose>
							<td>
							<form action="/update" method="post">
								<c:choose> 
								   <c:when test="${todo.complete}">								     
									    <input type="hidden" name="index" value="${loop.index}">
									    <input type="submit" name="action_name" value="Incomplete">&nbsp;&nbsp;							
								   </c:when>
								   <c:otherwise>								  
									    <input type="hidden" name="index" value="${loop.index}">
									    <input type="submit" name="action_name" value="Complete">&nbsp;&nbsp;								
								   </c:otherwise>
								</c:choose>								
								    <input type="hidden" name="index" value="${loop.index}">
								    <input type="submit" name="action_name" value="Delete">&nbsp;&nbsp;
								    <input type="hidden" name="index" value="${loop.index}">
								    <input type="submit" name="action_name" value="Edit">&nbsp;&nbsp;
								</form>&nbsp;&nbsp;
							</td>
					    </tr>
					  </c:forEach>
					</table>
					<br/>
					<button type=button id=f1 onclick="func()">New Task</button>
					<form action="/" method="post" id = newTask style="display:none;" >
						<br/>
						Task Name:
						<input type="text" name="name" maxlength="20"/><br/><br/>
						<input type="submit" value="Add Task"/><br/>
						
					</form>
				</p>
				<form action="/logout" method="post">
					You can signout
					<input type="hidden" name="logout_url" value="${logout_url}">
					<input type="submit" value="Logout">
				</form>
			</c:when>
			<c:otherwise>
					<h1>Welcome to Todo List <h1/>
					<a href="${login_url}">Sign in or register</a>
			</c:otherwise>
		</c:choose>
	
	</body>
</html>