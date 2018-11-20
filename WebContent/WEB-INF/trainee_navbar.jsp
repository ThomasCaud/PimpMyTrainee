<nav class="navbar navbar-expand-lg navbar-dark bg-primary"
	style="margin-bottom: 50px">
	<a class="navbar-brand"
		href="<c:url value = "/${applicationScope.URL_ROOT}"/>">PimpMyTrainee</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarColor01" aria-controls="navbarColor01"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarColor01">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item"><a class="nav-link"
				href="<c:url value = "/${applicationScope.URL_QUIZZES}" />">See quizzes</a></li>
			<li class="nav-item"><a class="nav-link"
				href="<c:url value = "/${applicationScope.URL_RESULTS}" />">See results</a></li>
		</ul>
		<form class="form-inline my-2 my-lg-0">
			<a class="btn btn-secondary my-2 my-sm-0"
				href="<c:url value = "/${applicationScope.URL_LOGOUT}" />">Logout</a>
		</form>
	</div>
</nav>