<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="index" class="mybean.data.Index" scope="request"/>
<%! int i; %>
<!doctype html>
<html>
<head>
    <title>index</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <%@ include file="nav.txt" %>
    <!--<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>-->
</head>
<body style="background-color:#00FF00">
<div class="container">
    <div class="row">
        <div class="col-md-2" style="background-color:#FFFFFF;height:600px;border:3px solid #00FF00">

            <!--消息-->
            <h3>消息
                <h3>
                    <!--最新消息-->
                    <ul>
                        <% for (i = 0; i < index.getMessageID().length; i++) { %>
                        <li>
                            <a href="#handleRead.java?messageID=<jsp:getProperty name="index" property="messageID[<%=i%>]"/>">
                                <jsp:getProperty name="index" property="messageName[<%=i%>]"/>
                            </a></li>
                        <% } %>
                    </ul>
                    <!--更多消息(每点击一次就多展示N条消息）-->
                    <a href="#handleSearchMsg.java">更多消息</a>
        </div>

        <!--博文-->
        <div class="col-md-10" style="background-color:#FFFFFF;height:600px;border:3px solid #00FF00">
            <h3>博文</h3>
            <ul>
                <% for (i = 0; i < index.getPostID().length; i++) { %>
                <li><a href="#handleRead.java?postID=<jsp:getProperty name="index" property="postID[<%=i%>]"/>">
                    <jsp:getProperty name="index" property="postTitle[<%=i%>]"/>
                </a></li>
                <% } %>
            </ul>
            <!--更多博文(每点击一次就多展示N条博文）-->
            <a href="#handleSearchPost.java?type=<jsp:getProperty name="index" property="postType"/>">更多博文</a>
        </div>
    </div>
</div>

</body>
</html>