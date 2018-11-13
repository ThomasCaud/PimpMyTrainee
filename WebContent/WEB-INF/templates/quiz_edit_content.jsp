<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<input type="hidden" value="${quiz.id}" name="quiz_id"/>
<c:forEach items="${quiz.questions}" var="question"
	varStatus="statusQ">
	<input type="hidden" value="${question.id}" name="question_${statusQ.count}_id"/>
	<hr>
	<div class="row">
		<div class="col-12">
			<h3>
				Question ${statusQ.count}
				<button <c:if test="${statusQ.index == 0}">disabled</c:if>
					type="submit" class="btn btn-info btn-sm" name="submit"
					value="moveUpQuestion_${statusQ.count}" data-toggle="tooltip"
					title="" data-placement="top"
					data-original-title="Move up this question">
					<i class="fa fa-caret-up"></i>
				</button>
				<button
					<c:if test="${fn:length(quiz.questions) eq statusQ.count}">disabled</c:if>
					type="submit" class="btn btn-info btn-sm" name="submit"
					value="moveDownQuestion_${statusQ.count}"
					data-toggle="tooltip" title="" data-placement="top"
					data-original-title="Move down this question">
					<i class="fa fa-caret-down"></i>
				</button>
				<button type="submit" class="btn btn-danger btn-sm"
					name="submit" value="deleteQuestion_${statusQ.count}"
					data-toggle="tooltip" title="" data-placement="top"
					data-original-title="Delete this question">
					<i class="fa fa-times"></i>
				</button>
			</h3>
		</div>
		<div class="col-12">
			<input type="text" class="form-control form-question-label"
				name="question_${statusQ.count}_label"
				value="${question.label}" placeholder="Enter question label">
			<div class="form-error">
				<c:set var="key" value="question_${statusQ.count}_label"></c:set>
				${form.errors[key]}
			</div>
		</div>
		<div class="col-12 mt-3">
			<h5>
				Answers
				<button type="submit" class="btn btn-info btn-sm"
					name="submit" value="newAnswer_${statusQ.count}"
					data-toggle="tooltip" title="" data-placement="top"
					data-original-title="Add a possible answer">
					<i class="fa fa-plus"></i>
				</button>
			</h5>
		</div>
		<div class="col-12">
			<div class="form-error">
				<c:set var="key" value="question_${statusQ.count}_answers"></c:set>
				${form.errors[key]}
			</div>
			<c:forEach items="${question.possibleAnswers}"
				var="possibleAnswer" varStatus="statusP">
				<input type="hidden" value="${possibleAnswer.id}" name="question_${statusQ.count}_possibleAnswer_${statusP.count}_id"/>
				<div
					class="row mt-1 py-1 possibleAnswerRow align-items-center">
					<div class="col-2 col-sm-1 align-self-center text-center">
						<input type="radio" name="question_${statusQ.count}_radio"
							value="${statusP.count}"
							<c:if test="${possibleAnswer.isCorrect}">checked</c:if>>
					</div>
					<div class="col-8 col-sm-9">
						<input type="text"
							class="form-control form-input-transparent form-answer-label"
							name="question_${statusQ.count}_possibleAnswer_${statusP.count}_label"
							value="${possibleAnswer.label}">
						<div class="form-error">
							<c:set var="key"
								value="question_${statusQ.count}_answer_${statusP.count}"></c:set>
							${form.errors[key]}
						</div>
					</div>
					<div class="col-2">
						<button <c:if test="${statusP.index == 0}">disabled</c:if>
							type="submit" class="btn btn-info btn-sm" name="submit"
							value="moveUpAnswer_${statusP.count}_fromQuestion_${statusQ.count}"
							data-toggle="tooltip" title="" data-placement="top"
							data-original-title="Move up this question">
							<i class="fa fa-caret-up"></i>
						</button>
						<button
							<c:if test="${fn:length(question.possibleAnswers) eq statusP.count}">disabled</c:if>
							type="submit" class="btn btn-info btn-sm" name="submit"
							value="moveDownAnswer_${statusP.count}_fromQuestion_${statusQ.count}"
							data-toggle="tooltip" title="" data-placement="top"
							data-original-title="Move down this question">
							<i class="fa fa-caret-down"></i>
						</button>
						<button class="btn btn-danger btn-sm" name="submit"
							value="deleteAnswer_${statusP.count}_fromQuestion_${statusQ.count}">
							<i class="fa fa-times"></i>
						</button>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</c:forEach>

<div class="form-error">${form.errors['questions']}</div>

<button type="submit" class="btn btn-info btn-block mt-3"
	name="submit" value="newQuestion">
	<i class="fa fa-plus"></i> New question
</button>