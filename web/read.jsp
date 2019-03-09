<%@ page import="mybean.data.Comment" %>
<%@ page import="mybean.data.Post" %>
<%@ page import="mybean.data.dbModel.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="personalInfo" type="mybean.data.PersonalInfo" scope="request"/>
<jsp:useBean id="post" type="mybean.data.Post" scope="request"/>
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
        <div class="col-md-3 offset-md-1" style="min-height:600px;background-color:#FFFFFF;border:3px solid #00FF00;">
            <!--博主信息-->
            <%
                User user = personalInfo.getUser();
            %>
            <img src="<%= user.getProfile_photo() %>" class="img-circle" style="width:80;height:80px;">

            <div style="height:270px;background-color:#FFFFFF;border-bottom:2px solid #00FF00;">
                <p>昵称：
                    <%= user.getUname() %>
                </p>
                <p>性别：
                    <%= user.getSex() %>
                </p>
                <p>高校：
                    <%= user.getCollege() %>
                </p>
                <p>专业：
                    <%= user.getProfession() %>
                </p>
                <p>积分：
                    <%= user.getPoints()%>
                </p>
                <p>排名：
                    <%= 1 %>
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
                            <%= personalInfo.getNumArticles() %>
                        </td>
                        <td>
                            <%= personalInfo.getNumFans() %>
                        </td>
                        <td>
                            <%= personalInfo.getNumLikes() %>
                        </td>
                        <td>
                            <%= personalInfo.getNumComments() %>
                        </td>
                        <td>
                            <%= personalInfo.getNumReads() %>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <!--该博主的最新博文-->
            <div style="height:200px;background-color:#FFFFFF">
                <h5><b>最新博文</b></h5>
                <ul>
                    <% int i;
                        Post[] posts = personalInfo.getPosts();
                        for (i = 0; i < posts.length; i++) { %>
                    <li><a href="handleRead?postID=<%= posts[i].getID() %>">
                        <%= posts[i].getTitle() %>
                    </a></li>
                    <% } %>
                </ul>
                <a href="handleRead?postNum=<%= posts.length+5 %>">更多博文</a>
            </div>
        </div>


        <div class="col-md-8" style="min-height:600px;background-color:#FFFFFF;border:3px solid #00FF00;">
            <!--文章内容-->
            <div>
                <h3><%= post.getTitle() %>
                </h3>
                <p style="float:right;color:#777777">
                    <%= "阅读量:" + post.getNumReads() + "  点赞量:" + post.getNumLikes() + "  评论量:" + post.getNumComments()%>
                </p><br/>
                <hr/>
                <p>
                    <%
                        String content = post.getContent();
                        out.println(content.replaceAll("\n", "<br/>").replaceAll(" ", "&nbsp;"));
                    %>
                </p>
            </div>
            <br/>
            <!--Write comments-->
            <div>
                <form role="form" action="handleRead" method="POST">
                    <div class="form-group">
                        <label class="sr-only" for="title">comment</label>
                        <input type="text" name="comment" class="form-control" id="title" placeholder="评论">
                        <input type="hidden" name="postID" value="<%= post.getID()%>"/>
                    </div>
                    <button type="submit" class="btn btn-default">发表</button>
                </form>
            </div>
            <!--Read comments-->
            <div>

                <%
                    Comment[] comments = post.getComments();
                    for (i = 0; i < comments.length; i++) { %>
                <p><%= comments[i].getAuthor() %>
                </p>
                <% if (comments[i].getReply_id() != -1) {
                    for (Comment comment : comments) {
                        if (comment.getID() == comments[i].getReply_id()) { %>
                <p>回复<%= comment.getAuthor() + ": " + comment.getComment_content() %>
                </p>
                <% break;
                }
                }%>
                <p><%= comments[i].getComment_content() %>
                </p>
                <% }%>
                <script>
                    function inputReply() {
                        var x = document.getElementById("reply<%= i %>");  // 找到元素
                        x.innerHTML = "<form id=\"cancel<%= i %>\" role=\"form\" action=\"handleRead\" method=\"POST\">\n" +
                            "                    <div class=\"form-group\">\n" +
                            "                        <label class=\"sr-only\" for=\"name\">reply</label>\n" +
                            "                        <input type=\"text\" class=\"form-control\" id=\"name\" name=\"replyContent\" placeholder=\"回复\"/>\n" +
                            "                    <input type=\"hidden\" class=\"form-control\" name=\"replyTo\" value=\"<%= comments[i].getID() %>\"/>" +
                            "                    <input type=\"hidden\" name=\"postID\" value=\"<%= post.getID()%>\" />" +
                            "                    <button type=\"submit\" class=\"btn btn-default\ onclick=\"cancel()\" >发表</button>\n" +
                            "                    <button class=\"btn btn-default\" onclick=\"cancel()\">取消</button>\n" +
                            "                </div></form>";    // 改变内容
                    }

                    function cancel() {
                        var x = document.getElementById("cancel<%= i %>");  // 找到元素
                        x.innerHTML = "<span id=\"reply<%= i %>\"><button type=\"button\" onclick=\"inputReply()\">回复</button></span>";
                    }
                </script>
                <span id="reply<%= i %>"><button type="button" onclick="inputReply()">回复</button></span>
                <% } %>
            </div>
        </div>


        <!--一些按钮-->
        <div class="col-md-1" style="height:200px;background-color:#00FF00;border:3px solid #00FF00;">
            <div class="btn-group-vertical">
                <a class="btn btn-default" href="handleRead?postID=<%= post.getID() %>&type=like">点赞</a>
                <a class="btn btn-default" href="handleRead?postID=<%= post.getID() %>&type=collect">收藏</a>
                <a class="btn btn-default" href="handleRead?type=attention&userID=<%= user.getMail() %>">关注</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>

