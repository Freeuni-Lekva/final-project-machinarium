const GAME_SERVLET = "/GameServlet";
const GARAGE_SERVLET = "/GarageServlet";
const ORDER_SERVLET = "/OrderServlet";
const CAR_SERVLET = "/CarServlet";
const EXCHANGE_SERVLET = "/ExchangeServlet";

const USERS_TABLE = "game_users";
const MY_REQUESTS_TABLE = "requests";
const MARKET_TABLE = "market";
const MY_INVENTORY_TABLE = "inventory";


// instance variables
var usersLst = [];              // current game users
var myOrdersLst = [];           // my orders
var activeOrdersLst = [];       // market orders
var mySpareItemsLst = [];       // unused items from my garage
var myCarsLst = [];             // my cars
var allItemsLst = [];           // all items and connectors user can have
var curCarItemOptions = "";     // my cars options for now


// collects info and builds the page
function generatePage() {
    askForUsersAndItems();
    askForGarageInfo();
    askForOrdersInfo();
}

// creates connection to the gameservlet and stores the lists of all users and my items
function askForUsersAndItems() {
    var request = new XMLHttpRequest();
    request.open("GET", GAME_SERVLET);

    request.onload = () => {
        var response = JSON.parse(request.response);
        allItemsLst = response.items.sort();
        usersLst = response.users.sort();
        buildPage();
    }

    request.send();
}

// callects information about user's cars and spare items
function askForGarageInfo() {
    var request = new XMLHttpRequest();
    request.open("GET", GARAGE_SERVLET);

    request.onload = () => {
        var response = JSON.parse(request.response);
        myCarsLst = response.cars.sort();
        mySpareItemsLst = response.items.sort();
        buildPage();
    }

    request.send();
}

// collects information about my orders and all active orders
function askForOrdersInfo() {
    var request = new XMLHttpRequest();
    request.open("GET", ORDER_SERVLET);

    request.onload = () => {
        var response = JSON.parse(request.response);
        myOrdersLst = response.user_orders.sort();
        activeOrdersLst = response.orders.sort();
        buildPage();
    }

    request.send();
}

// builds page using collected information
function buildPage() {
    clearTables();
    buildUsersTable();
    buildMyRequestsTable();
    buildMarketTable();
    buildMyInventoryTable();
    buildMyCarsDropDown();
}

function clearTables() {
    clearSingleTable(USERS_TABLE);
    clearSingleTable(MY_REQUESTS_TABLE);
    clearSingleTable(MARKET_TABLE);
    clearSingleTable(MY_INVENTORY_TABLE);
}

function clearSingleTable(tableId) {
    var table = document.getElementById(tableId);
    var rowsNum = table.rows.length - 1;

    for(var i = 0; i < rowsNum; i++) {
        table.deleteRow(1);
    }
}

// diplays information in the my requests table
function buildMyRequestsTable() {
    var table = document.getElementById(MY_REQUESTS_TABLE);

    for(var i = 0; i < myOrdersLst.length; i++) {
        var row = table.insertRow(1);
        var srcItems = row.insertCell(0);
        var dstItems = row.insertCell(1);
        var user = row.insertCell(2);
        
        var curOrder = myOrdersLst[i];

        var curSrcItems = curOrder.source_items;
        var srcItemsText = "";
        for(var j = 0; j < curSrcItems.length; j++) {
            var curItem = curSrcItems[j];
            var name = curItem.name;
            var amount = curItem.amount;
            srcItemsText = srcItemsText  + name + ": " + amount + "<br>";
        }
        srcItems.innerHTML = srcItemsText;

        var curDstItems = curOrder.destination_items;
        var dstItemsText = "";
        for(var j = 0; j < curDstItems.length; j++) {
            var curItem = curDstItems[j];
            var name = curItem.name;
            var amount = curItem.amount;
            dstItemsText = dstItemsText  + name + ": " + amount + "<br>";
        }
        dstItems.innerHTML = dstItemsText;

        user.innerHTML = curOrder.user;
    }
}

// dispays all active orders in the market table
function buildMarketTable() {
    var table = document.getElementById("market");

    for(var i = 0; i < activeOrdersLst.length; i++) {
        var row = table.insertRow(1);
        var srcItems = row.insertCell(0);
        var dstItems = row.insertCell(1);
        var user = row.insertCell(2);
        var acceptButton = row.insertCell(3);

        var curOrder = activeOrdersLst[i];

        var curSrcItems = curOrder.source_items;
        var srcItemsText = "";
        for(var j = 0; j < curSrcItems.length; j++) {
            var curItem = curSrcItems[j];
            var name = curItem.name;
            var amount = curItem.amount;
            srcItemsText = srcItemsText  + name + ": " + amount + "<br>";
        }
        srcItems.innerHTML = srcItemsText;
        console.log("Source Items text: " + srcItemsText);

        var curDstItems = curOrder.destination_items;
        var dstItemsText = "";
        for(var j = 0; j < curDstItems.length; j++) {
            var curItem = curDstItems[j];
            var name = curItem.name;
            var amount = curItem.amount;
            dstItemsText = dstItemsText  + name + ": " + amount + "<br>";
        }
        dstItems.innerHTML = dstItemsText;
        console.log("Destination Items text: " + dstItemsText);

        user.innerHTML = curOrder.user;

        acceptButton.innerHTML = "<button class=\"button\" id=\"exchange_" + i + "\"><b>Accept</b></button>"

        var buttonObj = document.getElementById("exchange_" + i);

        buttonObj.onclick = () => {
            var request = new XMLHttpRequest();
            request.open("POST", EXCHANGE_SERVLET);

            request.onload = () => {
                console.log("request accepted");
            }

            request.send(JSON.stringify(curOrder));
        }
    }
}

// displays information in the my inventory table
function buildMyInventoryTable() {
    var table = document.getElementById("inventory");

    for(var i = 0; i < mySpareItemsLst.length; i++) {
        var row = table.insertRow(1);
        var itemName = row.insertCell(0);
        var amount = row.insertCell(1);

        itemName.innerHTML = mySpareItemsLst[i].name;
        amount.innerHTML = mySpareItemsLst[i].amount;
    }
}

// builds my cars dropdown element in car builder pop up window
function buildMyCarsDropDown() {
    var elem = document.getElementById("selected_car");

    var options = "";
    for(var i = 0; i < myCarsLst.length; i++) {
        var curCar = myCarsLst[i];
        options = options + "<option value=\"" + curCar.id + "\">" + curCar.name + "</option>";
    }

    elem.innerHTML = options;
}

// displays game users in the users table
function buildUsersTable() {
    var table = document.getElementById("game_users");

    for(var i = 0; i < usersLst.length; i++) {
        var row = table.insertRow(1);
        var curUser = row.insertCell(0);
        curUser.innerHTML = "<i>" + usersLst[i].user_name + "</i>";
    }
}

window.onload = function() {
    generatePage();
    addCarSelectorListener();
}

setInterval(function(){
    generatePage();
}, 2500);


// pop up windows
var inventoryModal = document.getElementById("inventory_window");  // inventory window
var inventoryPopup = document.getElementById("inventory_popup");   // inventory button
var requestModal = document.getElementById("request_window");      // request builder window
var requestPopup = document.getElementById("request_popup");       // request builder button
var builderModal = document.getElementById("cars_window");         // car builder window
var builderPopup = document.getElementById("cars_popup");          // car builder button
var inventoryClose = document.getElementsByClassName("close")[0];  // inventory window close button
var requestClose = document.getElementsByClassName("close")[1];    // request builder window close button
var builderClose = document.getElementsByClassName("close")[2];    // car builder window close button

inventoryPopup.onclick = function() {
    inventoryModal.style.display = "block";
}

inventoryClose.onclick = function() {
    inventoryModal.style.display = "none";
    requestModal.style.display = "none";
    builderModal.style.display = "none";
}

requestPopup.onclick = function() {
    requestModal.style.display = "block";
}

requestClose.onclick = function() {
    inventoryModal.style.display = "none";
    requestModal.style.display = "none";
    builderModal.style.display = "none";
}

builderPopup.onclick = function() {
    builderModal.style.display = "block";
}

builderClose.onclick = function() {
    inventoryModal.style.display = "none";
    requestModal.style.display = "none";
    builderModal.style.display = "none";
}


window.onclick = function(event) {
    if (event.target == requestModal || event.target == inventoryModal || event.table == builderModal) {
        inventoryModal.style.display = "none";
        requestModal.style.display = "none";
        builderModal.style.display = "none";
    }
}

var testDropDown = [{id: "bla", name: "vaso"}, {id: "blu", name: "jano"}];

function buildOptionsText(items) {
    var result = "";
    for(var i = 0; i < items.length; i++) {
        result = result + "<option value=\"" + items[i].id + "\">" +  items[i].name + "</option>";
    }

    return result;
}

function buildDropDown(idPrefix, n, items) {
    var result = "<select id=\"";
    result = result + idPrefix + n;
    result = result + "\">";
    result = result + buildOptionsText(items);
    result = result + "</select>";
    return result;
}

// if add item button is clicked, then new field will be shown, which can be filled by user
function addSrcItem() {
    var srcTable = document.getElementById("src_table");
    var row = srcTable.insertRow(1);
    var item = row.insertCell(0);
    var num = row.insertCell(1);
    var desc = row.insertCell(2);
    var numRows = srcTable.rows.length - 1;
    item.innerHTML = buildDropDown("src_item_", numRows, mySpareItemsLst);
    // item.innerHTML = "<select name=\"src_items\" id=\"src_item_" + numRows + "\"><option value=\"dst_wheel\">Wheel</option><option value=\"dst_chassis\">Chassis</option><option value=\"dst_body\">Door</option><option value=\"dst_ngine\">Engine</option></select>";
    num.innerHTML = "<input id=\"src_num_" + numRows + "\"type=\"number\" placeholder=\"How many\" value=\"\" />";
    desc.innerHTML = "<label>Descr...</label>";

    // if(numRows > 2) {
    //     document.getElementById('src_item_1').addEventListener('change', function() {
    //         console.log('You selected: ', this.value);
    //     });
    // }
}

// if remove button is clicked, top row will be removed
function rmvSrcItem() {
    var srcTable = document.getElementById("src_table");
    srcTable.deleteRow(1);
}


// if add item button is clicked, then new field will be shown, which can be filled by user
function addDestItem() {
    var label = document.getElementById("dst_number");
    var tester = document.getElementById("tester");
    var destTable = document.getElementById("dest_table");
    var row = destTable.insertRow(1);
    var item = row.insertCell(0);
    var num = row.insertCell(1);
    var desc = row.insertCell(2);
    var rowsNum = (destTable.rows.length - 1);

    item.innerHTML = buildDropDown("dest_item_", rowsNum, allItemsLst);
    // item.innerHTML = "<select name=\"dest_items\" id=\"dest_item_" + rowsNum + "\"><option value=\"dst_wheel\">Wheel</option><option value=\"dst_chassis\">Chassis</option><option value=\"dst_body\">Door</option><option value=\"dst_ngine\">Engine</option></select>";
    num.innerHTML = "<input id=\"dest_num_" + rowsNum + "\"type=\"number\" placeholder=\"How many\" value=\"\" />";
    desc.innerHTML = "<label>Descr...</label>";
    
    if(rowsNum > 3) {
        // var objCells = destTable.rows.item(1).cells;
        // var str = objCells.item(1).innerHTML;
        // label.innerHTML = "rows number: " + str;
        var cur_item = document.getElementById("dest_item_2");
        tester.innerHTML = cur_item.value;
    }

    label.innerHTML = "rows number: " + rowsNum;
}

// if remove button is clicked, top row will be removed
function rmvDestItem() {
    var destTable = document.getElementById("dest_table");
    destTable.deleteRow(1);
}

// send request to servlet and add new order in the order list table
function doneRequest() {
    var srcTable = document.getElementById("src_table");
    var destTable = document.getElementById("dest_table");

    var sourceLst = [];

    console.log("Source:\n");
    
    for(var i = 1; i < srcTable.rows.length; i++) {
        var output = "item_type: ";
        var item = document.getElementById("src_item_" + i);
        output = output + item.value;
        var number = document.getElementById("src_num_" + i);
        output = output + " number: " + number.value;
        console.log(output);
        var curElem = {
            id: item.value,
            amount: number.value
        };
        sourceLst.push(curElem);
    }


    var destLst = [];
    console.log("Destination:\n");

    for(var i = 1; i < destTable.rows.length; i++) {
        var output = "item_type: ";
        var item = document.getElementById("dest_item_" + i);
        output = output + item.value;
        var number = document.getElementById("dest_num_" + i);
        output = output + " number: " + number.value;
        console.log(output);
        var curElem = {
            id: item.value,
            amount: number.value
        };
        destLst.push(curElem);
    }

    var request = new XMLHttpRequest();

    var data = {
        source_items: sourceLst,
        destination_items: destLst
    };

    request.open("POST", ORDER_SERVLET);
    request.send(JSON.stringify(data));

    console.log(JSON.stringify(data));
}

// sends new car name to the corresponding servlet
function createNewCar() {
    var request = new XMLHttpRequest();
    request.open("POST", GAME_SERVLET);

    var carName = document.getElementById("new_car_name");

    var data = {
        name: carName.value
    };

    console.log(JSON.stringify(data));
    request.send(JSON.stringify(data));
}

function buildCarItemsDropDown() {
    var currentCarItems = [];
    var selectedCar = document.getElementById("selected_car");

    var request = new XMLHttpRequest();
    request.open("POST", CAR_SERVLET);

    data = {
        id: selectedCar.value
    };

    request.onload = () => {
        var response = JSON.parse(request.response);
        currentCarItems = response.items;
    }

    var options = "";

    for(var i = 0; i < currentCarItems.length; i++) {
        var curItem = currentCarItems[i];
        options = options + "<option value=\"" + curItem.id + "\">" + curItem.name + "</option>";
    }

    curCarItemOptions = options;
}

function addCarSelectorListener() {
    buildCarItemsDropDown();
    var carSelector = document.getElementById("selected_car");
    carSelector.innerHTML = curCarItemOptions;
    carSelector.addEventListener('change', function() {
        buildCarItemsDropDown();
        clearSingleTable("car_builder");
        console.log('You selected: ', this.value);
    });
}

function putItemInCar() {
    var table = document.getElementById("car_builder");

    var row = table.insertRow(1);
    var item = row.insertCell(0);
    var method = row.insertCell(1);

    var rowsNum = table.rows.length - 1;

    item.innerHTML = buildDropDown("builder_", rowsNum, mySpareItemsLst);
    method.innerHTML = "<label id=\"label_" + numRows + "\">add</label>";
}

function removeItemFromCar() {
    var table = document.getElementById("car_builder");

    var row = table.insertRow(1);
    var item = row.insertCell(0);
    var method = row.insertCell(1);

    var rowsNum = table.rows.length - 1;

    var currentCar = document.getElementById("selected_car");

    var request = new XMLHttpRequest();
    request.open("post", CAR_SERVLET);
}

function removeRow() {
    var table = document.getElementById("car_builder");
    table.deleteRow(1);
}

// car building is done and changes information is sent to the servlet
function doneBuilding() {
    var table = document.getElementById("car_builder");

    var rowsNUm = table.rows.length - 1;

    var addLst = [];
    var rmvLst = [];
    for(var i = 0; i < numRows; i++) {
        var item = document.getElementById("builder_" + i);
        var method = document.getElementById("label_" + i);
        if(method.textContent === "add") {
            addLst.push(item.value);
        } else {
            rmvLst.push(item.value);
        }
    }

    var request = new XMLHttpRequest();
    request.open("POST", CAR_SERVLET);

    var data = {
        add: addLst,
        remove: rmvLst,
        car: document.getElementById("selected_car").value
    };

    request.send(JSON.stringify(data));
}
