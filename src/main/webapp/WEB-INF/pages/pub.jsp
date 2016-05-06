<!DOCTYPE html>
<%@page session="true"%>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Web Spring Pub</title>
	<link href="${pageContext.request.contextPath}/resources/styles/pub.css" rel="stylesheet">
	<link rel="stylesheet" href="resources/styles/buttons.css">
	<script src="//code.jquery.com/jquery-2.1.4.min.js"></script>
	<script src="resources/js/ProgressBar.js"></script>
	<script src="resources/js/jquery.percentageloader-0.1.min.js"></script>
</head>
<body>
	<div id="common">
		<div id="header">
			<div id="visitInd">24/250</div>
			<div id="labels">
				<div id="visitIndLabel">&nbsp:Total Visitors</div>
				<div id="beerIndLabel">Drunk beer:&nbsp</div>
			</div>			
			<div id="beerInd">425.25</div>
		</div>
		<div id="content">
			<div id="left">
				<div id="container"></div>
				<script>
    				var bar = new ProgressBar.Circle(container, {
  						color: '#FFEA82',
  						trailWidth: 1,
  						duration: 1400,
  						easing: 'bounce',
  						strokeWidth: 6,
  						from: {color: '#00FF00', a:0},
  						to: {color: '#FF0000', a:1},
  					// Set default step function for all animate calls
  						step: function(state, circle) {
    						circle.path.setAttribute('stroke', state.color);
  						}
					});
					bar.animate(0.5);  // Number from 0.0 to 1.0
				</script>
			</div>		
			<div id="center">
			<textarea id="historyTextArea"></textarea>
			</div>
			<div id="right" class="rightImg">
				<div id="rbar">
				<script>
					$("#rbar").percentageLoader({
    					width : 200, height : 200, progress : 0.5, value : '425L'});
   				</script>
   				</div>
			</div>
		</div>
		<div id="bottom">
		<FORM NAME="form1" ACTION="formAction.jsp" METHOD="POST">
		<div id="add"><a href="#" class="button button-3d-primary button-rounded">Add Visitor</a></div>
		<div id="save"><a href="#" class="button button-3d-highlight button-circle">Save Log</a></div>
		<div id="remove"><a href="#" class="button button-3d-caution button-rounded">Remove Visitor</a></div>		</FORM>
		</div>
	</div>

<h2>Message : ${message}</h2>
<h2>Counter : ${counter}</h2>	
</body>
</html>