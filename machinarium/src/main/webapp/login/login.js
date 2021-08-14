var loginButton = document.getElementById("login_button");

loginButton.onclick = function() {
    var username = document.getElementById("usrn");
    var password = document.getElementById("psw");
    var errorMessage = document.getElementById("error");

    var request = new XMLHttpRequest();
    request.open("POST", "/LoginServlet");

    var data = {
        user_name: username.value,
        password: password.value
    };

    request.onload = () => {
        var response = request.response;

        console.assert(request.status !== 400, "A malformed request was sent.");

        if(request.status === 401) {
            errorMessage.innerHTML = JSON.parse(response).message;
        } else if(request.status === 303) {
            window.location.href = "https://www.youtube.com/watch?v=TwTj0226zm4";
        }
    }

    request.send(JSON.stringify(data));
}