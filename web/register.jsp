<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <title>register</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container col-md-5" style="margin:auto;margin-top:5%">

    <form autocomplete="on" action="./servlet/handleRegister.java" method="POST">
        <h2 align="center">注册</h2>

        <label class="sr-only">电子邮箱</label>
        <input type="text" class="form-control" placeholder="电子邮箱" name="email" required/><br/>

        <label class="sr-only">密码</label>
        <input type="password" class="form-control" placeholder="密码" name="password" required/><br/>

        <label class="sr-only">确认密码</label>
        <input type="password" class="form-control" placeholder="确认密码" name="confirmPassword" required/><br/>

        <label class="sr-only">昵称</label>
        <input type="text" class="form-control" placeholder="昵称" name="nickname" required/><br/>

        <label class="sr-only">性别</label>
        <input type="text" class="form-control" placeholder="性别" name="sex" required/><br/>

        <label class="sr-only">高校</label>
        <input type="text" class="form-control" placeholder="高校" name="university" required/><br/>

        <label class="sr-only">专业</label>
        <input type="text" class="form-control" placeholder="专业" name="major" required/><br/>

        <button class="btn btn-lg btn-primary btn-block" type="submit">注册</button>
    </form>
</div>
</body>
</html>