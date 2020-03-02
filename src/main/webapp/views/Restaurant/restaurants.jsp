<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>Restaurants</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
    <jsp:include page="/views/General/header.jsp" />
    <div class="row row-3">
        <c:forEach var="restaurant" items="${restaurants}">
            <div class="col">
                <div class="info">
                    <a class="book" href="/restaurants/<c:out value="${restaurant.id}"/>">
                        <div class="restaurantCover">
                            <img src=<c:out value="${restaurant.logo}"/> alt=<c:out value="${restaurant.name}"/>>
                        </div>
                        <strong><c:out value="${restaurant.name}"/></strong>
                    </a>
                </div>
                <div class="restaurantDelivery">
                    <p>Expected Delivery Time: <c:out value="${expectedDeliveries[restaurant.id]}"/> Seconds</p>
                </div>
            </div>
        </c:forEach>
    </div>
</body>
</html>