<%@ include file="header.jsp"%>
<%@ include file="navbar.jsp"%>

<div class="container">
	<div class="row">
		<div class="col-12">
			<h1>My profile</h1>
			<hr/>
		</div>
		
		<div class="col-12 mb-5 mb-sm-0 col-sm-6">
			
			<h5>Personal Information :</h5>
			
			<div class="row">
				<div class="col-6 font-weight-bold">Firstname :</div>
				<div class="col-6">${user.firstname}</div>
			</div>
			
			<div class="row">
				<div class="col-6 font-weight-bold">Lastname :</div>
				<div class="col-6">${user.lastname}</div>
			</div>
			
			<div class="row">
				<div class="col-6 font-weight-bold">Email :</div>
				<div class="col-6">${user.email}</div>
			</div>
			
			<div class="row">
				<div class="col-6 font-weight-bold">Company :</div>
				<div class="col-6">${user.company}</div>
			</div>
			
			<div class="row">
				<div class="col-6 font-weight-bold">Phone :</div>
				<div class="col-6">${user.phone}</div>
			</div>
			
			<div class="row">
				<div class="col-6 font-weight-bold">Date of creation :</div>
				<div class="col-6"><fmt:formatDate type="both" value="${user.creationDate}" /></div>
			</div>
			
			<div class="row">
				<div class="col-6 font-weight-bold">Role :</div>
				<div class="col-6">${user.role}</div>
			</div>

		</div>
		
		<div class="col-12 col-sm-6">
			
			<h5>Change password :</h5>
			
			<c:if test="${form.successMessage != null && form.successMessage != ''}">
				<div class="form-success-message mb-1">${form.successMessage}</div>
			</c:if>
			
			<form method="post" action="">
				<fieldset>
					<div class="form-group">
						<input type="password" name="currentPassword" class="form-control" placeholder="Current password"/>
						<div class="form-error">${form.errors['currentPassword']}</div>
					</div>
					<div class="form-group">
						<input type="password" name="newPassword" class="form-control" placeholder="New password"/>
						<div class="form-error">${form.errors['newPassword']}</div>
					</div>
					<div class="form-group">
						<input type="password" name="confirmPassword" class="form-control" placeholder="Confirm password"/>
						<div class="form-error">${form.errors['confirmPassword']}</div>
					</div>
					<div class="form-group">
						<button type="submit" class="btn btn-lg btn-primary btn-block">Change password</button>
					</div>
				</fieldset>
			</form>
			
		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>