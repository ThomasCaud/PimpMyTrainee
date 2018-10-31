<%@ include file="header.jsp" %>

<%@ include file="admin_navbar.jsp" %>

<div class="container">
	<div class="row ">
		<div class="col-12">
		<form method="POST" action="">
		
			<div class="row align-items-center">
				<div class="col-12 col-sm-6 col-md-7 col-lg-8"><h1>Create a quiz</h1></div>
				<div class="col-12 col-sm-6 col-md-5 col-lg-4"><a href="<c:url value = "/${applicationScope.URL_QUIZZES}" />" class="btn btn-warning btn-block"><i class="fa fa-arrow-left"></i>  Back to the list</a></div>
			</div>
			
			<hr>
			
			<h3>Quiz Configuration</h3>
			
			<div class="row">
				<div class="col-12 col-sm-8 offset-sm-2">
					<fieldset>
						<div class="form-group">
					      	<label>Title</label>
					      	<input type="text" name="title" class="form-control" placeholder="Enter quiz title" value="<c:out value="${quiz.title}"/>">
					      	<div class="form-error">${form.errors['title']}</div>
					    </div>
					    <div class="form-group">
					      	<label>Theme</label>
					      	<div class="row no-gutters">
					      		<div class="col-12 col-sm-8">
							      <select class="form-control" name="theme">
							      	<c:forEach items="${themes}" var="theme">
							        <option value="${theme.id}" <c:if test="${theme.id == quiz.theme.id}">selected</c:if>>${theme.label}</option>
							        </c:forEach>
							      </select>
								</div>
								<div class="col-12 mt-2 mt-sm-0 col-sm-3 offset-sm-1">
							    	<a href="<c:url value = "/${applicationScope.URL_CREATE_THEME}"/>" class="btn btn-info btn-block"><i class="fa fa-plus"></i> New theme</a>
								</div>
								<div class="form-error">${form.errors['theme']}</div>
					      	</div>  	
					    </div>
					</fieldset>
				</div>
			</div>
			
			<hr>
			
			<h3>Questions</h3>
			
			<div class="row">
				<div id="quizQuestions" class="col-12 col-sm-8 offset-sm-2">
					<c:forEach items="${quiz.questions}" var="question" varStatus="statusQ">
					<hr>
					<div class="row">
						<div class="col-12">
							<h5>Question ${statusQ.count}</h5>
						</div>	
						<div class="col-12">
							<input type="text" class="form-control" name="question_${statusQ.count}_label" value="${question.label}">
						</div>
						<div class="col-12 mt-3">
							<h5>Answers <button type="submit" class="btn btn-info btn-sm" name="submit" value="newAnswer_${statusQ.count}"><i class="fa fa-plus"></i></button></h5>
						</div>
						<div class="col-12">
							<c:forEach items="${question.possibleAnswers}" var="possibleAnswer" varStatus="statusP">
							<div class="row mt-1 py-1 possibleAnswerRow">
						        	<div class="col-2 col-sm-1 align-self-center text-center">
						        		<input type="radio" name="question_${statusQ.count}_radio" value="${statusP.count}" <c:if test="${possibleAnswer.isCorrect}">checked</c:if>>
						        	</div>
						          	<div class="col-9 col-sm-10">
						          		<input type="text" class="form-control form-input-transparent" name="question_${statusQ.count}_possibleAnswer_${statusP.count}_label" value="${possibleAnswer.label}">
						          	</div>
						          	<div class="col-1 pl-0">
						          		<button class="btn btn-danger form-control"><i class="fa fa-times"></i></button>
						          	</div>
							</div>
							</c:forEach>
						</div>
					</div>
					</c:forEach>
					
					<button type="submit" class="btn btn-info btn-block mt-3" name="submit" value="newQuestion"><i class="fa fa-plus"></i> New question</button>
				</div>
			</div>
			
			<hr>
			
			<div class="row mb-3">
				<div class="col-12 col-sm-8 offset-sm-2">
					<button type="submit" class="btn btn-success btn-block" name="submit" value="createQuiz">Submit</button>
				</div>
			</div>
		
		</form>
		</div>
	</div>
</div>

<%@ include file="footer.jsp" %>