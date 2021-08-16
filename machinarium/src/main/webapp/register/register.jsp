<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style><%@include file="/register/register_styles.css"%></style>
        <title>Register</title>
    </head>

    <body>
            
        <div class="container" style="background-color:#007b8b">
            <label><b>Register page</b></label> 
        </div>
        
        <div class="container" style="background-color:#d8abab">
            <label><b>Username</b></label>
            <input id="usrn" type="text" placeholder="Enter Username" name="user_name" value="" required>
        </div>

        <div class="container" style="background-color:#d8abab">
            <label><b>Password</b></label>
            <input id="psw" type="password" placeholder="Enter Password" name="password" value="" required>
        </div>

        <div class="container" style="background-color:#d8abab">
            <label"><b>Email</b></label>
            <input id="email" type="text" placeholder="Enter Email" name="email" value="" required>
        </div>
        
        <div class="container" style="background-color:#007b8b">
            <button type="submit" class="button" id="register_button"><b>Sign Up</b></button>
        </div>

        <div class="container" style="background-color:#d8abab">
            <label id="error_message"><b></b></label>
        </div>

        <script><%@include file="/register/register.js"%></script>
    </body>
</html>
