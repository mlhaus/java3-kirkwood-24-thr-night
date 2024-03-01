<%@include file="/WEB-INF/learnx/top.jsp"%>

<main class="container">
    
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

    <%-- Add a jumbtron --%>
    
    <div id="carouselExampleCaptions" class="carousel slide mb-4">
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
            <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1" aria-label="Slide 2"></button>
        </div>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="${appURL}/images/learnx/slider-01.jpg" class="d-block w-100" alt="Person with graduation hat">
                <div class="carousel-caption d-none d-md-block">
                    <h5>First slide label</h5>
                    <p>Some representative placeholder content for the first slide.</p>
                </div>
            </div>
            <div class="carousel-item">
                <img src="${appURL}/images/learnx/slider-02.jpg" class="d-block w-100" alt="Stack of books">
                <div class="carousel-caption d-none d-md-block">
                    <h5>Second slide label</h5>
                    <p>Some representative placeholder content for the second slide.</p>
                </div>
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
        
    <%-- Add a 2 column info box --%>

</main>

<%@include file="/WEB-INF/learnx/bottom.jsp"%>