<%@ include file="header.jsp"%>

<%@ include file="trainee_navbar.jsp"%>

<input type="hidden" name="quizBeginningTimestamp" value="${sessionScope.sessionQuizBeginningTimestamp}"/>

<div class="container">
	<div class="row">
		<div class="col-12 offset-sm-3 col-sm-6 col-lg-4 offset-lg-4 text-center">
			<div class="alert alert-light" role="alert">
				<strong>Time elapsed :</strong>
				<span id="quizTimer"></span>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-12 text-center">
			<form id="formAnswerQuestion" method="post">
				<h1>Question ${questionNumber}</h1>
				<h5>${question.label}</h5>
				<div class="row justify-content-center runQuiz-possibleAnswers">
					<c:forEach items="${question.possibleAnswers}" var="answer"
						varStatus="status">
						<div data-index="${status.index}" class="col-12 col-sm-8 text-center runQuiz-possibleAnswer"><span class="align-middle">${answer.label}</span></div>
  					</c:forEach>
				</div>
				<input type="hidden" name="quizAnswerIndex" value="0"/>
				<button id="btnQuizNext" type="submit" class="btn btn-lg btn-success">Next</button>
			</form>
		</div>
	</div>
</div>

<div class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" id="modalNoAnswerSelected">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Error !</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p>Please select an answer first.</p>
      </div>
    </div>
  </div>
</div>


<%@ include file="footer.jsp"%>