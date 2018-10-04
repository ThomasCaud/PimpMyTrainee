<%@ include file="header.jsp" %>

<div id="login-page-background" class="container-fluid">
	
		<div class="row">
		
			<div class="col-xs-12 col-lg-6 offset-lg-3 align-middle">
			
				<div id="login-form">
				
					<div id="login-form-img">
						<img src="assets/img/logo.png">
					</div>
					
					<h2 id="login-form-header" class="text-center">Login to Your Account</h2>
					
					<div class="alert alert-danger">
						<span class="alert-heading"><strong>Error :</strong></span><br>
						- Bad password for this email
					</div>
					
					<form method="post" action="login">
						<fieldset>
							<div class="form-group">
						      <label>Email address</label>
						      <input type="text" name="email" class="form-control" placeholder="Enter email">
						    </div>
						    <div class="form-group">
						      <label>Password</label>
						      <input type="password" name="password" class="form-control" placeholder="Password">
						    </div>
						     <button type="submit" class="btn btn-primary btn-block">Login</button>
						</fieldset>
					</form>
					
				</div>
			
			</div>
		
		</div>

</div>

<%@ include file="footer.jsp" %>