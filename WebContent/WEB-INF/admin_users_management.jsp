<%@ include file="header.jsp" %>

<%@ include file="admin_navbar.jsp" %>

<div class="container">

	<div class="row align-items-center">
	
		<div class="col-12 col-sm-8 col-md-10">
			<h1>Users Management</h1>
		</div>
		<div class="col-12 col-sm-4 col-md-2">
			<a href="<c:url value = "/registerUser"/>" class="btn btn-info"><i class="fa fa-plus"></i> Register an user</a>
		</div>
		
	</div>
	
	<hr>
	
	<div class="row">
	
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
				<th>Active user</th>
				<th class="text-center">Action</th>
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
				<td>${user.creationDate}</td>
				<td>${user.role}</td>
				<td>${user.isActive}</td>
				<td class="text-center"><a href="<c:url value = "/viewUser/${user.id}"/>" class="btn btn-link"><i class="fa fa-eye"></i></a></td>
			</tr>
			</c:forEach>
		</tbody>
		
	</table>
	
	</div>

</div>



<%@ include file="footer.jsp" %>