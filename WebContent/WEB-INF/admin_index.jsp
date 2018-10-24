<%@ include file="header.jsp" %>

<%@ include file="admin_navbar.jsp" %>

<div class="container">

	<div style="margin-top:50px" class="jumbotron">
	  <h1 class="display-3">Hello, ${sessionScope.sessionUser.firstname}!</h1>
	</div>
</div>

<%@ include file="footer.jsp" %>