var loginButton = document.getElementsByClassName("button")[0];

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

        if(request.status === 401) {
            window.location.href = "https://www.youtube.com/watch?v=TwTj0226zm4";
        } else if(request.status === 303) {
            errorMessage.innerHTML = "Jigarsoooooooooon: \n" + response;
        }
    }

    request.send(JSON.stringify(data));

}