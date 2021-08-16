package com.machinarium.servlets;

import com.machinarium.dao.CarDAO;
import com.machinarium.dao.GarageDAO;
import com.machinarium.model.Item.Item;
import com.machinarium.model.car.Car;
import com.machinarium.utility.common.*;
import com.machinarium.utility.constants.ServletConstants;
import org.json.simple.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.machinarium.utility.constants.ServletConstants.ATTRIBUTE_GARAGE_DAO;

@WebServlet(name = "CarServlet", value = "/CarServlet")
public class CarServlet extends HttpServlet {

    private final static Logger logger = ConfiguredLogger.getLogger("CarServlet");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        String userName = SessionManager.getLoginUser(request).getUserName();

        ServletContext context = request.getServletContext();
        CarDAO carDAO = (CarDAO) context.getAttribute(ServletConstants.ATTRIBUTE_CAR_DAO);

        JSONResponse wrappedResponse = new JSONResponse(response);
        Integer carID = (Integer) JSONRequest.parse(request).get(ServletConstants.PARAMETER_CAR_ID);

        if(carID == null) {
            wrappedResponse.setError(response.SC_BAD_REQUEST, "The id of the car must be specified.");
            return;
        }

        logger.log(Level.INFO, "Sending car part information for car with ID \"" + carID + "\" to user(" + userName + ").");

        List<Item> carItems = carDAO.getAllCarItems(ID.of(carID));

        logger.log(Level.INFO, "List of car parts: " + carItems);

        JSONObject data = new JSONObject();
        data.put(ServletConstants.PARAMETER_ITEMS, carItems.stream().map(Item::toJSONMap).collect(Collectors.toList()));

        wrappedResponse.setResponse(response.SC_OK, data);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userName = SessionManager.getLoginUser(request).getUserName();

        ServletContext context = request.getServletContext();
        CarDAO carDAO = (CarDAO) context.getAttribute(ServletConstants.ATTRIBUTE_CAR_DAO);
        GarageDAO garageDAO = (GarageDAO) request.getServletContext().getAttribute(ATTRIBUTE_GARAGE_DAO);

        JSONResponse wrappedResponse = new JSONResponse(response);
        JSONObject data = JSONRequest.parse(request);

        Integer carID = (Integer) data.get(ServletConstants.PARAMETER_CAR_ID);
        List<String> addItems = (List<String>) data.get(ServletConstants.PARAMETER_ADD_ITEM);
        List<String> removeItems = (List<String>) data.get(ServletConstants.PARAMETER_REMOVE_ITEM);

        if(carID == null) {
            wrappedResponse.setError(response.SC_BAD_REQUEST, "The id of the car must be specified.");
            return;
        }

        if(addItems == null || removeItems == null) {
            wrappedResponse.setError(response.SC_BAD_REQUEST, "The items to be added/removed must be specified.");
            return;
        }

        if(garageDAO.getAllCars(userName).stream().map(Car::getID).noneMatch(id -> id.equals(ID.of(carID)))) {
            wrappedResponse.setError(response.SC_NOT_FOUND, "The car with the specified ID doesn't exist in the user's garage.");
            return;
        }

        List<Item> carItems = carDAO.getAllCarItems(ID.of(carID));
        logger.log(Level.INFO, "List of car current parts: " + carItems);


        List<ID> missingItems = addItems.stream().map(itemID -> ID.of(Integer.valueOf(itemID)))
                .filter(itemID -> garageDAO.hasThisSpareItem(userName, itemID)).collect(Collectors.toList());

//        List<ID> duplicateItems = addItems.stream().map(itemID -> ID.of(Integer.valueOf(itemID))).
//                filter(itemID)
//
        if(missingItems.size() != 0) {
            wrappedResponse.setError(response.SC_PRECONDITION_FAILED,
                    "The following items are missing from the users garage: " + missingItems);
            return;
        }




        JSONObject responseData = new JSONObject();
        responseData.put(ServletConstants.PARAMETER_ITEMS, carItems.stream().map(Item::toJSONMap).collect(Collectors.toList()));

        wrappedResponse.setResponse(response.SC_OK, data);
    }
}
