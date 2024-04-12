<%@include file="/WEB-INF/learnx/top.jsp" %>
<main>

    <div class="container">
        <div class="row">
            <%@include file="/WEB-INF/learnx/dashboard-sidebar.jsp" %>

            <div class="col-xl-9">
                <div class="card">
                    <div class="card-header bg-dark">
                        <h3 class="card-header-title text-light">${pageTitle}</h3>
                    </div>
                    <div class="card-body">

                        <c:choose>
                            <c:when test="${not empty flashMessageSuccess}">
                                <div class="alert alert-success my-2">
                                        ${flashMessageSuccess}
                                </div>
                                <c:remove var="flashMessageSuccess" scope="session"></c:remove>
                            </c:when>
                            <c:when test="${not empty flashMessageWarning}">
                                <div class="alert alert-warning my-2">
                                        ${flashMessageWarning}
                                </div>
                                <c:remove var="flashMessageWarning" scope="session"></c:remove>
                            </c:when>
                        </c:choose>


                        <form action="${appURL}/edit-user?id=${user.id}" method="post" class="row">
                            <%-- First Name--%>
                            <div class="col-md-6">
                                <label class="form-label" for="firstNameInput">First Name</label>
                                <input class="form-control" type="text" id="firstNameInput" name="firstNameInput"
                                       value="${fn:escapeXml(user.firstName)}">
                            </div>
                            <%-- Last Name--%>
                            <div class="col-md-6">
                                <label class="form-label" for="lastNameInput">Last Name</label>
                                <input class="form-control" type="text" id="lastNameInput" name="lastNameInput"
                                       value="${fn:escapeXml(user.lastName)}">
                            </div>

                            <!-- Email id -->
                            <div class="col-md-6">
                                <label class="form-label" for="emailInput">Email</label>
                                <input class="form-control <c:if test="${not empty results.emailError}">is-invalid</c:if>" type="text" id="emailInput" name="emailInput" value="${user.email}">
                                <c:if test="${not empty results.emailError }"><div class="invalid-feedback">${results.emailError}</div></c:if>
                            </div>

                            <!-- Phone number -->
                            <div class="col-md-6">
                                <label class="form-label" for="phoneInput">Phone number</label>
                                <input type="text" class="form-control <c:if test="${not empty results.phoneError}">is-invalid</c:if>" id="phoneInput" name="phoneInput" value="${user.phone}">
                                <c:if test="${not empty results.phoneError }"><div class="invalid-feedback">${results.phoneError}</div></c:if>
                            </div>

<%--                                Language--%>
                            <div class="col-md-6">
                                <label class="form-label" for="languageInput">Language</label>
                                <select class="form-select <c:if test="${not empty results.languageError}">is-invalid</c:if>" name="languageInput" id="languageInput">
                                    <option value="en" <c:if test="${user.language eq 'en'}">selected</c:if>>English</option>
                                    <option value="fr" <c:if test="${user.language eq 'fr'}">selected</c:if>>French</option>
                                    <option value="ar" <c:if test="${user.language eq 'ar'}">selected</c:if>>Arabic</option>
                                </select>
                                <c:if test="${not empty results.languageError}">
                                    <div class="invalid-feedback">${results.languageError}</div>
                                </c:if>
                            </div>

<%--                                Status--%>
                            <div class="col-md-6">
                                <label class="form-label" for="statusInput">Status</label>
                                <select class="form-select <c:if test="${not empty results.statusError}">is-invalid</c:if>" name="statusInput" id="statusInput">
                                    <option value="inactive" <c:if test="${user.status eq 'inactive'}">selected</c:if>>Inactive</option>
                                    <option value="active" <c:if test="${user.status eq 'active'}">selected</c:if>>Active</option>
                                    <option value="locked" <c:if test="${user.status eq 'locked'}">selected</c:if>>Locked</option>
                                </select>
                                <c:if test="${not empty results.statusError}">
                                    <div class="invalid-feedback">${results.statusError}</div>
                                </c:if>
                            </div>


                                <%--  Privileges--%>
                                <div class="col-md-6">
                                    <label class="form-label" for="privilegesInput">Privileges</label>
                                    <select class="form-select <c:if test="${not empty results.privilegesError}">is-invalid</c:if>" name="privilegesInput" id="privilegesInput">
                                        <option value="student" <c:if test="${user.privileges eq 'student'}">selected</c:if>>Student</option>
                                        <option value="teacher" <c:if test="${user.privileges eq 'teacher'}">selected</c:if>>Teacher</option>
                                        <option value="admin" <c:if test="${user.privileges eq 'admin'}">selected</c:if>>Admin</option>
                                    </select>
                                    <c:if test="${not empty results.privilegesError}">
                                        <div class="invalid-feedback">${results.privilegesError}</div>
                                    </c:if>
                                </div>

                            <!-- Save button -->
                            <div class="d-sm-flex justify-content-end">
                                <button type="submit" class="btn btn-primary mb-0">Save changes</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</main>
<%@include file="/WEB-INF/learnx/bottom.jsp" %>