<%@ page import="mybean.data.Post" %>
<%@ page import="mybean.data.dbModel.Message" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<jsp:useBean id="index" type="mybean.data.Index" scope="request"/>

<!doctype html>
<html>
<head>
    <title>index</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" TYPE="text/css" href="css/layout.css">
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <%@ include file="nav.txt" %>
    <style>

    </style>
    <!-- style="margin-left:50px" -->
    <!--<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
        <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
        <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>-->
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-2" style="background-color:#FFFFFF;min-height:600px;float:left;margin-left:3%">

            <!--消息-->
            <h3>消息</h3>
            <% int i;
                if (login.isLogined()) { %>
            <ul>
                <% Message[] messages = index.getMessages();
                    for (i = 0; i < messages.length; i++) { %>
                <li>
                    <% if (messages[i].getMsg_content().charAt(0) == '0') { %>
                    <a href="handleRead?postID=<%= messages[i].getMsg_content().substring(1) %>">
                        您关注的博主发了新博文啦</a>
                    <% } else if (messages[i].getMsg_content().charAt(0) == '1') { %>
                    <a href="handleRead?groupID=<%= messages[i].getMsg_content().substring(1) %>">
                        群<%= messages[i].getMsg_content().substring(1)%>发了新公告</a>
                    <% } else if (messages[i].getMsg_content().charAt(0) == '2') { %>
                    <%= messages[i].getMsg_content().substring(1) %>
                    <% } else if (messages[i].getMsg_content().charAt(0) == '3') { %>
                    <%= messages[i].getMsg_content().substring(1) %>
                    <% } %>
                </li>
                <% } %>
            </ul>
            <!--更多消息(每点击一次就多展示N条消息）-->
            <a href="handleIndex?msgNum=<%=index.getMessages().length+5%>">更多消息</a>
            <% } else { %>
            <p>未登陆</p>
            <% } %>

        </div>


        <!--博文-->
        <div class="col-md-9" style="background-color:#FFFFFF;min-height:600px;float:right;margin-right:3%">
            <h3 style="border-bottom:1px solid #dddddd">博文</h3>

            <% Post[] posts = index.getPosts();
                    for (i = 0; i < posts.length; i++) { %>
            <div style="border-bottom:1px solid #dddddd">
                <a href="handleRead?postID=<%= posts[i].getID()%>" style="font-size:20px;color:#000000">
                        <%= posts[i].getTitle()%>
                    </a><br>

                    <p style="font-size:15px;color:#707070">
                        <scan>
                            <% if (posts[i].getType() == 1) { %>
                            原创
                            <% } else if (posts[i].getType() == 2) { %>
                            转载
                            <% } else if (posts[i].getType() == 3) { %>
                            翻译
                            <% } %>
                        </scan>
                        <scan style="margin-left:10px">作者：<%= posts[i].getAuthor() + "  " %>
                        </scan>
                        <scan style="margin-left:10px">阅读量：<%= posts[i].getNumReads() + "  " %>
                        </scan>
                        <scan style="float:right;margin-left:20px"><%= posts[i].getPost_timestamp() %>
                        </scan>
                    </p>
            </div>
                <% } %>
            <!--更多博文(每点击一次就多展示N条博文）-->
            <a href="handleIndex?postNum=<%= posts.length+10 %>&postType=<%= index.getPostType() %>">更多博文</a>
        </div>
    </div>
</div>

</body>
</html>