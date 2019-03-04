<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="personalInfo" class="mybean.data.PersonalInfo" scope="request"/>
<jsp:useBean id="read" class="mybean.data.Read" scope="request"/>
<%! int i; %>
<!doctype html>
<html>
<head>
    <title>read</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <%@ include file="nav.txt" %>
<body style="background-color:#00FF00">
<div class="container">
    <div class="row">
        <div class="col-md-3 offset-md-1" style="height:500px;background-color:#FFFFFF;border:3px solid #00FF00;">
            <!--博主信息-->
            <div style="height:270px;background-color:#FFFFFF;border-bottom:2px solid #00FF00;">
                <p>昵称：
                    <jsp:getProperty name="personalInfo" property="nickname"/>
                </p>
                <p>性别：
                    <jsp:getProperty name="personalInfo" property="sex"/>
                </p>
                <p>高校：
                    <jsp:getProperty name="personalInfo" property="university"/>
                </p>
                <p>专业：
                    <jsp:getProperty name="personalInfo" property="major"/>
                </p>
                <p>积分：
                    <jsp:getProperty name="personalInfo" property="score"/>
                </p>
                <p>排名：
                    <jsp:getProperty name="personalInfo" property="rank"/>
                </p>
                <table class="table">
                    <thead>
                    <tr>
                        <th>文章</th>
                        <th>粉丝</th>
                        <th>点赞</th>
                        <th>评论</th>
                        <th>阅读</th>
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

            <!--该博主的最新博文-->
            <div style="height:200px;background-color:#FFFFFF">
                <h5><b>最新博文</b></h5>
                <ul>
                    <% for (i = 0; i < personalInfo.getPostID().length; i++) { %>
                    <li><a href="<jsp:getProperty name="personalInfo" property="postID[<%=i%>]"/>">
                        <jsp:getProperty name="personalInfo" property="postTitle[<%=i%>]"/>
                    </a></li>
                    <% } %>
                </ul>
                <a href="#handleSearchPost.java?author=<jsp:getProperty name="personalInfo" property="account"/>">更多博文</a>
            </div>
        </div>

        <!--文章内容-->
        <div class="col-md-8" style="height:500px;background-color:#FFFFFF;border:3px solid #00FF00;">
            <h3>
                <jsp:getProperty name="read" property="title"/>
            </h3>
            <p>
                <jsp:getProperty name="read" property="content"/>
            </p>
        </div>

        <!--一些按钮-->
        <div class="col-md-1" style="height:200px;background-color:#FFFFFF;border:3px solid #00FF00;">
            <a class="btn" href="handleReading.java?type=点赞">点赞<a><br/>
                <a class="btn" href="handleReading.java?type=收藏">收藏<a><br/>
                    <a class="btn" href="handleReading.java?type=评论">评论<a><br/>
                        <a class="btn" href="handleReading.java?type=上一篇">上一篇<a><br/>
                            <a class="btn" href="handleReading.java?type=下一篇">下一篇<a><br/>
        </div>
    </div>
</div>
</body>
</html>

