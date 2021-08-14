<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<html>
    <head>
        <style><%@include file="/login/login_styles.css"%></style>
        <title>Login</title>
    </head>

    <body>
        <div class="container" style="background-color:#007b8b">
            <label id="login_page"><b>Login page</b></label>
        </div>

        <div class="container" style="background-color:#d8abab">
            <label><b>Username</b></label>
            <input id="usrn" type="text" placeholder="Enter Username" name="user_name" required>
        </div>

        <div class="container" style="background-color:#d8abab">
            <label><b>Password</b></label>
            <input id="psw" type="password" placeholder="Enter Password" name="password" required>
        </div>

        <div class="container" style="background-color:#007b8b">
            <button class="button" id="login_button"><b>Sign in</b></button>
        </div>

        <div class="container" style="background-color:#d8abab">
            <label id="error"><b></b></label>
        </div>

        <div class="container" style="background-color:#007b8b">
            <label><b>If you don't have an account: </b></label>
            <span> <a href="register">Sign up</a></span>
        </div>

        <script><%@include file="/login/login.js"%></script>
    </body>

</html>