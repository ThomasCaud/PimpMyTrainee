<%@ include file="header.jsp"%>

<%@ include file="admin_navbar.jsp"%>

<div class="container">
	<div class="row">
		<div class="col-12">
		
			<div class="row align-items-center">
				<div class="col-12 col-sm-6 col-md-7 col-lg-8">
					<h1>View a quiz</h1>
				</div>
				<div class="col-12 col-sm-6 col-md-5 col-lg-4">
					<a href="<c:url value = "/${applicationScope.URL_QUIZZES}" />"
						class="btn btn-warning btn-block"> <i class="fa fa-arrow-left"></i>
						Back to the list
					</a>
				</div>
			</div>
			<hr>
		
			<ul class="nav nav-pills nav-fill justify-content-center">
				<li class="nav-item"><a class="nav-link active"
					data-toggle="tab" href="#quizContent">Quiz's content</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab"
					href="#records">Records</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade show active" id="quizContent">
					<hr>
					<form method="post" action="">
						<jsp:include page="templates/quiz_edit_theme.jsp">
							<jsp:param name="quiz" value="${quiz}"/>
						</jsp:include>
						<hr>
						<jsp:include page="templates/quiz_edit_content.jsp">
							<jsp:param name="quiz" value="${quiz}"/>
						</jsp:include>
						<hr>
						<div class="form-group">
							<button type="submit" name="submit" value="updateQuiz" class="btn btn-lg btn-warning btn-block">Save
								modifications</button>
						</div>
					</form>
				</div>
				<div class="tab-pane fade" id="records"></div>		
		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>