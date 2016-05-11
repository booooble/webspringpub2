<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to the Blue Lagoon</title>
<link href="${pageContext.request.contextPath}/resources/styles/index.css" rel="stylesheet">
<link rel="stylesheet" href="resources/styles/buttons.css">
<style type="text/css">
.button-circle {
    width: 200px;
    line-height: 15px;
    height: 100px;
    font-size: 36px;
}
</style>
</head>
<body>
	<div id="common">
		<div id="start">
			<form action="puby" method="get">
  				<span class="button-wrap"><button type="submit" name="your_name" value="START" class="btn-link button button-circle button-primary">START</button></span>
			</form>
<!-- 			<form:form modelAttribute="pub">
				<span class="button-wrap"><a href="puby" class="button button-circle button-primary" type="submit" value="Submit">START</a></span>
			</form:form> -->
		</div>
	</div>
</body>
</html>