<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Dropbox</title>
	</head>
	<body>
		<c:choose>
			 <c:when test="${user != null}"> 
		 		<p> Welcome ${user.email} <br/> </p>
		 		
		 		
				<h2>${path}</h2>
				<c:if test="${path!='root/'}">
					<form method="post" action="/directory">
						../<input type="hidden" name="dir_index" value="-1"/>
						<input type="submit" name="action" value="Change"/>
					</form>
				</c:if>
				<hr>
				<c:choose>
					<c:when test  ="${!(empty directories) || !(empty files)}" > 
						<!-- The list of files and folders passed in that will be made available -->
						<c:forEach items="${directories}" var= "directory" begin= "0" varStatus= "loop">
							<form method="post" action="/directory">
							<table border="0">
							<col width="200">
							<col width="100">
							<col width="100">
								<tr>
									<th>Directory name</th>
									<th>Browse</th>
									<th>Delete</th>
								</tr>
								<tr>
									<th>${directory}<input type="hidden" name="dir_index" value="${loop.index}"/></th>
									<th><input type="submit" name="action" value="Change"/></th>
									<th><input type="submit" name="action" value="Delete"/></th>
								</tr>
							</table>
							</form>
						</c:forEach><br/>
						<c:forEach items="${files}" var= "file" begin= "0" varStatus= "loop">
							<form method="get" action="/files">
							<table border="0s">
							<col width="200">
							<col width="100">
							<col width="100">
								<tr>
									<th>File name</th>
									<th>Download</th>
									<th>Delete</th>
								</tr>
								<tr>
									<th>${file.name}<input type="hidden" name="file_index" value="${loop.index}"/></th>
									<th><input type="submit" name="action" value="Download"/></th>
									<th><input type="submit" name="action" value="Delete"/></th>
								</tr>
							</table>
							</form>
						</c:forEach>
					</c:when>
					
		 			<c:otherwise  > 
		 				<p> No Files. Add a file or directory </p> 
		 			</c:otherwise> 
	 			</c:choose> 
				<hr>
				
				<form enctype="multipart/form-data" method="post" action="<%=BlobstoreServiceFactory.getBlobstoreService().createUploadUrl("/files")%>">
					<br/><br/><h2>Upload a File:</h2><br/> <input type="file" name="file" size="30"/>
					<input type="submit"/>
				</form><br/><hr>
				<form action="/directory" method="post"> 
					<br/><br/><h2>Create New Directory:</h2><br/> <input type="text" name="new_directory"/> 
					<input type="submit" name="action" value="Add"/> 
				</form>
				<br/><br/><br/><hr>
				<form action="/logout" method="post">
					You can signout
					<input type="hidden" name="logout_url" value="${logout_url}">
					<input type="submit" value="Logout">
				</form>
				
			</c:when>
 			<c:otherwise  > 
 				<p> Welcome! <a href="${login_url}">Sign in or register</a> </p> 
 			</c:otherwise> 
 		</c:choose> 
	</body>
</html>