<%@ include file="header.jsp" %>

<%@ include file="admin_navbar.jsp" %>

<div class="container">
	<div class="row ">
		<div class="col-12">
		
			<div class="row align-items-center">
				<div class="col-12 col-sm-6 col-md-7 col-lg-8"><h1>Create a quiz</h1></div>
				<div class="col-12 col-sm-6 col-md-5 col-lg-4"><a href="<c:url value = "/${applicationScope.URL_QUIZZES}" />" class="btn btn-warning btn-block"><i class="fa fa-arrow-left"></i>  Back to the list</a></div>
			</div>
			
			<hr>
		
		</div>
		
		<div class="col-12 col-sm-8 offset-sm-2">
		
			<form method="post" action="">
				<fieldset>
					<div class="form-group">
				      <label>Title</label>
				      <input type="text" class="form-control" placeholder="Enter title" name="title" value="<c:out value="${quiz.title}"/>">
				      <div class="form-error">${form.errors['title']}</div>
				    </div>
				</fieldset>
			</form>
		
		</div>
	</div>	

</div>

<%@ include file="footer.jsp" %>