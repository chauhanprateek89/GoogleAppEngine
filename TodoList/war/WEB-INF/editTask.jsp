<%@page import="com.Todo"%>
<%@page import="com.TodoUser"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Task</title>
</head>
<body>
<%
//TodoUser tu = (TodoUser)request.getAttribute("todoUser");
//Todo t = (Todo)request.getAttribute("todo");
String task_name = (String)request.getAttribute("task_name");
request.getSession().setAttribute("todo", request.getSession().getAttribute("todo"));
//int userIndex = Integer.valueOf(((String)request.getAttribute("userIndex")));
%>
Edit task<br/>
<form action="/editTask" method="post">
<input type="text" name="edit_task" value=<%=task_name%>>
<%-- <input type="hidden" name="t" value=<%=t%>>
<input type="hidden" name="tu" value=<%=tu%>>  --%>
<%-- <input type="hidden" name="userIndex" value=<%=userIndex%>> --%>

<input type="submit" value="Change Task"/>
</form> 
</body>
</html>