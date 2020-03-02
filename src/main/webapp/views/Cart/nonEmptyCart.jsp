<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cart</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
    <jsp:include page="/views/General/header.jsp" />
    <div class="cart">
         <div>Restaurant name: <c:out value="${cart.restaurantName}"/></div>
         <ul>
             <c:forEach var="cartItem" items="${cart.cartItemsList}">
                <li><c:out value="${cartItem.foodName}"/>: <c:out value="${cartItem.count}"/></li>
             </c:forEach>
         </ul>
         <form action="/order" method="POST">
             <button type="submit">finalize</button>
         </form>
    </div>
</body>
</html>