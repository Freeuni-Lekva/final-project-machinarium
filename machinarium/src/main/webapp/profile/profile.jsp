<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style><%@include file="/profile/profile_styles.css"%></style>
        <title>User Profile</title>
    </head>

    <body>
        <div class="container" style="background-color:#007b8b">
            <label id="profile"><b>Profile</b></label>
        </div>

        <div class="container" style="background-color:#d8abab">
            <label id="username"></label>
        </div>

        <div class="container" style="background-color:#007b8b">
            <label id="email"></label>
        </div>

        <div class="container" style="background-color:#d8abab">
            <label id="statistics"></label>
        </div>

        <div class="container" style="background-color:#007b8b">
            <label id="cars"></label>
        </div>

        <div class="container" style="background-color:#d8abab">
            <label>Spare Items:</label> <br> <hr> <br>
            <table id="items">
                <tr>
                    <th>Type</th>
                    <th>Amount</th>
                </tr>
            </table>
        </div>

        <div class="container" style="background-color:#007b8b">
            <button id="start" class="button"><b>Play</b></button>
        </div>

        <script><%@include file="/profile/profile.js"%></script>
    </body>
</html>
