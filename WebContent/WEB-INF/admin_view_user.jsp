<%@ include file="header.jsp"%>

<%@ include file="admin_navbar.jsp"%>

<div class="container">

	<div class="row">
		<div class="col-12">
			<div class="row align-items-center">
				<div class="col-12 col-sm-6 col-md-7 col-lg-8">
					<h1>View a user</h1>
				</div>
				<div class="col-12 col-sm-6 col-md-5 col-lg-4">
					<a href="<c:url value = "/${applicationScope.URL_USERS}" />"
						class="btn btn-warning btn-block"> <i class="fa fa-arrow-left"></i>
						Back to the list
					</a>
				</div>
			</div>
			<hr>

			<ul class="nav nav-pills nav-fill justify-content-center">
				<li class="nav-item">
					<a
						class="nav-link <c:if test="${searchResults == null}">active</c:if>"
						data-toggle="tab"
						href="#usersInformation">
						User's Profile
					</a>
				</li>
				<li class="nav-item">
					<a class="nav-link <c:if test="${searchResults != null}">active</c:if>"
						data-toggle="tab"
						href="#records">
						Records
					</a>
				</li>
			</ul>

			<div id="myTabContent" class="tab-content">
				<div
					class="tab-pane fade <c:if test="${searchResults == null}">show active</c:if>"
					id="usersInformation">
					<hr>
					<form method="post" action="">
						<fieldset>
							<div class="form-group">
								<label>Firstname</label> <input type="text" class="form-control"
									placeholder="Enter firstname" name="firstname"
									value="<c:out value="${user.firstname}"/>">
								<div class="form-error">${form.errors['firstname']}</div>
							</div>

							<div class="form-group">
								<label>Lastname</label> <input type="text" class="form-control"
									placeholder="Enter lastname" name="lastname"
									value="<c:out value="${user.lastname}"/>">
								<div class="form-error">${form.errors['lastname']}</div>
							</div>

							<div class="form-group">
								<label>Email</label> <input type="text" class="form-control"
									placeholder="Enter email" name="email"
									value="<c:out value="${user.email}"/>">
								<div class="form-error">${form.errors['email']}</div>
							</div>

							<div class="form-group">
								<label>Company</label> <input type=text class="form-control"
									placeholder="Enter company" name="company"
									value="<c:out value="${user.company}"/>">
								<div class="form-error">${form.errors['company']}</div>
							</div>

							<div class="form-group">
								<label>Phone</label> <input type=text class="form-control"
									placeholder="Enter phone" name="phone"
									value="<c:out value="${user.phone}"/>">
								<div class="form-error">${form.errors['phone']}</div>
							</div>

							<div class="form-group">
								<label>Role: ${user.role}</label>
							</div>

							<fieldset class="form-group">
								<label>Active account</label>
								<div class="form-check">
									<label class="form-check-label"> <input type="radio"
										class="form-check-input" name="isActive" value="true"
										<c:if test= "${user.isActive}">checked="checked" </c:if>>
										Yes
									</label>
								</div>
								<div class="form-check">
									<label class="form-check-label"> <input type="radio"
										class="form-check-input" name="isActive" value="false"
										<c:if test= "${!user.isActive}">checked="checked" </c:if>>
										No
									</label>
								</div>
							</fieldset>

							<div class="form-group">
								<label>Date of creation : ${user.creationDate } </label>
							</div>

							<div class="form-group">
								<button type="submit" class="btn btn-lg btn-warning btn-block">Save
									modifications</button>
							</div>
						</fieldset>
					</form>
				</div>
				<div
					class="tab-pane fade <c:if test="${searchResults != null}">show active</c:if>"
					id="records">

					<c:choose>
						<c:when test="${records.size() == 0 && searchResults == null}">
							No saved result.
						</c:when>
						<c:otherwise>
							<hr>
							<div class="row justify-content-center">
								<div class="col-12 col-sm-12 col-lg-5">
									<form method="get" action="" class="form-inline">
										<input type="text" class="form-control col-10"
											placeholder="Search" name="searchResults">
										<div class="col-2">
											<button type="submit" class="btn btn-primary form-control">
												<i class="fa fa-search"></i>
											</button>
										</div>
									</form>
								</div>
							</div>
							<hr>
							<c:if test="${searchResults != null && searchResults != ''}">
								<h5 class="inline-block">
									Results for the search "${searchResults}" <a
										href="<c:url value = "/${applicationScope.URL_VIEW_USER}/${user.id}"/>"
										class="btn btn-danger btn-sm"><i class="fa fa-times"></i></a>
								</h5>
							</c:if>
							<table class="table table-responsive-sm table-sm table-bordered">
								<thead class="thead-dark">
									<tr>
										<th>Quiz</th>
										<th>Theme</th>
										<th>Score</th>
										<th>Duration</th>
										<th>Best score</th>
										<th>Best score duration</th>
										<th>Rank</th>
										<th class="text-center">Action</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${records}" var="record">
										<tr>
											<td>${record.quiz.title}</td>
											<td>${record.quiz.theme.label}</td>
											<td>${record.score}/${record.answers.size()}</td>
											<td>${record.duration}sec.</td>
											<td>${record.ranking.bestScore}</td>
											<td>${record.ranking.durationOfBestScore}sec.</td>
											<td>${record.ranking.scoreRank}/${record.ranking.nbRespondents}</td>
											<td class="text-center"><a
												href="<c:url value = "/${applicationScope.URL_VIEW_RECORD}/${record.id}"/>"
												class="btn btn-link"><i class="fa fa-eye"></i></a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>