<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title><c:out value="${restaurant.name}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
    <jsp:include page="/views/General/header.jsp" />
    <div class="restaurant">
        <div class="restaurantLogo">
            <img src="${restaurant.logo}" alt="${restaurant.name}">
        </div>
        <div class="restaurantName">
            <p><c:out value="${restaurant.name}"/></p>
        </div>
        <div class="row row-3">
            <c:forEach var="food" items="${restaurant.listMenu}">
                <div class="col">
                    <div class="food">
                        <strong>Name: <c:out value="${food.name}"/></strong><br>
                        <strong>Description: <c:out value="${food.description}"/></strong><br>
                        <strong>Price: <c:out value="${food.price}"/></strong><br>
                        <strong>Popularity: <c:out value="${food.popularity}"/></strong><br>
                        <form action="/cart" method="POST">
                            <input type="hidden" name="foodName" value="${food.name}" />
                            <input type="hidden" name="restaurantId" value="${restaurant.id}" />
                            <button type="submit">addToCart</button>
                        </form>
                    </div><br>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>