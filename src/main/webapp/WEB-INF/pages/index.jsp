<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to the Blue Lagoon</title>
<link href="${pageContext.request.contextPath}/resources/styles/index.css" rel="stylesheet">
<link rel="stylesheet" href="resources/styles/buttons.css">
<style type="text/css">
.button-circle {
    width: 150px;
    line-height: 100px;
    height: 100px;
    font-size: 36px;
}
</style>
</head>
<body>
	<div id="common">
		<div id="start">
			<!-- <form:form method="GET" action="/pub" id="formid"> -->
				<span class="button-wrap"><a href="pub" class="button button-circle button-primary"> <!-- onclick="document.getElementById('formid').submit()" -->START</a></span>
			<!-- </form:form> -->
		</div>
	</div>
</body>
</html>