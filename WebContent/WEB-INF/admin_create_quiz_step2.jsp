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
					<input action="action"
						onclick="window.history.go(-1); return false;" type="button"
						value="Back to the first step" class="btn btn-warning btn-block">
				</div>
			</div>

			<hr>

			<div class="row">
				<div class="col-12 col-sm-8 offset-sm-2">

					<div class="card border-primary mb-3">
						<div class="card-header">Recap of the content</div>
						<div class="card-body">
							<h4 class="card-title">${quiz.title}</h4>
							<h6>Theme : ${quiz.theme.label}</h6>
							<p class="card-text">
								<c:forEach items="${quiz.questions}" var="question">

									<div class="quizz-recap-question-header mt-4 px-2 py-2">
										Question ${question.position}</div>
									<div class="quizz-recap-question-label px-2 py-2">
										${question.label}</div>
									<div class="quizz-recap-answers px-2">
										Possible Answers :
										<c:forEach items="${question.possibleAnswers}" var="answer">
											<div
												class="quizz-recap-answer ${answer.isCorrect ? 'quizz-recap-answer-correct':'quizz-recap-answer-incorrect'} } mt-1 px-2 py-1">
												${answer.label}</div>
										</c:forEach>
									</div>

								</c:forEach>
							</p>
						</div>
					</div>
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