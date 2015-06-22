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
<h1>Login page</h1>



<form method="post" action="/user" >
  <table>
    <tbody>
    <tr>
      <td>Login:</td>
      <td><input type="text" name="login" id="login"size="30" maxlength="40"  /></td>
    </tr>
    <tr>
      <td>Password:</td>
      <td><input type="password" name="password" id="password" size="30" maxlength="32" /></td>
    </tr>
    <tr>
    <tr>
      <td>City:</td>
      <td><input type="text" name="city" id="city"size="30" maxlength="40"  /></td>
    </tr>
    <tr>
      <td>Email:</td>
      <td><input type="text" name="email" id="email" size="30" maxlength="32" /></td>
    </tr>

      <td></td>
      <td><input type="submit" value="Register" /></td>
    </tr>
    </tbody>
  </table>
</form>

<p>
  <%--<a href="${pageContext.request.contextPath}/index.html">Home page</a><br/>--%>
</p>

<%--<c:if test="${not empty users}">
  <ul>
    <c:forEach var="user" items="${users}">
      <li> "${user.login} , ${user.password} , ${user.email} , ${user.city} "</li>
    </c:forEach>
  </ul>
</c:if>--%>

</body>
</html>