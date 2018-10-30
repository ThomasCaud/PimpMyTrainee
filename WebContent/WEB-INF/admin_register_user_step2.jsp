<%@ include file="header.jsp" %>

<%@ include file="admin_navbar.jsp" %>

<div class="container">

	<div class="row ">
	
		<div class="col-12 col-sm-8 offset-sm-2">
		
			<div class="row align-items-center">
				<div class="col"><h1>Register an user</h1></div>
			</div>
			
			<hr>
			
			<div class="card">
				<div class="card-body text-center">
					<h4 class="card-title">Successful registration !</h4>
					<p class="card-text">An email has been sent to ${user.email} with the credentials to login to the website.</p>
					<a href="<c:url value = "/${applicationScope.URL_USERS}" />" class="btn btn-warning">Go back to the users list</a>
				</div>
			</div>
		
		</div>
		
	</div>

</div>

<%@ include file="footer.jsp" %>