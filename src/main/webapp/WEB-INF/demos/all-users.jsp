<%@include file="/WEB-INF/learnx/top.jsp"%>
<main>
    <div class="container">
        <div class="row">
            <div class="col-lg-8">
                <h1>All LearnX Users</h1>

<%--                Add flash message tags--%>

                <p><fmt:message key="allUsers.userCount">
                    <fmt:param value="${fn:length(users) == 1 ? 'is' : 'are'}" />
                    <fmt:param value="${fn:length(users)}" />
                    <fmt:param value="${fn:length(users) != 1 ? 's' : ''}" />
                </fmt:message></p>
                <c:if test="${users.size() > 0}">
                    <div class="table-responsive">
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th scope="col"></th>
                                    <th scope="col">Online</th>
                                    <th scope="col">First name</th>
                                    <th scope="col">Last name</th>
                                    <th scope="col">Email</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${users}" var="user">
                                <tr>
                                    <td><a href="${appURL}/edit-user?id=${user.id}" class="btn btn-dark">Edit</a></td>
                                    <td>
                                        <i class="fa-solid fa-circle text-success"></i>
                                        <i class="fa-regular fa-circle text-success"></i>
                                    </td>
                                    <td>${user.firstName}</td>
                                    <td>${user.lastName}</td>
                                    <td>${user.email}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/learnx/bottom.jsp"%>