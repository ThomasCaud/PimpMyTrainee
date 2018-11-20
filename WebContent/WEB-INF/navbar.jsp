<c:choose>
	<c:when test="${sessionScope.sessionUser.getRole() == 'ADMIN'}">
		<%@ include file="admin_navbar.jsp"%>
	</c:when>
	<c:when test="${sessionScope.sessionUser.getRole() == 'TRAINEE'}">
		<%@ include file="trainee_navbar.jsp"%>
	</c:when>
</c:choose>
