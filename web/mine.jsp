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
                    <li><a href="handleMine?value=create">创建group</a></li>
                    <li><a href="handleMine?value=join">加入group</a></li>
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
                                <a href="handleGroup?groupID=<%= groups[i].getGid() %>">
                                    <%= groups[i].getGname() %>
                                </a>
                            </li>
                            <% } %>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>

        <div class="col-md-9" style="height:550px;background-color:#FFFFFF;border:3px solid #00FF00;">
            <!--昵称-->
            <p align="center" style="font-size:30px">
                <%= user.getUname() %>
            </p>
            <!--头像-->
            <img src="image/dog.jpg" class="img-circle" style="width:auto;height:200px;margin-left:28%">
            <br/><br/>
            <!--一些数据-->
            <p align="center" style="font-size:15px">积分：
                <%= user.getPoints() %>
            </p>
            <p align="center" style="font-size:15px">排名：
                1
            </p>
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

