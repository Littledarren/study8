<%@ page import="mybean.data.Group" %>
<%@ page import="mybean.data.dbModel.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="personalInfo" type="mybean.data.PersonalInfo" scope="request"/>
<%! int i; %>
<!doctype html>
<html>
<head>
    <title>mine</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body {
            background-image: url("images/6.jpg");
            background-repeat: no-repeat;
            background-position: center center;
            background-attachment: fixed;
            background-size: cover;
        }
    </style>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <%@ include file="nav.txt" %>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-3 offset-md-1" style="height:550px;background-color:#FFFFFF;opacity:0.7">
            <!--个人分类-->
            <div style="height:150px;background-color:#FFFFFF;border-bottom:2px solid #00FF00;">
                <h3>个人分类</h3>
                <ul>
                    <%
                        User user = personalInfo.getUser();
                        for (i = 0; i < personalInfo.getClasses().length; i++) { %>
                    <li>
                        <a href="handleMine?class=<%= personalInfo.getClasses()[i] %>">
                            <%= personalInfo.getClasses()[i] %>
                        </a>
                    </li>
                    <% } %>
                </ul>
            </div>

            <!--group-->
            <div style="height:200px;background-color:#FFFFFF">
                <h3>Group</h3>
                <ul>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            创建group
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu" style="width:250px;height:50px;text-align:center">
                            <li>
                                <form class="form-inline" role="form" action="handleMine" method="POST">
                                    <div class="form-group">
                                        <label class="sr-only" for="groupname">group名</label>
                                        <input type="text" class="form-control" id="groupname" name="create"
                                               placeholder="输入group名" style="display:inline-block">
                                    </div>
                                    <button type="submit" name="submit" value="create" class="btn btn-default"
                                            style="display:inline-block;">OK
                                    </button>
                                </form>
                            </li>
                        </ul>
                    </li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            加入group
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu" style="width:250px;height:50px;text-align:center">
                            <li>
                                <form class="form-inline" role="form" action="handleMine" method="POST">
                                    <div class="form-group">
                                        <label class="sr-only" for="groupID">groupID</label>
                                        <input type="text" class="form-control" id="groupID" name="join"
                                               placeholder="输入group ID" style="display:inline-block">
                                    </div>
                                    <button type="submit" name="submit" value="join" class="btn btn-default"
                                            style="display:inline-block">OK
                                    </button>
                                </form>
                            </li>
                        </ul>
                    </li>
                    <!--所在的groups-->
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            所在的groups
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <%
                                Group[] groups = personalInfo.getGroups();
                                for (i = 0; i < groups.length; i++) { %>
                            <li>
                                <a href="handleMine?action=showGroup&groupID=<%= groups[i].getGid() %>">
                                    <%= groups[i].getGname() %>
                                </a>
                            </li>
                            <% } %>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>

        <div class="col-md-9" style="height:550px;background-color:#FFFFFF;opacity:0.7">
            <div style="text-align:center;">
                <!--昵称-->
                <p style="font-size:30px">
                    <%= user.getUname() %>
                </p>
                <!--头像-->
                <img src="<%= user.getProfile_photo()%>" class="img-circle" style="width:auto;height:200px">
                <br/><br/>
                <!--一些数据-->
                <p style="font-size:15px">积分：
                    <%= user.getPoints() %>
                </p>
                <p style="font-size:15px">排名：
                    <%= personalInfo.getRank() %>
                </p>
            </div>
            <table class="table">
                <thead>
                <tr>
                    <th>文章数</th>
                    <th>粉丝数</th>
                    <th>点赞数</th>
                    <th>评论数</th>
                    <th>阅读数</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <jsp:getProperty name="personalInfo" property="numArticles"/>
                    </td>
                    <td>
                        <jsp:getProperty name="personalInfo" property="numFans"/>
                    </td>
                    <td>
                        <jsp:getProperty name="personalInfo" property="numLikes"/>
                    </td>
                    <td>
                        <jsp:getProperty name="personalInfo" property="numComments"/>
                    </td>
                    <td>
                        <jsp:getProperty name="personalInfo" property="numReads"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>

