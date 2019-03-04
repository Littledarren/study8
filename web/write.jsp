<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="personalInfo" class="mybean.data.PersonalInfo" scope="request"/>
<%! int i; %>
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
    <%@ include file="nav.txt" %>
</head>
<body style="background-color: #00FF00">
<div class="container">

    <!--管理-->
    <div class="col-md-2" style="background-color: #FFFFFF;height:600px;border:3px solid #00FF00">
        <h4>管理</h4>
        <br/>
        <a href="#">文章管理</a><br/><br/>
        <a href="#">个人分类管理</a><br/><br/>
        <a href="#">评论管理</a><br/>
    </div>

    <!--编辑博文或上传资源-->
    <div class="col-md-8" style="background-color: #FFFFFF;height:600px;border:3px solid #00FF00">
        <h4>编辑博文</h4>
        <form action="handleWriting.java" method="POST" role="form">
            <div class="form-group">
                <br/>
                <label for="inputPassword" class="sr-only">标题</label>
                <input type="text" class="form-control" placeholder="标题" name="title" required/><br/>
                <div>
                    <label for="inputPassword" class="sr-only">上传文件</label>
                    上传文件:<input type="file" style="width:100px;display:inline-block" class="form-control"
                                placeholder="上传文件" name="title" required/></div>
                <label for="inputPassword" class="sr-only">正文</label>
                <textarea class="form-control" placeholder="正文" name="content" rows="18"></textarea>
            </div>
        </form>
    </div>

    <!--博文分类-->
    <div class="col-md-2" style="background-color: #FFFFFF;height:600px;border:3px solid #00FF00">
        <h4>分类</h4>
        <br/>
        <form action="#handleWriting.java" method="POST">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    文章类型
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <div class="radio"><label>
                            <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>原创
                        </label></div>
                    </li>
                    <li>
                        <div class="radio"><label>
                            <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2" checked>转载
                        </label></div>
                    </li>
                    <li>
                        <div class="radio"><label>
                            <input type="radio" name="optionsRadios" id="optionsRadios3" value="option3" checked>翻译
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
                            <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>论坛
                        </label></div>
                    </li>
                    <li>
                        <div class="radio"><label>
                            <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2" checked>知识理论
                        </label></div>
                    </li>
                    <li>
                        <div class="radio"><label>
                            <input type="radio" name="optionsRadios" id="optionsRadios3" value="option3" checked>经验分享
                        </label></div>
                    </li>
                    <li>
                        <div class="radio"><label>
                            <input type="radio" name="optionsRadios" id="optionsRadios3" value="option3" checked>资源分享
                        </label></div>
                    </li>
                    <li>
                        <div class="radio"><label>
                            <input type="radio" name="optionsRadios" id="optionsRadios3" value="option3" checked>考研
                        </label></div>
                    </li>
                    <li>
                        <div class="radio"><label>
                            <input type="radio" name="optionsRadios" id="optionsRadios3" value="option3" checked>就业
                        </label></div>
                    </li>
                    <li>
                        <div class="radio"><label>
                            <input type="radio" name="optionsRadios" id="optionsRadios3" value="option3" checked>其他
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
                    <% for (i = 0; i < personalInfo.getClasses().length(); i++) { %>
                    <li>
                        <div class="checkbox"><label>
                            <input type="checkbox" value="class<%=i%>">
                            <jsp:getProperty name="personalInfo" property="classes[<%=i%>]"/>
                        </label></div>
                    </li>
                    <% } %>
                </ul>
            </li>
            <br/>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    专业
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <div class="radio"><label>
                            <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>工科
                        </label></div>
                    </li>
                    <li>
                        <div class="radio"><label>
                            <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2" checked>理学
                        </label></div>
                    </li>
                    <li>
                        <div class="radio"><label>
                            <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2" checked>经济学
                        </label></div>
                    </li>
                    <li>
                        <div class="radio"><label>
                            <input type="radio" name="optionsRadios" id="optionsRadios3" value="option3" checked>管理学
                        </label></div>
                    </li>
                    <li>
                        <div class="radio"><label>
                            <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2" checked>医学
                        </label></div>
                    </li>
                    <li>
                        <div class="radio"><label>
                            <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2" checked>教育学
                        </label></div>
                    </li>
                    <li>
                        <div class="radio"><label>
                            <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2" checked>农学
                        </label></div>
                    </li>
                </ul>
            </li>
            <br/>
            <li class="dropdown" style="margin-left:3%;display:inline-block">
                <a href="#handleWriting.java?value=findGroups" class="dropdown-toggle" data-toggle="dropdown">
                    分享范围
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <div class="radio"><label>
                            <input type="radio" name="optionsRadios" id="optionsRadios1" value="公开" checked>公开
                        </label></div>
                    </li>
                    <li>
                        <div class="radio"><label>
                            <input type="radio" name="optionsRadios" id="optionsRadios2" value="私密" checked>私密
                        </label></div>
                    </li>
                    <% for (i = 0; i < personalInfo.getGroupNames().length(); i++) { %>
                    <li>
                        <div class="checkbox"><label>
                            <input type="checkbox" value="group<%=i%>">
                            <jsp:getProperty name="personalInfo" property="groupNames[<%=i%>]"/>
                        </label></div>
                    </li>
                    <% } %>
                </ul>
            </li>
            <br/>
            <a href="" class="btn">保存草稿</a>
            <button class="btn btn-sm btn-primary btn-block" type="submit">发布</a>
                <a href="index.jsp" class="btn">返回</a>
        </form>
    </div>
</body>
</html>