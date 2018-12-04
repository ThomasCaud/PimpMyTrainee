<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="card border-primary mb-3">
	<div class="card-header">Recap of the content</div>
	<div class="card-body">
		<h4 class="card-title">${quiz.title}</h4>
		<h6>Theme : ${quiz.theme.label}</h6>
		<p class="card-text">
			<c:forEach items="${quiz.questions}" var="question" varStatus="statusQ">

				<div class="quizz-recap-question-header mt-4 px-2 py-2">
					Question ${question.position}</div>
				<div class="quizz-recap-question-label px-2 py-2">
					${question.label}</div>
				<c:choose>
					<c:when test="${param.typeOfView == 'quizRecap'}">
						<div class="quizz-recap-answers px-2">
							Possible Answers :
							<c:forEach items="${question.possibleAnswers}" var="answer">
								<div
									class="quizz-recap-answer ${answer.isCorrect ? 'quizz-recap-answer-correct':'quizz-recap-answer-incorrect'} } mt-1 px-2 py-1">
									${answer.label}</div>
							</c:forEach>
						</div>
					</c:when>
					<c:when test="${param.typeOfView == 'viewQuizResult'}">
						<div class="quizz-recap-answers px-2">
							Answers :
							<c:forEach items="${question.possibleAnswers}" var="answer">
								<c:choose>
									<c:when test="${answer.id == record.answers[statusQ.index].id}">
										<div class="quizz-recap-answer ${answer.isCorrect ? 'quizz-recap-answer-correct':'quizz-recap-answer-incorrect'} mt-1 px-2 py-1">
											${answer.label}
										</div>
									</c:when>
									<c:otherwise>
										<div class="quizz-recap-answer ${answer.isCorrect ? 'quizz-recap-answer-correct':''} mt-1 px-2 py-1">
											${answer.label}
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</div>
					</c:when>
				</c:choose>
			</c:forEach>
		</p>
	</div>
</div>