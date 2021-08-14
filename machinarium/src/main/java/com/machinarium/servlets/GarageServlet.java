package com.machinarium.servlets;

import com.machinarium.dao.GarageDAO;
import com.machinarium.model.Item.Item;
import com.machinarium.model.car.Car;
import com.machinarium.model.user.User;
import com.machinarium.utility.common.JSONRequest;
import com.machinarium.utility.common.JSONResponse;
import com.machinarium.utility.constants.CarConstants;
import com.machinarium.utility.constants.ItemConstants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.machinarium.utility.constants.CarConstants.NAME;
import static com.machinarium.utility.constants.ServletConstants.*;

@WebServlet(name = "GarageServlet", value = "/GarageServlet")
public class GarageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        assert user != null;

        String userName = user.getUserName();

        GarageDAO garageDAO = (GarageDAO) request.getServletContext().getAttribute(ATTRIBUTE_GARAGE_DAO);

        JSONResponse wrappedResponse = new JSONResponse(response);

        JSONObject data = new JSONObject();

        data.put(PARAMETER_CARS, garageDAO.getAllCars(userName).stream().map(Car::toMap).collect(Collectors.toList()));
        data.put(PARAMETER_SPARE_ITEMS, garageDAO.getAllSpareItems(userName).stream().map(Item::toMap).collect(Collectors.toList()));

        wrappedResponse.setBody(data);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        assert user != null;

        String userName = user.getUserName();

        GarageDAO garageDAO = (GarageDAO) request.getServletContext().getAttribute(ATTRIBUTE_GARAGE_DAO);

        JSONResponse wrappedResponse = new JSONResponse(response);

        JSONObject data = JSONRequest.parse(request);
        String carName = (String) data.get(PARAMETER_CAR_NAME);

        if(garageDAO.addEmptyCar(userName, carName) == null) {
            wrappedResponse.setError(HttpServletResponse.SC_CONFLICT, "Couldn't create a car for " + userName);
        } else {
            wrappedResponse.setStatus(response.SC_CREATED);
        }
    }
}
