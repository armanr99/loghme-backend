<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>${restaurant.name}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
    <jsp:include page="/views/General/header.jsp" />
    <div class="restaurant">
        <div class="restaurantLogo">
            <img src="${restaurant.logo}" alt="${restaurant.name}">
        </div>
        <div class="row row-3">
            <c:forEach var="food" items="${restaurant.listMenu}">
                <div class="col">
                    <div class="food">
                        <strong>Name: ${food.name}</strong><br>
                        <strong>Description: ${food.description}</strong><br>
                        <strong>Price: ${food.price}</strong><br>
                        <strong>Popularity: ${food.popularity}</strong><br>
                        <form action="/cart" method="POST">
                            <input type="hidden" name="foodName" value="${food.name}" />
                            <input type="hidden" name="restaurantId" value=${restaurant.id} />
                            <button type="submit">addToCart</button>
                        </form>
                    </div><br>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>