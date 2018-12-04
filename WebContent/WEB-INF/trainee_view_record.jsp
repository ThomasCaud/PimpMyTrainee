<%@ include file="header.jsp"%>

<%@ include file="trainee_navbar.jsp"%>

<div class="container">
	<div class="row ">
		<div class="col-12">
			<div class="row align-items-center">
				<div class="col-12 col-sm-6 col-md-7 col-lg-8">
					<h1>Your Result</h1>
				</div>
				<div class="col-12 col-sm-6 col-md-5 col-lg-4">
					<a href="<c:url value = "/${applicationScope.URL_ROOT}" />"
						class="btn btn-warning btn-block"> <i class="fa fa-arrow-left"></i>
						Back to the homepage
					</a>
				</div>
			</div>
			
			<hr>

			<div class="row">
				<div class="col-12 col-sm-8 offset-sm-2 mb-2">
					<strong>Duration :</strong> ${record.duration} sec.<br/>
					<strong>Score :</strong> ${record.score}/${fn:length(record.answers)}
				</div>
			</div>
			
			<div class="row">
				<div class="col-12 col-sm-8 offset-sm-2">
					<jsp:include page="templates/quiz_recap.jsp">
						<jsp:param name="quiz" value="${quiz}"/>
						<jsp:param name="typeOfView" value="viewQuizResult"/>
					</jsp:include>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>