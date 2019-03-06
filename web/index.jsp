<%@ page import="mybean.data.Post" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<jsp:useBean id="index" type="mybean.data.Index" scope="request"/>

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

</head>
<body style="background-color:#00FF00">
<div class="container">
    <div class="row">
        <div class="col-md-2" style="background-color:#FFFFFF;min-height:600px;border:3px solid #00FF00">

            <!--消息-->
            <h3>消息</h3>
            <ul>
                <% int i;
                    for (i = 0; i < index.getMessages().length; i++) { %>
                <li><%= index.getMessages()[i]%>
                </li>
                <% } %>
            </ul>
            <!--更多消息(每点击一次就多展示N条消息）-->
            <a href="handleIndex?msgNum=<%=index.getMessages().length+5%>">更多消息</a>
        </div>


        <!--博文-->
        <div class="col-md-10" style="background-color:#FFFFFF;min-height:600px;border:3px solid #00FF00">
            <h3>博文</h3>
            <ul>
                <%
                    Post[] posts = index.getPosts();
                    for (i = 0; i < posts.length; i++) { %>
                <li>
                    <a href="read.jsp">
                        <%= posts[i].getTitle()%>
                    </a>
                    <%= posts[i].getAuthor() + " " + posts[i].getNumReads()%>
                </li>
                <% } %>
            </ul>
            <!--更多博文(每点击一次就多展示N条博文）-->
            <a href="handleIndex?postNum=<%= posts.length+10 %>&postType=<%= index.getPostType() %>">更多博文</a>
        </div>
    </div>
</div>

</body>
</html>