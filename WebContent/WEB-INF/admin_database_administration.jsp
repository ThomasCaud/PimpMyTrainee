<%@ include file="header.jsp"%>

<div class="container">
	<div class="row ">
		<div class="col-12 col-lg-2">
			<a class="btn btn-link btn-block" href="<c:url value = "/${applicationScope.URL_LOGIN}"/>"> << Back to Login page</a>
		</div>
		<div class="col-12">
			<button class="btn btn-danger" id="btn_dbadmin_drop_all_tables">Drop all tables</button><br>
			Output: <span id="span_dbadmin_drop_all_tables"></span><br/>
			
			<button class="btn btn-success" id="btn_dbadmin_create_all_tables">Create all tables</button><br>
			Output: <span id="span_dbadmin_create_all_tables"></span><br/>
			
			<button class="btn btn-success" id="btn_dbadmin_create_admin_account">Create an admin account</button><br>
			Output: <span id="span_dbadmin_create_admin_account"></span><br/>
		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>