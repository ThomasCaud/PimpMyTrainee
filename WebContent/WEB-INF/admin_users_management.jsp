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
				<td>
					<label class="switch">
						<c:choose>
							<c:when test="${user.isActive}"><input type="checkbox" checked></c:when>
							<c:otherwise><input type="checkbox"></c:otherwise>
						</c:choose>
					  <span class="slider round"></span>
					</label>
				</td>
				<td>
					
				</td>
			</tr>
			</c:forEach>
		</thead>
	
	</table>

</div>



<%@ include file="footer.jsp" %>