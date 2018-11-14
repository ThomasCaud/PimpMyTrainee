<%@ include file="header.jsp"%>

<%@ include file="admin_navbar.jsp"%>

<div class="container">
	<div class="row ">
		<div class="col-12">

			<div class="row align-items-center">
				<div class="col-12 col-sm-6 col-md-7 col-lg-8">
					<h1>Confirm the quiz creation</h1>
				</div>

				<div class="col-12 col-sm-6 col-md-5 col-lg-4">
					<input formaction="action"
						onclick="window.history.go(-1); return false;" type="button"
						value="Back to the first step" class="btn btn-warning btn-block">
				</div>
			</div>

			<hr>

			<div class="row">
				<div class="col-12 col-sm-8 offset-sm-2">

					<jsp:include page="templates/quiz_recap.jsp">
						<jsp:param name="quiz" value="${quiz}"/>
					</jsp:include>
					
				</div>
			</div>

			<hr>

			<div class="row mb-3">
				<div class="col-12 col-sm-8 offset-sm-2">
					<form method="POST" action="">
						<button type="submit" class="btn btn-success btn-block"
							name="submit" value="confirmQuiz">Confirm</button>
					</form>
				</div>
			</div>

		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>