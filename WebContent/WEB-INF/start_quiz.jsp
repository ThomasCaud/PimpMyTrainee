<%@ include file="header.jsp"%>

<%@ include file="trainee_navbar.jsp"%>

<div class="container">
	<div class="row">
		<div class="col-12">
			<div id="startQuiz-title">
				<form method="post">
					<h1>${quiz.title}</h1>
					<h4>Theme : ${quiz.theme.label}</h4>
					You are on the verge of beginning a quiz.<br/>
					<strong>Warning : Once the quiz is started, there is no possible interruption.</strong><br/>
					If you quit or refresh the page, your progress will be saved as it is.<br/>
					This quiz contains <span class="badge badge-info">${fn:length(quiz.questions)} question<c:if test="${fn:length(quiz.questions) > 1}">s</c:if></span>
					<hr/>
					<a href="<c:url value = "/${applicationScope.URL_ROOT}" />" class="btn btn-danger btn-lg">Cancel</a>
					<button type="submit" class="btn btn-warning btn-lg">Start the quiz</button>
				</form>
			</div>
		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>