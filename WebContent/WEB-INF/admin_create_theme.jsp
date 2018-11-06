<%@ include file="header.jsp"%>

<%@ include file="admin_navbar.jsp"%>

<div class="container">
	<div class="row ">
		<div class="col-12">

			<div class="row align-items-center">
				<div class="col-12 col-sm-6 col-md-7 col-lg-8">
					<h1>Create a theme</h1>
				</div>
				<div class="col-12 col-sm-6 col-md-5 col-lg-4">
					<a href="<c:url value = "/${applicationScope.URL_CREATE_QUIZ}" />"
						class="btn btn-warning btn-block"><i class="fa fa-arrow-left"></i>
						Back to the quiz</a>
				</div>
			</div>

			<hr>
		</div>

		<div class="col-12 col-sm-8 offset-sm-2">

			<form method="post" action="">
				<fieldset>
					<div class="form-group">
						<label>Theme Label</label> <input type="text" class="form-control"
							placeholder="Enter theme label" name="label"
							value="<c:out value="${theme.label}"/>">
						<div class="form-error">${form.errors['label']}</div>
					</div>

					<div class="form-group">
						<button type="submit" class="btn btn-lg btn-primary btn-block">Create</button>
					</div>


				</fieldset>
			</form>

		</div>

	</div>

</div>

<%@ include file="footer.jsp"%>