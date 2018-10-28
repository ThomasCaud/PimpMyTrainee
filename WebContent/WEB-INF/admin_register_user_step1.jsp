<%@ include file="header.jsp" %>

<%@ include file="admin_navbar.jsp" %>

<div class="container">
	<div class="row ">
		<div class="col-12 col-sm-8 offset-sm-2">
		
			<div class="row align-items-center">
				<div class="col-12 col-sm-6 col-md-7 col-lg-8"><h1>Register an user</h1></div>
				<div class="col-12 col-sm-6 col-md-5 col-lg-4"><a href="<c:url value = "/users" />" class="btn btn-warning btn-block"><i class="fa fa-arrow-left"></i>  Back to the list</a></div>
			</div>
			
			<hr>
			
			<form method="post" action="">
				<fieldset>
					<div class="form-group">
				      <label>Firstname</label>
				      <input type="text" class="form-control" placeholder="Enter firstname" name="firstname" value="<c:out value="${user.firstname}"/>">
				      <div class="form-error">${form.errors['firstname']}</div>
				    </div>
				    
				    <div class="form-group">
				      <label>Lastname</label>
				      <input type="text" class="form-control" placeholder="Enter lastname" name="lastname" value="<c:out value="${user.lastname}"/>">
				      <div class="form-error">${form.errors['lastname']}</div>
				    </div>
				    
				    <div class="form-group">
				      <label>Email</label>
				      <input type="text" class="form-control" placeholder="Enter email" name="email" value="<c:out value="${user.email}"/>">
				      <div class="form-error">${form.errors['email']}</div>
				    </div>
				    
				    <div class="form-group">
				      <label>Company</label>
				      <input type=text class="form-control" placeholder="Enter company" name="company" value="<c:out value="${user.company}"/>">
				      <div class="form-error">${form.errors['company']}</div>
				    </div>
				    
				    <div class="form-group">
				      <label>Phone</label>
				      <input type=text class="form-control"  placeholder="Enter phone" name="phone" value="<c:out value="${user.phone}"/>">
				      <div class="form-error">${form.errors['phone']}</div>
				    </div>
				    
				    <div class="form-group">
				      <button type="submit" class="btn btn-lg btn-primary btn-block">Register</button>
				    </div>
				    
				
				</fieldset>
			</form>
		
		</div>
		
	</div>

</div>

<%@ include file="footer.jsp" %>