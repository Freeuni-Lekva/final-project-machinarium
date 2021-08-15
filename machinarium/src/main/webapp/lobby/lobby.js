var users = [];
var role = "";
var status = "";

// get information about users
function getUsers() {
    var URL = "/LobbyServlet";
    var request = new XMLHttpRequest();
    request.open("GET", URL);

    request.onload = () => {
        var response = (request.response);
        users = response.users;
        role = response.role;
        status = response.status;

        buildCurrentPage();
    }

    request.send();
}

// clears users table
function clearTable(tableName) {
    var table = document.getElementById(tableName);
    var rowsNum = table.rows.length - 1;

    for(var i = 0; i < rowsNum; i++) {
        table.deleteRow(1);
    }
}

// this function builds user table using 
function buildUsersTable() {
    var table = document.getElementById("users");

    for(var i = 0; i < users.length; i++) {
        var row = table.insertRow(1);
        var user = row.insertCell(0);
        user.innerHTML = users[i].name;
    }
}

// if user is host, then button will be desplayed and if it is pressed game will be started
function setButtonState() {
    var button = document.getElementById("start");

    if(role !== "host") {
        button.style.display = "none";
    } else {
        button.onclick = () => {
            var URL = "/GameServlet";
            var request = new XMLHttpRequest();
            request.open("POST", URL);
            request.send();
        }
    }
}

// build page using current information
function buildCurrentPage() {
    // if game is start, all users will be redirected to the game page
    if(status === "started") {
        window.location.href = "/game";
    }

    clearTable("users");
    buildUsersTable();
    setButtonState();
}

window.onload = function() {
    getUsers();
    buildCurrentPage();
}

// refreshes page every 2.5 seconds with new information
setInterval(function(){
    getUsers();
    buildCurrentPage();
}, 2500);