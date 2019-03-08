<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="personalInfo" class="mybean.data.PersonalInfo" scope="request"/>

<!doctype html>
<html>
<head>
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
                            <a class=\"btn\" onclick=\"cancel()\">取消</a>\
                            <li class=\"dropdown\" style=\"margin-left:3%;display:inline-block\">\
                                <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">\
                                    选择groups\
                                    <b class=\"caret\"></b>\
                                </a>\
                                <ul class=\"dropdown-menu\">\
                                    <% for (i = 0; i < personalInfo.getGroupNames().length; i++) { %>\
                                    <li>\
                                        <div class=\"checkbox\"><label>\
                                            <input type=\"checkbox\" name=\"share_group\" value=\"<%= personalInfo.getGroupIDs()[i]%>\">\
                                            <%= personalInfo.getGroupNames()[i] %>\
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
                    <a class=\"btn\" onclick=";
            shInGroups();\">选择groups</a>\
                    <li class=\"dropdown\" style=\"margin-left:3%;display:inline-block\">\
                        <a href=\"handle\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">\
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
    </script>
    <%@ include file="nav.txt" %>
</head>

<body style="background-color: #00FF00">
<div class="container">
    <div class="row">
        <!--管理-->
        <div class="col-md-2" style="background-color: #FFFFFF;height:600px;border:3px solid #00FF00">
            <h4>管理</h4>
            <br/>
            <a href="handleArticles">文章管理</a><br/><br/>
            <a href="handleClasses">个人分类管理</a><br/><br/>
            <a href="handleComments">评论管理</a><br/>
        </div>

        <!--上传资源-->
        <form action="handleWrite" method="post" enctype="multipart/form-data">
            <div class="col-md-8" style="background-color: #FFFFFF;height:600px;border:3px solid #00FF00">
                <h4>上传资源</h4>
                <a class="btn" href="handleWrite?action=edit">编辑文章</a>
                <div class="form-group">
                    <br/>
                    <label for="title" class="sr-only">标题</label>
                    <input type="text" class="form-control" placeholder="标题" name="title" id="title"
                           required/><br/><br/>

                    <input type="file" name="file" size="50"/>
                    <br/>
                    <input type="submit" value="Upload File"/>
                </div>
            </div>


            <!--博文分类-->
            <div class="col-md-2" style="background-color: #FFFFFF;height:600px;border:3px solid #00FF00">
                <h4>设置</h4>

                <br/>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        个人分类
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <% int i;
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
                </li>

                <br/>
                <div id="share">
                    <a class="btn" onclick="shInGroups()">选择groups</a>
                    <li class="dropdown" style="margin-left:3%;display:inline-block">

                        <a href="handle" class="dropdown-toggle" data-toggle="dropdown">
                            公开/私密
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <div class="radio"><label>
                                    <input type="radio" name="private_switch" id="public" value="0" checked>公开
                                </label></div>
                            </li>
                            <li>
                                <div class="radio"><label>
                                    <input type="radio" name="private_switch" id="private" value="1" checked>私密
                                </label></div>
                            </li>
                        </ul>
                    </li>
                </div>
                <br/><br/>

                <input class="btn btn-sm btn-primary" name="submit" value="uploadFile" type="submit"/><br/>
                <a href="handleIndex" class="btn btn-sm btn-primary">返回</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>