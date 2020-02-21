<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
    <jsp:include page="/views/General/header.jsp" />
    <div class="error">
        <p> Error: ${error}</p>
    </div>
</body>
</html>