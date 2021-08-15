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

        console.log(response);

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

        console.log(response);

        carsLst = response.cars;
        itemsLst = response.spare_items;

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
    userStats.innerHTML = "<b>First Place: " + firstPlaceCount + "<br>Second Place: "
                            + secondPlaceCount + "<br>Third Place: " + thirdPlaceCount + "<br></br>Lost: " + lossCount + "</b>";

    var pageCars = document.getElementById("cars");
    var carsAsString = "Cars:";

    for(var i = 0; i < carsLst.length; i++) {
        carsAsString = carsAsString + " " + carsLst[i].name;
    }

    pageCars.innerHTML = "<b>" + carsAsString + "</b>";

    var pageItems = document.getElementById("items");
    var itemsAsString = "Items:";
    
    for(var i = 0; i < itemsLst.length; i++) {
        itemsAsString = itemsAsString + " " + itemsLst[i].name;
    }

    pageItems.innerHTML = "<b>" + itemsAsString + "</b>";
}

// when the page is loaded, all information will be collected and page will be generated.
window.onload = function() {
    getUserInfo();
    getGarageInfo();
}

var gameButton = document.getElementById("start");

gameButton.onclick = function() {
    window.location.href = "/lobby";
}
