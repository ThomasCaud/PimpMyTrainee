<%@ include file="header.jsp"%>

<%@ include file="trainee_navbar.jsp"%>

<div class="container">
	<div>
		<div class="row align-items-center">
			<div class="col-12 col-sm-8 col-md-10">
				<h1>Quizzes availables</h1>
			</div>
		</div>
		<hr>

		<c:if test="${fn:length(quizzes) != 0}">
			<div class="row justify-content-center">
				<div class="col-12 col-sm-12 col-lg-5">
					<form method="get" action="" class="form-inline">
						<input type="text" class="form-control col-10" placeholder="Search"
							name="search">
						<div class="col-2">
							<button type="submit" class="btn btn-primary form-control">
								<i class="fa fa-search"></i>
							</button>
						</div>
					</form>
				</div>
			</div>
			<hr>
		</c:if>
			
		<c:if test="${search != null && search != '' && fn:length(quizzes) != 0}">
			<h5 class="inline-block">
				Results for the search "${search}" <a href="<c:url value = "/"/>"
					class="btn btn-danger btn-sm"><i class="fa fa-times"></i></a>
			</h5>
		</c:if>
		<c:if test="${(search == null || search == '') && fn:length(quizzes) != 0}">
			<div class="row mb-2">
				<div class="col-12 col-sm-12 col-lg-5">

					Items per page <select id="nbItemsPerPage" name="nbItemsPerPage">
						<option value="1" <c:if test="${param.n == 1}">selected</c:if>>1</option>
						<option value="2" <c:if test="${param.n == 2}">selected</c:if>>2</option>
						<option value="3"
							<c:if test="${param.n == null || param.n == 3}">selected</c:if>>3</option>
						<option value="4" <c:if test="${param.n == 4}">selected</c:if>>4</option>
						<option value="5" <c:if test="${param.n == 5}">selected</c:if>>5</option>
						<option value="10" <c:if test="${param.n == 10}">selected</c:if>>10</option>
						<option value="20" <c:if test="${param.n == 20}">selected</c:if>>20</option>
					</select>
				</div>
			</div>
		</c:if>

		<c:choose>
			<c:when test="${fn:length(quizzes) != 0}">
				<table class="table table-responsive-sm table-sm table-bordered">
					<thead class="thead-dark">
						<tr>
							<th>Quiz</th>
							<th class="text-center">Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${quizzes}" var="quiz">
		
							<tr>
								<td>${quiz.title}</td>
								<td class="text-center"><a
									href="<c:url value = "/${applicationScope.URL_START_QUIZ}/${quiz.id}"/>"
									class="btn btn-link"><i class="fa fa-play"></i></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				There is no available quiz for the moment.
			</c:otherwise>
		</c:choose>

		<c:if test="${(search == null || search == '') && fn:length(quizzes) != 0 && paginationTotal > 1}">
			<div class="row justify-content-center">
				<ul class="pagination">
					<li
						class="page-item <c:if test = "${paginationActive == 1}">disabled</c:if>">
						<a class="page-link"
						href="<c:url value = "/"/>${paginationActive-1}<c:if test="${param.n != null}">&n=${param.n}</c:if>">&laquo;</a>
					</li>

					<c:forEach var="i" begin="1" end="${paginationTotal}">
						<li
							class="page-item <c:if test = "${paginationActive == i}">active</c:if>">
							<a class="page-link"
							href="<c:url value = "/?p="/>${i}<c:if test="${param.n != null}">&n=${param.n}</c:if>">${i}</a>
						</li>
					</c:forEach>

					<li
						class="page-item <c:if test = "${paginationActive == paginationTotal}">disabled</c:if>">
						<a class="page-link"
						href="<c:url value = "/?p="/>${paginationActive+1}<c:if test="${param.n != null}">&n=${param.n}</c:if>">&raquo;</a>
					</li>
				</ul>
			</div>
		</c:if>
	</div>
	<div>
		<div class="row align-items-center" style="margin-top:50px">
			<div class="col-12 col-sm-8 col-md-10">
				<h1>Results</h1>
			</div>
		</div>
		<hr>
		<c:choose>
			<c:when test="${fn:length(records) != 0}">
				<table class="table table-responsive-sm table-sm table-bordered">
					<thead class="thead-dark">
						<tr>
							<th>Quiz</th>
							<th>Theme</th>
							<th>Score</th>
							<th>Duration</th>
							<th class="text-center">Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${records}" var="record">
							<tr>
								<td>${record.quiz.title}</td>
								<td>${record.quiz.theme.label}</td>
								<td>${record.score}/${record.answers.size()}</td>
								<td>${record.duration}sec.</td>
								<td class="text-center"><a
									href="<c:url value = "/${applicationScope.URL_VIEW_RECORD}/${record.id}"/>"
									class="btn btn-link"><i class="fa fa-eye"></i></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				You haven't answered to any quiz yet.
			</c:otherwise>
		</c:choose>
	</div>
</div>

<%@ include file="footer.jsp"%>