<%@ include file="header.jsp" %>

<%@ include file="admin_navbar.jsp" %>

<div class="container">

	<div class="row align-items-center">
	
		<div class="col-12 col-sm-8 col-md-10">
			<h1>Quizzes Management</h1>
		</div>
		<div class="col-12 col-sm-4 col-md-2">
			<a href="<c:url value = "/${applicationScope.URL_CREATE_QUIZ}"/>" class="btn btn-info"><i class="fa fa-plus"></i> Create a quiz</a>
		</div>
		
	</div>
	
	<hr>
	
	<div class="row justify-content-center">

		<div class="col-12 col-sm-12 col-lg-5">
			<form method="get" action="" class="form-inline">
				<input type="text" class="form-control col-10" placeholder="Search" name="search">
				<div class="col-2"><button type="submit" class="btn btn-primary form-control"><i class="fa fa-search"></i></button></div>
			</form>
		</div>	
	
	</div>
	
	<hr>
	
	<c:if test = "${search != null && search != ''}"><h5 class="inline-block">Results for the search "${search}" <a href="<c:url value = "/${applicationScope.URL_QUIZZES}"/>" class="btn btn-danger btn-sm"><i class="fa fa-times"></i></a></h5></c:if>
	
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
				<td><fmt:formatDate type="both" value="${quiz.creationDate}"/></td>
				<td>
					<c:choose>
						<c:when test = "${user.isActive}"><span class="badge badge-pill badge-success">Active</span></c:when>
						<c:otherwise><span class="badge badge-pill badge-danger">Deleted</span></c:otherwise>
					</c:choose>
				</td>
				<td class="text-center"><a href="<c:url value = "/${applicationScope.URL_VIEW_QUIZ}/${quiz.id}"/>" class="btn btn-link"><i class="fa fa-eye"></i></a></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<c:if test = "${search == null || search == ''}">
	<div class="row justify-content-center">
		<ul class="pagination">
			<li class="page-item <c:if test = "${paginationActive == 1}">disabled</c:if>">
		    		<a class="page-link" href="<c:url value = "/${applicationScope.URL_QUIZZES}?p="/>${paginationActive-1}">&laquo;</a>
			</li>
		  	
			<c:forEach var="i" begin="1" end="${paginationTotal}">
				<li class="page-item <c:if test = "${paginationActive == i}">active</c:if>">
					<a class="page-link" href="<c:url value = "/${applicationScope.URL_QUIZZES}?p="/>${i}">${i}</a>
		    		</li>
		  	</c:forEach>
		  	
			<li class="page-item <c:if test = "${paginationActive == paginationTotal}">disabled</c:if>">
				<a class="page-link" href="<c:url value = "/${applicationScope.URL_QUIZZES}?p="/>${paginationActive+1}">&raquo;</a>
			</li>
		</ul>
	</div>
	</c:if>
</div>

<%@ include file="footer.jsp" %>