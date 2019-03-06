<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="login" type="mybean.data.Login" scope="session"/>
<!doctype html>
<html>
<head>
    <title>loginFail</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container col-md-5" style="margin:auto;margin-top:10%">

    <form autocomplete="on" action="./servlet/handleLogin" method="POST">
        <h2 align="center">登录</h2>

        <label for="inputAccount" class="sr-only">账号</label>
        <input type="text" id="inputAccount" class="form-control" placeholder="账号" name="account" required
               autofocus/><br/>

        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="密码" name="password" required/><br/>

        <!--判断是否是登陆失败后跳转过来的页面-->
        <% if (login.isSuccess() == false) { %>
        <p style="color:#FF0000">您输入的账号或密码错误，请重新输入</p>
        <% } %>

        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
        <br/>
        <a href="register.jsp" style="margin-left:5px"><u>注册</u></a>
        <a href="#" style="margin-left:50px"><u>退出登录</u></a>
    </form>
</div>
</body>
</html>