<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Assignment 1</title>
</head>
<body>

<!-- this is the interface that the user sees when he first access the application, if the user is logged in then we need to render one version
of the page
consequently if the user is logged out we need to render a different version of the page -->

	<c:choose>
		<c:when test="${user != null}">
		<!-- this renderation is given when the user is logged in, consists of 2 forms; one for adding the GPU and the second one for searching
		for a GPU via selecting features -->
			<p>
			<form action="/" method="post" name="driverForm">
				Welcome ${user.email} <br />
				<br /> Add a driver: <br /> Name: <input type="text"
					name="driver_name" /><br /> <br />Select Features<br /> <input
					type="checkbox" name="geometryShader" value="1" /> geometryShader<br />
				<input type="hidden" name="geometryShader" value="0" /> <input
					type="checkbox" name="tesselationShader" value="1" />
				tesselationShader<br /> <input type="hidden"
					name="tesselationShader" value="0" /> <input type="checkbox"
					name="shaderInt16" value="1" /> shaderInt16<br /> <input
					type="hidden" name="shaderInt16" value="0" /> <input
					type="checkbox" name="sparseBinding" value="1" /> sparseBinding<br />
				<input type="hidden" name="sparseBinding" value="0" /> <input
					type="checkbox" name="textureCompressionETC2" value="1" />
				textureCompressionETC2<br /> <input type="hidden"
					name="textureCompressionETC2" value="0" /> <input type="checkbox"
					name="vertexPipelineStoresAndAtomics" value="1" />
				vertexPipelineStoresAndAtomics<br /> <input type="hidden"
					name="vertexPipelineStoresAndAtomics" value="0" /> <input
					type="submit" value="Add" />
			</form>

			<form action="/searchQuery" name="searchQuery" method="post">
				Select the features for the driver:<br /> <input type="checkbox"
					name="geometryShader" value="1" /> geometryShader<br /> <input
					type="hidden" name="geometryShader" value="0" /> <input
					type="checkbox" name="tesselationShader" value="1" />
				tesselationShader<br /> <input type="hidden"
					name="tesselationShader" value="0" /> <input type="checkbox"
					name="shaderInt16" value="1" /> shaderInt16<br /> <input
					type="hidden" name="shaderInt16" value="0" /> <input
					type="checkbox" name="sparseBinding" value="1" /> sparseBinding<br />
				<input type="hidden" name="sparseBinding" value="0" /> <input
					type="checkbox" name="textureCompressionETC2" value="1" />
				textureCompressionETC2<br /> <input type="hidden"
					name="textureCompressionETC2" value="0" /> <input type="checkbox"
					name="vertexPipelineStoresAndAtomics" value="1" />
				vertexPipelineStoresAndAtomics<br /> <input type="hidden"
					name="vertexPipelineStoresAndAtomics" value="0" /> <input
					type="submit" value="Search" /><br />
				<br />
			</form>
			You can signout <a href="${logout_url}">here</a>
			<br />
			<br />
			<br />

		</c:when>
		<c:otherwise>
			<p>
				Welcome! <a href="${login_url}">Sign in or register</a>
			</p>
		</c:otherwise>
	</c:choose>
	
</body>
</html>