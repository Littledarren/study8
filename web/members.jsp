<%--
  Created by IntelliJ IDEA.
  User: thunder
  Date: 3/7/19
  Time: 1:01 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="group" class="mybean.data.Group" scope="request"/>

<html>
<head>
    <title>members</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <%@ include file="nav.txt" %>
</head>
<body>
<h3>Members</h3>
<ul><% int i;
    for (i = 0; i < group.getMemberIDs().length; i++) { %>
    <li><%= group.getMemberNames()[i] + "  " + group.getGroupID()[i] %>
    </li>
    <% } %>
</ul>
</body>
</html>
