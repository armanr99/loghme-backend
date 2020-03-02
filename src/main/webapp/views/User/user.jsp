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
            <li>id: <c:out value="${user.id}"/></li>
            <li>First Name: <c:out value="${user.firstName}"/></li>
            <li>Last Name: <c:out value="${user.lastName}"/></li>
            <li>Phone Number: <c:out value="${user.phoneNumber}"/></li>
            <li>email: <c:out value="${user.email}"/></li>
            <li>credit: <c:out value="${user.credit}"/> Tomans</li>
            <form action="/wallet" method="POST">
                <button type="submit">increase</button>
                <input type="text" name="amount" value="" />
            </form>
            <li>
                Orders :
                <ul>
                <c:forEach var="order" items="${user.ordersList}">
                    <li>
                        <a href="order/${order.id}">order id : <c:out value="${order.id}"/></a>
                    </li>
                </c:forEach>
                </ul>
            </li>
        </ul>
    </div>
</body>
</html>