<%@ include file="header.jsp" %>

<%@ include file="admin_navbar.jsp" %>

<div class="container">

	<h1 style="margin-top:50px">Users Management</h1>
	
	<a href="" class="btn btn-primary"><i class="fa fa-plus"></i> Create a profile</a>
	
	<table class="table table-hover table-sm table-bordered">
	
		<thead>
			<tr>
				<th>Firstname</th>
				<th>Lastname</th>
				<th>Email</th>
				<th>Company</th>
				<th>Phone</th>
				<th>Created at</th>
				<th>Role</th>
				<th>Active user</th>
				<th>Action</th>
			</tr>
			<c:forEach items="${users}" var="user">
			<tr>
				<td>${user.firstname}</td>
				<td>${user.lastname}</td>
				<td>${user.email}</td>
				<td>${user.company}</td>
				<td>${user.phone}</td>
				<td>${user.creationDate}</td>
				<td>${user.role}</td>
				<td>${user.isActive}</td>
				<td>
					
				</td>
			</tr>
			</c:forEach>
		</thead>
	
	</table>

</div>

<label class="switch">
					  <input type="checkbox">
					  <span class="slider round"></span>
					</label>

<%@ include file="footer.jsp" %>