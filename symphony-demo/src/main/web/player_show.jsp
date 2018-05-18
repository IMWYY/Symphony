<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: stephen
  Date: 2018/5/18
  Time: 下午7:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Players List</title>
</head>
<body>

<h1> Player List </h1>

<table>
    <tr>
        <th>ID</th>
        <th>name</th>
        <th>team</th>
        <th>phone</th>
        <th>address</th>
        <th>remark</th>
        <th>operation</th>
    </tr>

    <c:forEach var="player" items="${players}">
        <tr>
            <td>${player.id}</td>
            <td>${player.name}</td>
            <td>${player.team}</td>
            <td>${player.phone}</td>
            <td>${player.address}</td>
            <td>${player.remark}</td>
            <td><a href="#">delete</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
