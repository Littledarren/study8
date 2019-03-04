<%--
  Created by IntelliJ IDEA.
  User: thunder
  Date: 3/4/19
  Time: 1:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="group" class="mybean.data.Group" scope="request"/>
<%! int i; %>
<!doctype html>
<html>
<head>
    <title>group</title>
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
        <div class="col-md-3 offset-md-1" style="height:550px;background-color:#FFFFFF;border:3px solid #00FF00;">

            <!--group名与groupID-->
            <div style="height:50px;background-color:#FFFFFF;border-bottom:2px solid #00FF00;">
                <h3>
                    <jsp:getProperty name="group" property="groupName"/>
                </h3>
                <p>
                    <jsp:getProperty name="group" property="groupID"/>
                </p>
            </div>

            <!--公告栏-->
            <div style="height:150px;background-color:#FFFFFF;border-bottom:2px solid #00FF00;">
                <h3>公告栏</h3>
                <ul>
                    <% for (i = 0; i < group.getNoticeIDs().length(); i++) { %>
                    <li>
                        <a href="handleGroup.java?<jsp:getProperty name="group" property="noticeIDs[<%=i%>]"/>">
                            <jsp:getProperty name="group" property="noticeNames[<%=i%>]"/>
                        </a>
                    </li>
                    <% } %>
                </ul>
            </div>

            <!--一些功能-->
            <div style="height:200px;background-color:#FFFFFF">
                <h3></h3>
                <ul>
                    <li><a href="#">修改名片</a></li>
                    <br/>
                    <li>
                        <a href="#handleGroup.java?groupID=<jsp:getProperty name="group" property="groupID"/>">查看Group成员</a>
                    </li>
                    <br/>
                    <!--管理员才有的功能-->
                    <% if (group.getIsAdmin() == true) { %>
                    <li><a href="#">发公告</a></li>
                    <br/>
                    <li><a href="#">踢人</a></li>
                    <br/>
                    <% } %>
                    <li><a href="#handleGroup.java">退出group</a></li>
                </ul>
            </div>
        </div>

        <!--该group的最新博文-->
        <div class="col-md-9" style="height:550px;background-color:#FFFFFF;border:3px solid #00FF00;">
            <h3>最新博文</h3>
            <ul>
                <% for (i = 0; i < group.getPostID().length; i++) { %>
                <li><a href="#handleRead.java?postID=<jsp:getProperty name="group" property="postID[<%=i%>]"/>">
                    <jsp:getProperty name="group" property="postTitle[<%=i%>]"/>
                </a></li>
                <% } %>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
