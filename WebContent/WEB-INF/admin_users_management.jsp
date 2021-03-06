<%@ include file="header.jsp"%>

<%@ include file="admin_navbar.jsp"%>

<div class="container">

	<div class="row align-items-center">

		<div class="col-12 col-sm-8 col-md-10">
			<h1>Users Management</h1>
		</div>
		<div class="col-12 col-sm-4 col-md-2">
			<a href="<c:url value = "/${applicationScope.URL_REGISTER_USER}"/>"
				class="btn btn-info">Register a user <i class="fa fa-user-plus"></i></a>
		</div>

	</div>

	<hr>
	
	<c:if test="${fn:length(users) != 0 || search != null}">
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

	

	<c:if test="${search != null && search != ''}">
		<h5 class="inline-block">
			Results for the search "${search}" <a
				href="<c:url value = "/${applicationScope.URL_USERS}"/>"
				class="btn btn-danger btn-sm"><i class="fa fa-times"></i></a>
		</h5>
	</c:if>

	<c:if test="${(search == null || search == '') && fn:length(users) != 0}">
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
		<c:when test="${fn:length(users) != 0}">
			<table class="table table-responsive-sm table-sm table-bordered">
				<thead class="thead-dark">
					<tr>
						<th>#</th>
						<th>Firstname</th>
						<th>Lastname</th>
						<th>Email</th>
						<th>Company</th>
						<th>Phone</th>
						<th>Creation</th>
						<th>Role</th>
						<th>Status</th>
						<th class="text-center"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${users}" var="user">
		
						<tr>
							<th scope="row">${user.id}</th>
							<td>${user.firstname}</td>
							<td>${user.lastname}</td>
							<td>${user.email}</td>
							<td>${user.company}</td>
							<td>${user.phone}</td>
							<td><fmt:formatDate type="both" value="${user.creationDate}" /></td>
							<td><c:choose>
									<c:when test="${user.role == 'ADMIN'}">
										<span class="badge badge-pill badge-warning">${user.role.label}</span>
									</c:when>
									<c:otherwise>
										<span class="badge badge-pill badge-info">${user.role.label}</span>
									</c:otherwise>
								</c:choose></td>
							<td><c:choose>
									<c:when test="${user.isActive}">
										<span class="badge badge-pill badge-success">Active</span>
									</c:when>
									<c:otherwise>
										<span class="badge badge-pill badge-danger">Deleted</span>
									</c:otherwise>
								</c:choose></td>
							<td><a
								href="<c:url value = "/${applicationScope.URL_VIEW_USER}/${user.id}"/>"
								class="btn btn-link" data-toggle="tooltip" title="" data-placement="top" data-original-title="Edit the user's profile"><i
									class="fa fa-edit"></i></a>
								<form action="" method="post" class="awesomeForm">
									<c:choose>
										<c:when test="${user.isActive}">
											<button type="submit" name="deactivate" value="${user.id}" class="btn btn-link" data-toggle="tooltip" title="" data-placement="top" data-original-title="Deactivate this user">
												<i class="fa fa-minus-square"></i>
											</button>
										</c:when>
										<c:otherwise>
											<button type="submit" name="activate" value="${user.id}" class="btn btn-link" data-toggle="tooltip" title="" data-placement="top" data-original-title="Reactivate this user">
												<i class="fa fa-plus-square"></i>
											</button>
										</c:otherwise>
									</c:choose>
								</form></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</c:when>
		<c:otherwise>
				There is no user for the moment.
		</c:otherwise>
	</c:choose>
	
	<c:if test="${(search == null || search == '') && fn:length(users) != 0 && paginationTotal > 1}">
		<div class="row justify-content-center">
			<ul class="pagination">
				<li
					class="page-item <c:if test = "${paginationActive == 1}">disabled</c:if>">
					<a class="page-link"
					href="<c:url value = "/${applicationScope.URL_USERS}?p="/>${paginationActive-1}<c:if test="${param.n!=null}">&n=${param.n}</c:if>">&laquo;</a>
				</li>

				<c:forEach var="i" begin="${paginationBegin}" end="${paginationEnd}">
					<li
						class="page-item <c:if test = "${paginationActive == i}">active</c:if>">
						<a class="page-link"
						href="<c:url value = "/${applicationScope.URL_USERS}?p="/>${i}<c:if test="${param.n!=null}">&n=${param.n}</c:if>">${i}</a>
					</li>
				</c:forEach>

				<li
					class="page-item <c:if test = "${paginationActive == paginationTotal}">disabled</c:if>">
					<a class="page-link"
					href="<c:url value = "/${applicationScope.URL_USERS}?p="/>${paginationActive+1}<c:if test="${param.n!=null}">&n=${param.n}</c:if>">&raquo;</a>
				</li>
			</ul>
		</div>
	</c:if>
</div>

<%@ include file="footer.jsp"%>
