var registerButton = document.getElementById("register_button");

registerButton.onclick = function() {
    var username = document.getElementById("usrn");
    var password = document.getElementById("psw");
    var email = document.getElementById("email");
    var errorMessage = document.getElementById("error_message");

    var request = new XMLHttpRequest();
    request.open("POST", "/RegisterServlet");

    var data = {    
        user_name: username.value,
        password: password.value,
        email: email.value
    };

    request.onload = () => {
        var response = request.response;

        console.assert(request.status !== 400, "A malformed request was sent.");

        if(request.status === 409) {
            errorMessage.innerHTML = JSON.parse(response).message;
        } else if(request.status === 201) {
            window.location.href = "/profile";
        }
    }

    request.send(JSON.stringify(data));
}
