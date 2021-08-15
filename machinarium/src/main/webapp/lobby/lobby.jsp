<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Lobby</title>
        <style><%@include file="/lobby/lobby_styles.css"%></style>
    </head>

    <body>
        <table id="users">
            <tr>
                <th>Username</th>
            </tr>
        </table>

        <br> <hr> <br>

        <button class="button" id="start">START</button>
        <script><%@include file="/lobby/lobby.js"%></script>
    </body>
</html>