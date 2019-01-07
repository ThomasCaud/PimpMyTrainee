<%@ include file="header.jsp"%>

<div class="container">
	<div class="row ">
		<div class="col-12 col-lg-2">
			<a class="btn btn-link btn-block" href="<c:url value = "/${applicationScope.URL_LOGIN}"/>"> << Back to Login page</a>
		</div>
		<div class="col-12">
			<h3>Database structure :</h3>
			<button
				class="btn btn-danger btn_dbadmin"
				data-script="drop_all_tables" 
				data-in-progress="Dropping all tables..."
				data-done="Done!">
					Drop all tables
			</button><br>
			Output: <span id="span_drop_all_tables"></span><br/>
			
			<button
				class="btn btn-danger btn_dbadmin"
				data-script="create_all_tables" 
				data-in-progress="Creating all tables..."
				data-done="Done!">
					Create all tables
			</button><br>
			Output: <span id="span_create_all_tables"></span><br/>
			
			<hr>
			<h3>Demo datasets :</h3>
			
			<h5>Dataset 1 :</h5>
			1 admin account with the following credentials : <br/>
			- <strong>email:</strong> admin@pimpmytrainee.fr <br/>
			- <strong>password:</strong> password <br/>
			<button
				class="btn btn-info btn_dbadmin"
				data-span-id="#span_demo_dataset_1"
				data-script="demo_dataset"
				data-dataset-number="1" 
				data-in-progress="Filling with dataset 1..."
				data-done="Done!">
					Fill the database
			</button><br/>
			
			<h5>Dataset 2 :</h5>
			1 admin account and 1 trainee account <br/>
			<button
				class="btn btn-info btn_dbadmin"
				data-span-id="#span_demo_dataset_2"
				data-script="demo_dataset"
				data-dataset-number="2" 
				data-in-progress="Filling with dataset 2..."
				data-done="Done!">
					Fill the database
			</button>
			
			<hr>
			<h3>Actions :</h3>
			<button class="btn btn-primary" id="btn_dbadmin_createuser_minus"><i class="fa fa-minus"></i></button>
			<button
				class="btn btn-primary"
				id="btn_dbadmin_createuser"
				data-nbusers="5">
					Create 5 users 
			</button>
			<button class="btn btn-primary" id="btn_dbadmin_createuser_plus"><i class="fa fa-plus"></i></button><br/>
			<span id="span_dbadmin_createuser_console"></span>
		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>