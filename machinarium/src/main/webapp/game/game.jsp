<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <style><%@include file="/game/game_styles.css"%></style>
        <title>Game</title>
    </head>
    
    <body>

        <div class="container">

            <!-- game users table -->
            <div id="users_content">
                <label><b>Users</b></label> <br>
                <table id="game_users">
                    <tr>
                        <th><b>Username</b></th>
                    </tr>
                </table>
            </div>

            <!-- my requests table -->
            <div id="my_requests_content">
                <label><b>My Orders</b></label> <br>
                <table id="requests">
                    <tr>
                        <th>Source</th>
                        <th>Destination</th>
                        <th>Author</th>
                    </tr>
                </table>
            </div>
            
            
            <!-- market table -->
            <div id="market_content">
                <label><b>Market</b></label> <br>
                <table id="market">
                    <tr>
                        <th>Source</th>
                        <th>Destination</th>
                        <th>Author</th>
                        <th>Exchange</th>
                    </tr>
                </table>
            </div>
        </div>
        


<!----------------------------------------------------------------------------->

<!----------------------------------------------------------------------------->

        <div class="container" id="my_invent">

            <!-- inventory pop up window -->

            <div id="invent_content">
                <button id="inventory_popup"><b>My Inventory</b></button>

                <div id="inventory_window" class="modal">

                    <div class="modal-content">
                        <span class="close" id="inventory_close_button">&times;</span>
                        <div style="text-align: center;" id="inventory_close">
                            <label><b>Inventory</b></label> <br>
                            <table id="inventory">
                                <tr>
                                    <th>Item</th>
                                    <th>Quantity</th>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <!-- request builder pop up window -->

            <div id="request_content">
                <button id="request_popup"><b>Make Request</b></button>
    
                <div id="request_window" class="modal">
    
                    <div class="modal-content">
                        <span class="close" id="request_close_button">&times;</span>
                        <div style="text-align: center;" id="request_close">
                            <div>
                                <label><b>გამაქვს:</b></label> <br> <hr> <br>
                                <table id="src_table">
                                    <tr>
                                        <th>Item</th>
                                        <th>Quantity</th>
                                        <th>Description</th>
                                    </tr>
                                </table>

                                <!-- Add item button -->
                                <button id="src_add" class="button" onclick="addSrcItem()"><b>მეტი</b></button>
                                <!-- Remove item button -->
                                <button id="src_remove" class="button" onclick="rmvSrcItem()"><b>ნაკლები</b></button>
                                <label id="src_number"></label>
                            </div>

                            <div>
                                <label><b>მინდა:</b></label> <br> <hr> <br>
                                <table id="dest_table">
                                    <tr>
                                        <th>Item</th>
                                        <th>Quantity</th>
                                        <th>Description</th>
                                    </tr>
                                </table>

                                <!-- Add item button -->
                                <button id="dest_add" class="button" onclick="addDestItem()"><b>მეტი</b></button>
                                <!-- Remove item button -->
                                <button id="dest_remove" class="button" onclick="rmvDestItem()"><b>ნაკლები</b></button>
                                <br> <hr> <br>
                                <button class="button" id="done" onclick="doneRequest()">მზადაა</button>
                                <label id="dst_number"></label>
                                <label id="tester"></label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            

            <!-- car builder pop up window -->

            <div id="cars_content">
                <button id="cars_popup"><b>Build Car</b></button>

                <div id="cars_window" class="modal">

                    <div class="modal-content">
                        <span class="close" id="cars_close_button">&times;</span>
                        <div style="text-align: center;" id="cars_close">
                            <!-- window input -->
                            <div class="container" id="create_car_container">
                                <label id="new_car_label"><b>Enter new car name: </b></label>
                                <input type="text" id="new_car_name" value="" />
                                <button class="button" id="new_car_button" onclick="createNewCar()">CREATE</button>
                            </div>
                            
                            <br> <hr>

                            <!------------------>

                            <div id="builder_container">
                                <!-- car selector -->
                                <div>
                                    <select id="selected_car"></select>
                                </div>

                                <!-- put/remove item -->
                                <div>
                                    <table id="car_builder">
                                        <tr>
                                            <th><b>Item</b></th>
                                            <th><b>Method</b></th>
                                        </tr>
                                    </table>
                                </div>

                                <div class="container">
                                    <button class="button" id="put_item" onclick="putItemInCar()">PUT</button>
                                    <button class="button" id="remove_item" onclick="removeItemFromCar()">REMOVE</button>
                                    <button class="button" id="remove_row" onclick="removeRow()">PREVIOUS</button>
                                </div>
                                <div class="container">
                                    <button class="button" id="add_car_button" onclick="doneBuilding()">ADD CAR</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <script><%@include file="/game/game.js"%></script>
    </body>
</html>
