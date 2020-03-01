<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>Food Party</title>
    <style>
        img {
        	width: 50px;
        	height: 50px;
        }
        li {
            display: flex;
            flex-direction: row;
        	padding: 0 0 5px;
        }
        div, form {
            padding: 0 5px
        }
        .old-price {
            text-decoration: line-through;
        }
    </style>
</head>
<body>
    <jsp:include page="/views/General/header.jsp" />
    <ul>
        <c:forEach var="restaurant" items="${restaurants}">
            <c:forEach var="food" items="${restaurant.listPartyFoodsMenu}">
                <li>menu:
                    <ul>
                        <li>
                            <img src="${restaurant.logo}" alt="${restaurant.name}">
                            <div>${restaurant.name}</div>
                            <div>${food.name}</div>
                            <div>${food.description}</div>
                            <div class="old-price">${food.oldPrice} Tomans</div>
                            <div>${food.price} Tomans</div>
                            <div>remaining count: ${food.count}</div>
                            <div>popularity: ${food.popularity}</div>
                            <form action="/cart" method="POST">
                                <input type="hidden" name="foodName" value="${food.name}" />
                                <input type="hidden" name="restaurantId" value=${restaurant.id} />
                                <button type="submit">addToCart</button>
                            </form>
                        </li>
                    </ul>
                </li>
            </c:forEach>
        </c:forEach>
    </ul>
</body>
</html>