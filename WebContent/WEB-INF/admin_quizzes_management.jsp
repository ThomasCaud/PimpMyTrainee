<%@ include file="header.jsp"%>

<%@ include file="admin_navbar.jsp"%>

<div class="container">

	<div class="row align-items-center">

		<div class="col-12 col-sm-8 col-md-10">
			<h1>Quizzes Management</h1>
		</div>
		<div class="col-12 col-sm-4 col-md-2">
			<a href="<c:url value = "/${applicationScope.URL_CREATE_QUIZ}"/>"
				class="btn btn-info"><i class="fa fa-plus"></i> Create a quiz</a>
		</div>

	</div>

	<hr>

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

	<c:if test="${search != null && search != ''}">
		<h5 class="inline-block">
			Results for the search "${search}" <a
				href="<c:url value = "/${applicationScope.URL_QUIZZES}"/>"
				class="btn btn-danger btn-sm"><i class="fa fa-times"></i></a>
		</h5>
	</c:if>

	<c:if test="${search == null || search == ''}">
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

	<table class="table table-responsive-sm table-sm table-bordered">
		<thead class="thead-dark">
			<tr>
				<th>#</th>
				<th>Quiz</th>
				<th>Theme</th>
				<th>Number of records</th>
				<th>Creation</th>
				<th>Status</th>
				<th class="text-center">Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${quizzes}" var="quiz">

				<tr>
					<th scope="row">${quiz.id}</th>
					<td>${quiz.title}</td>
					<td>${quiz.theme.label}</td>
					<td>N.A.</td>
					<td><fmt:formatDate type="both" value="${quiz.creationDate}" /></td>
					<td><c:choose>
							<c:when test="${quiz.isActive}">
								<span class="badge badge-pill badge-success">Active</span>
							</c:when>
							<c:otherwise>
								<span class="badge badge-pill badge-danger">Deleted</span>
							</c:otherwise>
						</c:choose></td>
					<td><a
						href="<c:url value = "/${applicationScope.URL_VIEW_QUIZ}/${quiz.id}"/>"
						class="btn btn-link" data-toggle="tooltip" title="" data-placement="top" data-original-title="View the quiz content"><i class="fa fa-eye"></i></a>
						<form action="" method="post" class="awesomeForm">
							<c:choose>
								<c:when test="${quiz.isActive}">
									<button type="submit" name="deactivate" value="${quiz.id}"
										class="btn btn-link" data-toggle="tooltip" title="" data-placement="top" data-original-title="Deactivate the quiz">
										<i class="fa fa-minus-square awesomeItem"></i>
									</button>
								</c:when>
								<c:otherwise>
									<button type="submit" name="activate" value="${quiz.id}"
										class="btn btn-link btn-small" data-toggle="tooltip" title="" data-placement="top" data-original-title="Reactivate the quiz">
										<i class="fa fa-plus-square awesomeItem"></i>
									</button>
								</c:otherwise>
							</c:choose>
						</form></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<c:if test="${search == null || search == ''}">
		<div class="row justify-content-center">
			<ul class="pagination">
				<li
					class="page-item <c:if test = "${paginationActive == 1}">disabled</c:if>">
					<a class="page-link"
					href="<c:url value = "/${applicationScope.URL_QUIZZES}?p="/>${paginationActive-1}<c:if test="${param.n!=null}">&n=${param.n}</c:if>">&laquo;</a>
				</li>

				<c:forEach var="i" begin="1" end="${paginationTotal}">
					<li
						class="page-item <c:if test = "${paginationActive == i}">active</c:if>">
						<a class="page-link"
						href="<c:url value = "/${applicationScope.URL_QUIZZES}?p="/>${i}<c:if test="${param.n!=null}">&n=${param.n}</c:if>">${i}</a>
					</li>
				</c:forEach>

				<li
					class="page-item <c:if test = "${paginationActive == paginationTotal}">disabled</c:if>">
					<a class="page-link"
					href="<c:url value = "/${applicationScope.URL_QUIZZES}?p="/>${paginationActive+1}<c:if test="${param.n!=null}">&n=${param.n}</c:if>">&raquo;</a>
				</li>
			</ul>
		</div>
	</c:if>
</div>

<%@ include file="footer.jsp"%>
