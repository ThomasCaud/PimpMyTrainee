<%@ include file="header.jsp"%>
<%@ include file="navbar.jsp"%>

<div class="container">
	<table class="table table-responsive-sm table-sm table-bordered">
		<thead class="thead-dark">
			<tr>
				<th>Quiz</th>
				<th>Theme</th>
				<th>Score</th>
				<th>Duration</th>
				<th class="text-center">Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${records}" var="record">
				<tr>
					<td>${record.quiz.title}</td>
					<td>${record.quiz.theme.label}</td>
					<td>${record.score}/ ${record.answers.size()}</td>
					<td>${record.duration} sec.</td>
					<td class="text-center"><a
						href="<c:url value = "/${applicationScope.URL_VIEW_RESULT}/${record.id}"/>"
						class="btn btn-link"><i class="fa fa-eye"></i></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<%@ include file="footer.jsp"%>