<%@ page import="mybean.data.Group" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="personalInfo" class="mybean.data.PersonalInfo" scope="request"/>

<!doctype html>
<html>
<head>
    <style>
        body {
            background-image: url("images/6.jpg");
            background-repeat: no-repeat;
            background-position: center center;
            background-attachment: fixed;
            background-size: cover;
        }
    </style>
    <title>write</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        function shInGroups() {
            var x = document.getElementById("share");
            x.innerHTML = "<div id=\"cancel\">\
                            <a class=\"btn btn-default\" onclick=\"cancel()\">取消</a>\
                            <li class=\"dropdown\" style=\"display:inline-block\">\
                                <a href=\"#\" class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\">\
                                    选择groups\
                                    <b class=\"caret\"></b>\
                                </a>\
                                <ul class=\"dropdown-menu\">\
                                    <%
                                    int i;
                                    Group[] groups = personalInfo.getGroups();
                                    for (i = 0; i < groups.length; i++) { %>\
                                    <li>\
                                        <div class=\"checkbox\"><label>\
                                            <input type=\"checkbox\" name=\"share_group\" value=\"<%= groups[i].getGid() %>\">\
                                            <%= groups[i].getGname() %>\
                                        </label></div>\
                                    </li>\
                                    <% } %>\
                                </ul>\
                            </li>\
                            </div>";
        }

        function cancel() {
            var x = document.getElementById("cancel");
            x.innerHTML = "<div id=\"share\">\
                    <a class=\"btn btn-default\" onclick=\"shInGroups()\">选择groups</a>\
                    <li class=\"dropdown\" style=\"display:inline-block\">\
                        <a href=\"handle\" class=\"dropdown-toggle btn btn-default\" data-toggle=\"dropdown\">\
                            公开/私密\
                            <b class=\"caret\"></b>\
                        </a>\
                        <ul class=\"dropdown-menu\">\
                            <li>\
                                <div class=\"radio\"><label>\
                                    <input type=\"radio\" name=\"optionsRadios\" id=\"public\" value=\"public\" checked>公开\
                                </label></div>\
                            </li>\
                            <li>\
                                <div class=\"radio\"><label>\
                                    <input type=\"radio\" name=\"optionsRadios\" id=\"private\" value=\"private\" checked>私密\
                                </label></div>\
                            </li>\
                        </ul>\
                    </li>\
                </div>";
        }

        function release() {
            window.alert("发布成功");
        }
    </script>
    <%@ include file="nav.txt" %>
</head>

<body style="background-color: #00FF00">
<div class="container">
    <div class="row">
        <!--管理-->
        <%--<div class="col-md-2" style="background-color: #FFFFFF;height:600px;float:left;margin-left:2%">--%>
        <%--<h4>管理</h4>--%>
        <%--<br/>--%>
        <%--<div class="btn-group-vertical">--%>
        <%--<a class="btn btn-default">文章管理</a>--%>
        <%--<a class="btn btn-default">个人分类管理</a>--%>
        <%--<a class="btn btn-default">评论管理</a>--%>
        <%--</div>--%>
        <%--</div>--%>

        <div class="col-6 col-md-2" id="sidebar"
             style="background-color: #FFFFFF;height:600px;opacity:0.7">
            <h4>管理</h4>
            <div class="list-group">
                <a class="list-group-item" href="#" target="">文章管理</a>
                <a class="list-group-item" href="#" target="">个人分类管理</a>
                <a class="list-group-item" href="#" target="">评论管理</a>
            </div>
        </div>

        <!--编辑博文-->
        <form action="handleWrite" method="POST">
            <div class="col-md-7" style="background-color: #FFFFFF;height:600px;float:left;margin-left:2%;opacity:0.95">
                <h4>编辑博文</h4>
                <a class="btn btn-default" href="handleWrite?action=upload">上传文件</a>
                <div class="form-group">
                    <br/>
                    <label for="title" class="sr-only">标题</label>
                    <input type="text" class="form-control" placeholder="标题" name="title" id="title" required/><br/>

                    <label for="content" class="sr-only">正文</label>
                    <textarea class="form-control" placeholder="正文" name="content" rows="20" id="content"></textarea>
                </div>
            </div>


            <!--博文分类-->
            <div class="col-md-2"
                 style="background-color: #FFFFFF;height:600px;float:right;margin-right:2%;text-align:center;opacity:0.7">
                <h4>设置</h4>
                <div class="btn-group-vertical">
                    <div class="dropdown">
                        <a href="#" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                            文章类型
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <div class="radio"><label>
                                    <input type="radio" name="blogType" id="yuanchuang" value="1" checked>原创
                                </label></div>
                            </li>
                            <li>
                                <div class="radio"><label>
                                    <input type="radio" name="blogType" id="zhuanzai" value="2" checked>转载
                                </label></div>
                            </li>
                            <li>
                                <div class="radio"><label>
                                    <input type="radio" name="blogType" id="fanyi" value="3" checked>翻译
                                </label></div>
                            </li>
                        </ul>
                    </div>

                    <div class="dropdown">
                        <a href="#" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                            博客类型
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <div class="radio"><label>
                                    <input type="radio" name="predefined" value="1" checked="checked">论坛
                                </label></div>
                            </li>
                            <li>
                                <div class="radio"><label>
                                    <input type="radio" name="predefined" value="2">知识理论
                                </label></div>
                            </li>
                            <li>
                                <div class="radio"><label>
                                    <input type="radio" name="predefined" value="3">经验分享
                                </label></div>
                            </li>
                            <li>
                                <div class="radio"><label>
                                    <input type="radio" name="predefined" value="4">资源分享
                                </label></div>
                            </li>
                            <li>
                                <div class="radio"><label>
                                    <input type="radio" name="predefined" value="5">考研
                                </label></div>
                            </li>
                            <li>
                                <div class="radio"><label>
                                    <input type="radio" name="predefined" value="6">就业
                                </label></div>
                            </li>
                            <li>
                                <div class="radio"><label>
                                    <input type="radio" name="predefined" value="7">其他
                                </label></div>
                            </li>
                        </ul>
                    </div>

                    <div class="dropdown">
                        <a href="#" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                            个人分类
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <%
                                for (i = 0; i < personalInfo.getClasses().length; i++) { %>
                            <li>
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" name="self_classification"
                                               value="<%=personalInfo.getClasses()[i]%>">
                                        <%=personalInfo.getClasses()[i]%>
                                    </label>
                                </div>
                            </li>
                            <% } %>
                        </ul>
                    </div>


                    <div id="share">
                        <a class="btn btn-default" onclick="shInGroups()">选择groups</a>
                        <li class="dropdown" style="display:inline-block">

                            <a href="#" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                公开/私密
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <div class="radio"><label>
                                        <input type="radio" name="private_switch" id="public" value="0"
                                               checked="checked">公开
                                    </label></div>
                                </li>
                                <li>
                                    <div class="radio"><label>
                                        <input type="radio" name="private_switch" id="private" value="1">私密
                                    </label></div>
                                </li>
                            </ul>
                        </li>
                    </div>
                </div>
                <br/><br/>
                <div class="btn-group-vertical">
                    <input class="btn btn-md btn-primary" name="submit" value="保存草稿" type="submit"/>
                    <input class="btn btn-md btn-primary" name="submit" value="发布" type="submit" onclick="release()"/>
                    <a class="btn btn-sm btn-primary" href="handleIndex">返回</a>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
