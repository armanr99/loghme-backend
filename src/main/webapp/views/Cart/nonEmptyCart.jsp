<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<html>
<meta charset="UTF-8">
<head>
    <title>Cart</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
    <jsp:include page="/views/General/header.jsp" />
    <div class="cart">
         <div>Restaurant name: ${cart.restaurantName}</div>
         <ul>
             <c:forEach var="cartItem" items="${cart.cartItemsList}">
                <li> ${cartItem.foodName}: ${cartItem.count} </li>
             </c:forEach>
         </ul>
         <form action="/order" method="POST">
             <button type="submit">finalize</button>
         </form>
    </div>
</body>
</html>