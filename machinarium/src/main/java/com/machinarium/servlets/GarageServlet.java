package com.machinarium.servlets;

import com.machinarium.dao.GarageDAO;
import com.machinarium.model.Item.Item;
import com.machinarium.model.car.Car;
import com.machinarium.model.user.User;
import com.machinarium.utility.common.ConfiguredLogger;
import com.machinarium.utility.common.JSONRequest;
import com.machinarium.utility.common.JSONResponse;
import com.machinarium.utility.common.SessionManager;
import com.machinarium.utility.constants.CarConstants;
import com.machinarium.utility.constants.ItemConstants;
import com.machinarium.utility.constants.ServletConstants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.machinarium.utility.constants.CarConstants.NAME;
import static com.machinarium.utility.constants.ServletConstants.*;

@WebServlet(name = "GarageServlet", value = "/GarageServlet")
public class GarageServlet extends HttpServlet {

    private final static Logger logger = ConfiguredLogger.getLogger("GarageServlet");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        String userName = SessionManager.getLoginUser(request).getUserName();

        GarageDAO garageDAO = (GarageDAO) request.getServletContext().getAttribute(ATTRIBUTE_GARAGE_DAO);

        JSONResponse wrappedResponse = new JSONResponse(response);

        JSONObject data = new JSONObject();

        List<Car> cars = garageDAO.getAllCars(userName);
        Map<Item, Integer> items = garageDAO.getAllSpareItems(userName);

        logger.log(Level.FINE, "User(" + userName + ") Cars: " + cars);
        logger.log(Level.FINE, "User(" + userName + ") Items: " + items);

        data.put(PARAMETER_CARS, cars.stream().map(Car::toJSONMap).collect(Collectors.toList()));

        data.put(PARAMETER_ITEMS, items.entrySet().stream()
                .map(itemEntry -> itemEntry.getKey().toJSONMap(itemEntry.getValue())).collect(Collectors.toList()));

        wrappedResponse.setResponse(response.SC_OK, data);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        String userName = SessionManager.getLoginUser(request).getUserName();

        GarageDAO garageDAO = (GarageDAO) request.getServletContext().getAttribute(ATTRIBUTE_GARAGE_DAO);

        JSONResponse wrappedResponse = new JSONResponse(response);

        String carName = (String) JSONRequest.parse(request).get(ServletConstants.PARAMETER_CAR_NAME);

        if(carName == null) {
            wrappedResponse.setError(response.SC_BAD_REQUEST, "The name of the new car must be specified.");
            return;
        }

        if(garageDAO.addEmptyCar(userName, carName) == null) {
            wrappedResponse.setError(HttpServletResponse.SC_CONFLICT, "Couldn't create a car for " + userName);
        } else {
            wrappedResponse.setStatus(response.SC_CREATED);
        }
    }
}
