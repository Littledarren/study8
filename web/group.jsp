<%--
  Created by IntelliJ IDEA.
  User: thunder
  Date: 3/4/19
  Time: 1:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="group" class="mybean.data.Group" scope="request"/>

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
                <p><%= group.getNoticeContent() %>
                </p>
            </div>

            <!--一些功能-->
            <div style="height:200px;background-color:#FFFFFF">
                <h3></h3>
                <ul>
                    <li><a id="input1" class="btn" onclick="input(<%= "名片" %>)">修改名片</a></li>
                    <br/>
                    <li>
                        <a href="handleGroup?type=members&groupID=<%= group.getGroupID()%>">查看Group成员</a>
                    </li>
                    <br/>
                    <!--管理员才有的功能-->
                        <% if (group.isAdmin() == true) { %>
                    <li><a id="input2" class="btn" onclick="input(<%= "公告" %>)">发公告</a></li>
                    <br/>
                    <li>
                        <form role="form" action="handleGroup" method="POST">
                            <input type="text" class="form-control" name="memberID" placeholder="输入成员ID"/>
                            <button type="submit" class="btn btn-default>踢人</button>
                        </form>
                    </li>
                    <% } %>
                    <li><a href=" handleGroup?groupID=<%= group.getGroupID() %>">退出group</a></li>
                </ul>
            </div>
        </div>

        <!--该group的最新博文-->
        <div class=" col-md-9
                            " style="height:550px;background-color:#FFFFFF;border:3px solid #00FF00;">
                            <h3>最新博文</h3>
                            <ul>
                                <% int i;
                                    for (i = 0; i < group.getPosts().length; i++) { %>
                                <li>
                                    <a href="handleGroup?postID=<%= group.getPosts()[i].getID() %>">
                                        <%= group.getPosts()[i].getTitle() %>
                                    </a>
                                    <%= group.getPosts()[i].getAuthor() + " " + group.getPosts()[i].getNumReads()%>
                                </li>
                                <% } %>
                            </ul>
            </div>
        </div>
    </div>
</body>
</html>
<script>
    function input(
        _type
    ) {
        if (_type === "名片")
            var x = document.getElementById("input1");  // 找到元素
        else if (_type === "公告")
            var x = document.getElementById("input2");
        x.innerHTML = "<form id=\"cancel\" role=\"form\" action=\"handleGroup\" method=\"POST\">\n" +
            "             <div class=\"form-group\">\n" +
            "                  <label class=\"sr-only\" for=\"content\">content</label>\n" +
            "                  <input type=\"text\" class=\"form-control\" id=\"content\" name=\"content\" placeholder=" + _type + "/>\n" +
            "             </div>\n" +
            "             <input type=\"hidden\" class=\"form-control\" name=\"groupID_type\" value=\"<%= group.getGroupID()+" "+_type %>\"/>" +
            "             <button type=\"submit\" class=\"btn btn-default\ onclick=\"cancel\" >OK</button>\n" +
            "             <button class=\"btn btn-default\" onclick=\"cancel\">取消</button>\n" +
            "        </form>";    // 改变内容
    }

    function cancel(
        _type
    ) {
        var x = document.getElementById("cancel");  // 找到元素
        if (_type === "公告")
            x.innerHTML = "<a id=\"input\" class=\"btn\" onclick=\"input()\">发公告</a>";
        else if (type === "名片")
            x.innerHTML = "<a id=\"input\" class=\"btn\" onclick=\"input()\">修改名片</a>";
    }
</script>
