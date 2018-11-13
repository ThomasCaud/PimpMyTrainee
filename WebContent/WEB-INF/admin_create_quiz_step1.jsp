<%@ include file="header.jsp"%>

<%@ include file="admin_navbar.jsp"%>

<div class="container">
	<div class="row ">
		<div class="col-12">
			<form method="POST" action="">

				<div class="row align-items-center">
					<div class="col-12 col-sm-6 col-md-7 col-lg-8">
						<h1>Create a quiz</h1>
					</div>
					<div class="col-12 col-sm-6 col-md-5 col-lg-4">
						<a href="<c:url value = "/${applicationScope.URL_QUIZZES}" />"
							class="btn btn-warning btn-block"><i class="fa fa-arrow-left"></i>
							Back to the list</a>
					</div>
				</div>

				<hr>

				<div class="row">
					<div class="col-12 col-sm-8 offset-sm-2">
						
						<jsp:include page="templates/quiz_edit_theme.jsp">
							<jsp:param name="quiz" value="${quiz}"/>
						</jsp:include>
						
					</div>
				</div>

				<hr>

				<div class="row">
					<div id="quizQuestions" class="col-12 col-sm-8 offset-sm-2">
					
						<jsp:include page="templates/quiz_edit_content.jsp">
							<jsp:param name="quiz" value="${quiz}"/>
						</jsp:include>
						
					</div>
				</div>

				<hr>

				<div class="row mb-3">
					<div class="col-12 col-sm-8 offset-sm-2">
						<button type="submit" class="btn btn-success btn-block"
							name="submit" value="createQuiz">Submit</button>
					</div>
				</div>

			</form>
		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>