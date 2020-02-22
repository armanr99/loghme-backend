<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        li, div, form {
            padding: 5px
        }
    </style>
</head>
<body>
    <jsp:include page="/views/General/header.jsp" />
    <div>${order.restaurantName}</div>
    <ul>
     <c:forEach var="cartItem" items="${order.cartItemsList}">
        <li> ${cartItem.foodName}: ${cartItem.count} </li>
     </c:forEach>
    </ul>
    <div>
        <div>
        Status : ${order.state}
        </div>
        <c:if test="${order.remainingSeconds > 0}">
        <div>
            Remaining time: <c:out value="${order.remainingSeconds}"/> seconds
        </div>
        </c:if>
    </div>
</body>
</html>