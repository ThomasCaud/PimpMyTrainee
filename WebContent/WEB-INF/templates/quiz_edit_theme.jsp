<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<fieldset>
	<div class="form-group">
		<h5>Theme</h5>
		<div class="row no-gutters">
			<div class="col-12 col-sm-8">
				<select class="form-control" name="theme">
					<c:forEach items="${themes}" var="theme">
						<option value="${theme.id}"
							<c:if test="${theme.id == quiz.theme.id}">selected</c:if>>${theme.label}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-12 mt-2 mt-sm-0 col-sm-3 offset-sm-1">
				<a
					href="<c:url value = "/${applicationScope.URL_CREATE_THEME}"/>"
					class="btn btn-info btn-block"><i class="fa fa-plus"></i>
					New theme</a>
			</div>
			<div class="form-error">${form.errors['theme']}</div>
		</div>
	</div>
	<div class="form-group">
		<h5>Title</h5>
		<input type="text" name="title" class="form-control"
			placeholder="Enter quiz title"
			value="<c:out value="${quiz.title}"/>">
		<div class="form-error">${form.errors['title']}</div>
	</div>
</fieldset>