<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="personalInfo" class="mybean.data.PersonalInfo" scope="request"/>

<html>
<head>
    <title>registerSuccess</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container col-md-5" style="margin:auto;margin-top:5%">
    <p style="font-size:25px;color:#0000FF">
        <jsp:getProperty name="personalInfo" property="backNews"/>
    </p>
    <ul>
        <li>
            <jsp:getProperty name="personalInfo" property="nickname"/>
        </li>
        <li>
            <jsp:getProperty name="personalInfo" property="accout"/>
        </li>
        <li>
            <jsp:getProperty name="personalInfo" property="sex"/>
        </li>
        <li>
            <jsp:getProperty name="personalInfo" property="university"/>
        </li>
        <li>
            <jsp:getProperty name="personalInfo" property="major"/>
        </li>
    </ul>
    <a href="/index">进入首页</a>
</div>
</body>
</html>	
