<%@ include file="header.jsp"%>

<%@ include file="trainee_navbar.jsp"%>

<div class="container">
	<div class="row">
		<div class="col-12">
			<div id="startQuiz-title">
				<form method="post">
					<h3>You have finished the quiz !</h3>
					You will be able to get your result on your dashboard.<br/>
					<a href="<c:url value = "/${applicationScope.URL_ROOT}" />" class="btn btn-warning btn-lg">Back to the homepage</a>
				</form>
			</div>
		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>