<%@ include file="header.jsp"%>

<%@ include file="admin_navbar.jsp"%>

<div class="container">
	<div class="row">
		<div class="col-12">
		
			<div class="row align-items-center">
				<div class="col-12 col-sm-6 col-md-7 col-lg-8">
					<h1>View a quiz</h1>
				</div>
				<div class="col-12 col-sm-6 col-md-5 col-lg-4">
					<a href="<c:url value = "/${applicationScope.URL_QUIZZES}" />"
						class="btn btn-warning btn-block"> <i class="fa fa-arrow-left"></i>
						Back to the list
					</a>
				</div>
			</div>
			<hr>
		
			<ul class="nav nav-pills nav-fill justify-content-center">
				<li class="nav-item"><a class="nav-link active"
					data-toggle="tab" href="#quizContent">Quiz's content</a></li>
				<li class="nav-item"><a class="nav-link" data-toggle="tab"
					href="#records">Records</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade show active" id="quizContent">
					<hr>
					<form method="post" action="">
						<jsp:include page="templates/quiz_edit_theme.jsp">
							<jsp:param name="quiz" value="${quiz}"/>
						</jsp:include>
						<hr>
						<jsp:include page="templates/quiz_edit_content.jsp">
							<jsp:param name="quiz" value="${quiz}"/>
						</jsp:include>
						<hr>
						<div class="form-group">
							<button type="submit" name="submit" value="updateQuiz" class="btn btn-lg btn-warning btn-block">Save
								modifications</button>
						</div>
					</form>
				</div>
				<div class="tab-pane fade" id="records">
					<c:choose>
						<c:when test="${records.size() == 0}">
							No saved result.
						</c:when>
						<c:otherwise>
							<hr>
							<div class="row justify-content-center">
								<div class="col-12 col-sm-12 col-lg-5">
									<form method="get" action="" class="form-inline">
										<input type="text" class="form-control col-10"
											placeholder="Search" name="searchResults">
										<div class="col-2">
											<button type="submit" class="btn btn-primary form-control">
												<i class="fa fa-search"></i>
											</button>
										</div>
									</form>
								</div>
							</div>
							<hr>
							<c:if test="${searchResults != null && searchResults != ''}">
								<h5 class="inline-block">
									Results for the search "${searchResults}" <a
										href="<c:url value = "/${applicationScope.URL_VIEW_USER}/${user.id}"/>"
										class="btn btn-danger btn-sm"><i class="fa fa-times"></i></a>
								</h5>
							</c:if>
							<table class="table table-responsive-sm table-sm table-bordered">
								<thead class="thead-dark">
									<tr>
										<th>Firstname</th>
										<th>Lastname</th>
										<th>Score</th>
										<th>Duration</th>
										<th>Rank</th>
										<th class="text-center">Action</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${records}" var="record">
										<tr>
											<td>${record.trainee.firstname}</td>
											<td>${record.trainee.lastname}</td>
											<td>${record.score}/${record.answers.size()}</td>
											<td>${record.duration}sec.</td>
											<td>${record.ranking.scoreRank}/${record.ranking.nbRespondents}</td>
											<td class="text-center"><a
												href="<c:url value = "/${applicationScope.URL_VIEW_RECORD}/${record.id}"/>"
												class="btn btn-link"><i class="fa fa-eye"></i></a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:otherwise>
					</c:choose>
				</div>
			</div>		
		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>