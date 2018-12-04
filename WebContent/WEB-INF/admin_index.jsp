<%@ include file="header.jsp"%>

<%@ include file="admin_navbar.jsp"%>

<div class="container">

	<div style="margin-top: 50px" class="jumbotron">
		<h3 class="display-3">Hello,
			${sessionScope.sessionUser.firstname}!</h3>
	</div>
	
	<div class="row">
		<div class="col-12 col-sm-3 px-4">
			<div class="metric">
				<h4 class="title"><i class="fa fa-user-plus"></i> Number of active users</h4>
				<span class="value">${sessionScope.nbActiveUser}</span>
			</div>
		</div>
		<div class="col-12 col-sm-3 px-4">
			<div class="metric">
				<h4 class="title"><i class="fa fa-user-times"></i> Number of inactive users</h4>
				<span class="value">${sessionScope.nbInactiveUser}</span>
			</div>
		</div>
		<div class="col-12 col-sm-3 px-4">
			<div class="metric">
				<h4 class="title"><i class="fa fa-question-circle"></i> Number of created quizzes</h4>
				<span class="value">${sessionScope.nbCreatedQuizzes}</span>
			</div>
		</div>
		<div class="col-12 col-sm-3 px-4">
			<div class="metric">
				<h4 class="title"><i class="fa fa-check-circle"></i> Number of answers</h4>
				<span class="value">${sessionScope.nbRecords}</span>
			</div>
		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>