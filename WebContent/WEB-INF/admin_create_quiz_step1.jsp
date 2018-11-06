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
						<fieldset>
							<div class="form-group">
								<h5>Theme</h5>
								<div class="row no-gutters">
									<div class="col-12 col-sm-8">
										<select class="form-control" name="theme">
											<c:forEach items="${themes}" var="theme">
												<option value="${theme.id}"
													<c:if test="${theme.id == quiz.theme.id}">selected</c:if>>${theme.label}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-12 mt-2 mt-sm-0 col-sm-3 offset-sm-1">
										<a
											href="<c:url value = "/${applicationScope.URL_CREATE_THEME}"/>"
											class="btn btn-info btn-block"><i class="fa fa-plus"></i>
											New theme</a>
									</div>
									<div class="form-error">${form.errors['theme']}</div>
								</div>
							</div>
							<div class="form-group">
								<h5>Title</h5>
								<input type="text" name="title" class="form-control"
									placeholder="Enter quiz title"
									value="<c:out value="${quiz.title}"/>">
								<div class="form-error">${form.errors['title']}</div>
							</div>
						</fieldset>
					</div>
				</div>

				<hr>

				<div class="row">
					<div id="quizQuestions" class="col-12 col-sm-8 offset-sm-2">
						<c:forEach items="${quiz.questions}" var="question"
							varStatus="statusQ">
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