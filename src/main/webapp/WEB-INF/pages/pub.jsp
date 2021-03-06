<!DOCTYPE html>
<%-- <%@page session="true"%> --%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Web Spring Pub</title>
	<link href="${pageContext.request.contextPath}/resources/styles/pub.css" rel="stylesheet">
	<link rel="stylesheet" href="resources/styles/buttons.css">
	<link rel="stylesheet" href="resources/styles/font-awesome.min.css">
	<script src="//code.jquery.com/jquery-2.1.4.min.js"></script>
	<script src="resources/js/ProgressBar.js"></script>
	<script src="resources/js/jquery.percentageloader-0.1.min.js"></script>
</head>
<body>
	<div id="common">
		<div id="labels">
			<div id="visitIndLabel">Visitors:</div>
			<div id="queueIndLabel">In Queue:</div>
			<div id="beerIndLabel">Drunk beer:</div>
		</div>
		<div id="infoBar">
			<div id="visitorsInfo">${pub.currCapacity}/${pub.maxCapacity}</div>
			<div id="incrQueue" >
					<form action="add2queue" method="POST">
 						<!-- <a href="#" class="button button-rounded button-flat-primary"><i class="fa fa-user-plus"></i>Process</a> -->
 						<button type="submit" name="incQueue" value="Add Visitor" class="button button-rounded button-flat-primary" style="height:50px; width:50px; margin-top: 12px; padding: 0px; line-height: 0px; background: rgba(12, 222, 0, 0.51); vertical-align: top;" ><i class="fa fa-plus-circle fa-3x" ></i></button> 
					</form>
			</div>
			<div id="queueInfo">${pub.visitorsQueueSize}</div>
			<div id="processQueue" >
					<form action="processQueue" method="POST">
 						<!-- <a href="#" class="button button-rounded button-flat-primary"><i class="fa fa-refresh"></i>Process</a> -->
 						<button type="submit" name="procQueue" class="button button-rounded button-flat-primary" style="height:50px; width:50px; margin-top: 12px; padding: 0px; line-height: 0px; background: rgba(12, 222, 0, 0.51); vertical-align: top;" ><i class="fa fa-refresh fa-3x" ></i></button> 
					</form>
			</div>
			<div id="beerInfo">${pub.drunkBeer}</div>
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
					bar.animate(${pub.relativeCapacity});  // Number from 0.0 to 1.0
				</script>
			</div>		
			<div id="center">
				<textarea id="historyTextArea" readonly>${pub.historyText}</textarea>
				<script>
					var textarea = document.getElementById('historyTextArea');
					textarea.scrollTop = textarea.scrollHeight;
				</script>
			</div>
			<div id="right" class="rightImg">
				<div id="rbar">
					<script>
						var $topLoader = $("#rbar").percentageLoader({
    						width : 200, height : 200, progress : 0, value : '0L'});

				    	var currL = ${pub.beerLiterLimit};
				    	var totalL = ${pub.maxBeerLimit};
				    
						$topLoader.setProgress(currL / totalL);
						$topLoader.setValue(currL.toString() + 'L');
   					</script>
   				</div>
			</div>
		</div>
		<div id="bottom">
					
			<div id="add">
				<form action="add" method="POST">
					<input type="submit" name="add" value="Add Visitor" class="button button-3d-primary button-rounded">
				</form>
			</div>
			
			<div id="save">
				<form action="save" method="POST">
  					<input type="submit" name="save" value="Save History" class="btn-link button button-3d-highlight button-circle">
				</form>
			</div>
			
			<div id="remove">
				<form action="remove" method="POST">
					<input type="submit" name="remove" value="Remove Visitor" class="button button-3d-caution button-rounded">		
				</form>
			</div>
			
		</div>
	</div>

</body>
</html>