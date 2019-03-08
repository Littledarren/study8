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
        function input(
            _type
        ) {
            if (_type === "上传文件") {
                var x = document.getElementById("input1");  // 找到元素
                x.innerHTML = "<div id=\"input1\">\n" +
                    "                    <a class=\"btn\" onclick=\"input('编写正文')\">编写正文</a>\n" +

                    "                    <label for=\"file\" class=\"sr-only\">上传文件</label>\n" +
                    "                    上传文件:\n" +
                    "                    <input type=\"file\" style=\"width:100px;display:inline-block\" class=\"form-control\"\n" +
                    "                           placeholder=\"上传文件\" name=\"file\" id=\"file\" required/>\n" +
                    "                </div>";
            } else if (_type === "编写正文") {
                var x = document.getElementById("input1");
                x.innerHTML = "<div id=\"input1\">\n" +
                    "                    <a class=\"btn\" onclick=\"input('上传文件')\">上传文件</a>\n" +

                    "                    <label for=\"content\" class=\"sr-only\">正文</label>\n" +
                    "                    <textarea class=\"form-control\" placeholder=\"正文\" name=\"content\" rows=\"18\" id=\"content\"></textarea>\n" +
                    "                </div>";
            }
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

        <!--编辑博文或上传资源-->
        <form action="handleWrite" method="POST">
            <div class="col-md-8" style="background-color: #FFFFFF;height:600px;border:3px solid #00FF00">
                <h4>编辑博文</h4>
                <div class="form-group">
                    <br/>
                    <label for="title" class="sr-only">标题</label>
                    <input type="text" class="form-control" placeholder="标题" name="title" id="title" required/><br/>

                    <div id="input1">
                        <a class="btn" onclick="input('上传文件')">上传文件</a>

                        <label for="content" class="sr-only">正文</label>
                        <textarea class="form-control" placeholder="正文" name="content" rows="20"
                                  id="content"></textarea>
                    </div>

                </div>
            </div>


            <!--博文分类-->
            <div class="col-md-2" style="background-color: #FFFFFF;height:600px;border:3px solid #00FF00">
                <h4>分类</h4>
                <br/>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
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
                </li>
                <br/>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        博客类型
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <div class="radio"><label>
                                <input type="radio" name="predefined" value="1" checked>论坛
                            </label></div>
                        </li>
                        <li>
                            <div class="radio"><label>
                                <input type="radio" name="predefined" value="2" checked>知识理论
                            </label></div>
                        </li>
                        <li>
                            <div class="radio"><label>
                                <input type="radio" name="predefined" value="3" checked>经验分享
                            </label></div>
                        </li>
                        <li>
                            <div class="radio"><label>
                                <input type="radio" name="predefined" value="4" checked>资源分享
                            </label></div>
                        </li>
                        <li>
                            <div class="radio"><label>
                                <input type="radio" name="predefined" value="5" checked>考研
                            </label></div>
                        </li>
                        <li>
                            <div class="radio"><label>
                                <input type="radio" name="predefined" value="6" checked>就业
                            </label></div>
                        </li>
                        <li>
                            <div class="radio"><label>
                                <input type="radio" name="predefined" value="7" checked>其他
                            </label></div>
                        </li>
                    </ul>
                </li>
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
                <%--<br/>--%>
                <%--<li class="dropdown">--%>
                <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown">--%>
                <%--专业--%>
                <%--<b class="caret"></b>--%>
                <%--</a>--%>
                <%--<ul class="dropdown-menu">--%>
                <%--<li>--%>
                <%--<div class="radio"><label>--%>
                <%--<input type="radio" name="optionsRadios" id="optionsRadios1" value="gong" checked>工科--%>
                <%--</label></div>--%>
                <%--</li>--%>
                <%--<li>--%>
                <%--<div class="radio"><label>--%>
                <%--<input type="radio" name="optionsRadios" id="optionsRadios2" value="li" checked>理学--%>
                <%--</label></div>--%>
                <%--</li>--%>
                <%--<li>--%>
                <%--<div class="radio"><label>--%>
                <%--<input type="radio" name="optionsRadios" id="optionsRadios3" value="jingji" checked>经济学--%>
                <%--</label></div>--%>
                <%--</li>--%>
                <%--<li>--%>
                <%--<div class="radio"><label>--%>
                <%--<input type="radio" name="optionsRadios" id="optionsRadios4" value="guanli" checked>管理学--%>
                <%--</label></div>--%>
                <%--</li>--%>
                <%--<li>--%>
                <%--<div class="radio"><label>--%>
                <%--<input type="radio" name="optionsRadios" id="optionsRadios5" value="yi" checked>医学--%>
                <%--</label></div>--%>
                <%--</li>--%>
                <%--<li>--%>
                <%--<div class="radio"><label>--%>
                <%--<input type="radio" name="optionsRadios" id="optionsRadios6" value="jiaoyu" checked>教育学--%>
                <%--</label></div>--%>
                <%--</li>--%>
                <%--<li>--%>
                <%--<div class="radio"><label>--%>
                <%--<input type="radio" name="optionsRadios" id="optionsRadios7" value="nong" checked>农学--%>
                <%--</label></div>--%>
                <%--</li>--%>
                <%--</ul>--%>
                <%--</li>--%>
                <br/>
                <li class="dropdown" style="margin-left:3%;display:inline-block">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        公开/私密
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <div class="radio"><label>
                                <input type="radio" name="optionsRadios" id="public" value="public" checked>公开
                            </label></div>
                        </li>
                        <li>
                            <div class="radio"><label>
                                <input type="radio" name="optionsRadios" id="private" value="private" checked>私密
                            </label></div>
                        </li>
                    </ul>
                </li>
                <br/>
                <li class="dropdown" style="margin-left:3%;display:inline-block">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        分享范围
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <% for (i = 0; i < personalInfo.getGroupNames().length; i++) { %>
                        <li>
                            <div class="checkbox"><label>
                                <input type="checkbox" name="option" value="<%= personalInfo.getGroupIDs()[i]%>">
                                <%= personalInfo.getGroupNames()[i] %>
                            </label></div>
                        </li>
                        <% } %>
                    </ul>
                </li>
                <br/><br/>
                <input class="btn btn-sm btn-primary" value="保存草稿" type="submit"/><br/>
                <input class="btn btn-sm btn-primary" value="发布" type="submit"/><br/>
                <a href="handleIndex" class="btn btn-sm btn-primary">返回</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
<script>
    function input(
        _type
    ) {
        if (_type === "上传文件") {
            var x = document.getElementById("input1");  // 找到元素
            x.innerHTML = "<div id=\"input1\">\n" +
                "                    <a class=\"btn\" onclick=\"input('编写正文')\">编写正文</a>\n" +

                "                    <label for=\"file\" class=\"sr-only\">上传文件</label>\n" +
                "                    上传文件:\n" +
                "                    <input type=\"file\" style=\"width:100px;display:inline-block\" class=\"form-control\"\n" +
                "                           placeholder=\"上传文件\" name=\"file\" id=\"file\" required/>\n" +
                "                </div>";
        } else if (_type === "编写正文") {
            var x = document.getElementById("input1");
            x.innerHTML = "<div id=\"input1\">\n" +
                "                    <a class=\"btn\" onclick=\"input('上传文件')\">上传文件</a>\n" +

                "                    <label for=\"content\" class=\"sr-only\">正文</label>\n" +
                "                    <textarea class=\"form-control\" placeholder=\"正文\" name=\"content\" rows=\"18\" id=\"content\"></textarea>\n" +
                "                </div>";
        }
    }

</script>
