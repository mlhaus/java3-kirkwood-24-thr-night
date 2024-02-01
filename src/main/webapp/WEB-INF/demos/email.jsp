<%@include file="/WEB-INF/shared/top.jsp"%>
    <title>Send Email</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/nprogress@0.2.0/nprogress.css" rel="stylesheet"/>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/nprogress@0.2.0/nprogress.js"></script>
<link rel="stylesheet" href="css/loading.css">
<script src="js/loading.js"></script>
</head>
<body>
<h1>Send Email</h1>
<c:if test="${not empty success}">
<div class="alert alert-success" role="alert">
  ${success}
</div>
</c:if>
<form action="${appURL}/email" method="post">
  <!-- Email Address -->
  <label for="email">Email Address:</label>
  <input type="email" id="email" name="email" required>
  <br>

  <!-- Subject -->
  <label for="subject">Subject:</label>
  <input type="text" id="subject" name="subject" required>
  <br>

  <!-- Message -->
  <label for="message">Message:</label>
  <textarea id="message" name="message" rows="4" required></textarea>
  <br>

  <!-- Submit Button -->
  <input type="submit" value="Submit">
</form>
<%@include file="/WEB-INF/shared/bottom.jsp"%>
