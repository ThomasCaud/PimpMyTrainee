<%@ include file="header.jsp"%>

<%@ include file="trainee_navbar.jsp"%>

<input type="hidden" name="quizBeginningTimestamp" value="${sessionScope.sessionQuizBeginningTimestamp}"/>
${sessionScope.sessionQuizBeginningTimestamp}

<div class="container">
	<div class="row">
		<div class="col-12 text-center">
			<form method="post">
				<h1>Question ${questionNumber}</h1>
				<h5>${question.label}</h5>
				<div class="row justify-content-center runQuiz-possibleAnswers">
					<c:forEach items="${question.possibleAnswers}" var="answer"
						varStatus="status">
						<div class="col-12 col-sm-8 text-center runQuiz-possibleAnswer"><span class="align-middle">${answer.label}</span></div>
  					</c:forEach>
				</div>
				<button type="submit" class="btn btn-lg btn-success">Next</button>
			</form>
		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>