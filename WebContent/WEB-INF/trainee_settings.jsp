<%@ include file="header.jsp"%>

<%@ include file="trainee_navbar.jsp"%>

<div class="container">
	Connected as ${sessionScope.sessionUser.firstname}
	${sessionScope.sessionUser.lastname }

	<div class="row ">
		<div class="col-12">
			<div class="row align-items-center">
				<div class="col-12 col-sm-6 col-md-7 col-lg-8">
					<h1>Update your profile</h1>
				</div>
			</div>
			<hr>
		</div>

		<div class="col-12 col-sm-8 offset-sm-2">
			<form method="post" action="">
				<fieldset>
					<div class="form-group">
						<label>New password</label> <input type="password" class="form-control"
							placeholder="Enter new password" name="newPassword">
						<div class="form-error">${form.errors['newPassword']}</div>
					</div>
					<div class="form-group">
						<label>New password (confirmation)</label> <input type="password" class="form-control"
							placeholder="Enter new password" name="newPasswordConfirmation">
						<div class="form-error">${form.errors['newPasswordConfirmation']}</div>
					</div>
					<div class="form-group">
						<label>Phone</label> <input type="text" class="form-control"
							placeholder="Enter phone" name="phone"
							value="<c:out value="${user.phone}"/>">
						<div class="form-error">${form.errors['phone']}</div>
					</div>
					<div class="form-group">
						<button type="submit" class="btn btn-lg btn-primary btn-block">Update</button>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>