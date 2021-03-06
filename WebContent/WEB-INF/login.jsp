<%@ include file="header.jsp"%>

<div id="login-page-background" class="container-fluid">

	<div class="row">

		<div class="col-xs-12 col-lg-6 offset-lg-3 align-middle">

			<div id="login-form">

				<div id="login-form-img">
					<img src="assets/img/logo.png">
				</div>

				<h2 id="login-form-header" class="text-center">Login to Your
					Account</h2>

				<form method="post" action="">
					<fieldset>
						<div class="form-group">
							<label>Email address</label> <input type="text" name="email"
								class="form-control" placeholder="Enter email"
								value="<c:out value="${user.email}"/>">
							<div class="form-error">${form.errors['email']}</div>
						</div>
						<div class="form-group">
							<label>Password</label> <input type="password" name="password"
								class="form-control" placeholder="Enter password">
							<div class="form-error">${form.errors['password']}</div>
						</div>
						<button type="submit" class="btn btn-primary btn-block">Login</button>
					</fieldset>
				</form>

			</div>
			
			<a class="btn btn-link btn-block" href="<c:url value = "/${applicationScope.URL_DATABASE_ADMINISTRATION}"/>"> >> Go to Database Administration page </a>

		</div>

	</div>

</div>

<%@ include file="footer.jsp"%>