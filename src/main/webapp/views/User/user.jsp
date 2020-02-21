<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profile</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
    <jsp:include page="/views/General/header.jsp" />
    <div class="user">
        <ul>
            <li>id: ${user.id}</li>
            <li>First Name: ${user.firstName}</li>
            <li>Last Name: ${user.lastName}</li>
            <li>Phone Number: ${user.phoneNumber}</li>
            <li>email: ${user.email}</li>
            <li>credit: ${user.credit} Tomans</li>
            <form action="/wallet" method="POST">
                <button type="submit">increase</button>
                <input type="text" name="amount" value="" />
            </form>
        </ul>
    </div>
</body>
</html>