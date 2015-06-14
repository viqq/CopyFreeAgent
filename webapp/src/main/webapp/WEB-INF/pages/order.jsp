<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Create order</title>
</head>

<body>

<p>Order <b>${film.name}</b> film</p>
<form action="/shop/saveOrder" method="post">
    <input type="hidden" name="filmId" value="${film.id}">
    <input type="text" name="customerName" value="Customer name">
    <input type="text" name="customerPhone" value="Customer phone">
    <input type="submit" value="Save order">
</form>

</body>
</html>
