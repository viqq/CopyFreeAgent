<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
  <title>Login page</title>

</head>
<body>
<h1></h1>


<form method="post" action="${pageContext.request.contextPath}/admin/sport">
  <table>
    <tbody>
    <tr>
      <td>Sport:</td>
      <td><input type="text" name="name" id="name"size="30" maxlength="40"  /></td>
    </tr>


    <td></td>
    <td><input type="submit" value="Add Sport" /></td>
    </tr>
    </tbody>
  </table>
</form>

<p>
  <%--<a href="${pageContext.request.contextPath}/index.html">Home page</a><br/>--%>
</p>

<c:if test="${not empty sports}">
  <ul>
    <c:forEach var="sport" items="${sports}">
      <li> "${sport.name} "</li>
    </c:forEach>
  </ul>
</c:if>

</body>
</html>