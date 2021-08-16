var username = "";
var email = "";
var firstPlaceCount = "";
var secondPlaceCount = "";
var thirdPlaceCount = "";
var lossCount = "";
var carsLst = [];
var itemsLst = [];

// creates connection with UserServlet and collects information about authorized user
function getUserInfo() {
    var URL = "/UserServlet";

    var request = new XMLHttpRequest();
    request.open("GET", URL);

    request.onload = () => {
        var response = JSON.parse(request.response);

        username = response.user_name;
        email = response.email;
        firstPlaceCount = response.first_place_count;
        secondPlaceCount = response.second_place_count;
        thirdPlaceCount = response.third_place_count;
        lossCount = response.loss_count;

        buildPage();
    }

    request.send();
}

// creates connection with GarageServlet and collects information about authorized user's garage
function getGarageInfo() {
    var URL = "/GarageServlet";

    var request = new XMLHttpRequest();
    request.open("GET", URL);

    request.onload = () => {
        var response = JSON.parse(request.response);

        carsLst = response.cars;
        itemsLst = response.items;
        buildPage();
    }

    request.send();
}

// builds page using collected information
function buildPage() {
    var pageUsername = document.getElementById("username");
    pageUsername.innerHTML = "<b>Username: " + username + "</b>";

    var pageEmail = document.getElementById("email");
    pageEmail.innerHTML =  "<b>email: " + email + "</b>";

    var userStats = document.getElementById("statistics");
    userStats.innerHTML = "<b>Statistics:</b><br>" +
        "First place: <i>" + firstPlaceCount +
        "</i><br>Second place: <i>" + secondPlaceCount +
        "</i><br>Third place <i>"+ thirdPlaceCount +
        "</i><br> Lost <i>" + lossCount + "</i>";

    var pageCars = document.getElementById("cars");
    var carsAsString = "Cars:";

    for(var i = 0; i < carsLst.length; i++) {
        carsAsString = carsAsString + " " + carsLst[i].name;
    }

    pageCars.innerHTML = "<b>" + carsAsString + "</b>";

    var pageItems = document.getElementById("items");

    for(var i = 0; i < itemsLst.length; i++) {
        var row = pageItems.insertRow(1);
        var itemType = row.insertCell(0);
        var amount = row.insertCell(1);
        itemType.innerHTML = itemsLst[i].name;
        amount.innerHTML = itemsLst[i].amount;
    }
}

// when the page is loaded, all information will be collected and page will be generated correspondly
window.onload = function() {
    getUserInfo();
    getGarageInfo();
}

var gameButton = document.getElementById("start");

gameButton.onclick = function() {
    var URL = "/LobbyServlet"
    var request = new XMLHttpRequest();
    request.open("POST", URL);

    request.onload = () => {
        window.location.href = "/lobby";
    }

    request.send();
}
