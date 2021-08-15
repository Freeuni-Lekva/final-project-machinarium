package com.machinarium.servlets;

import com.machinarium.dao.OrderDAO;
import com.machinarium.dao.UserDAO;
import com.machinarium.model.user.Order;
import com.machinarium.model.user.User;
import com.machinarium.utility.common.ID;
import com.machinarium.utility.common.JSONRequest;
import com.machinarium.utility.common.JSONResponse;
import com.machinarium.utility.common.SessionManager;
import com.machinarium.utility.constants.ItemConstants;
import com.machinarium.utility.constants.OrderConstants;
import org.json.simple.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.machinarium.utility.constants.ServletConstants.*;

@WebServlet(name = "OrderServlet", value = "/OrderServlet")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        String userName = SessionManager.getLoginUser(request).getUserName();

        JSONResponse wrappedResponse = new JSONResponse(response);

        UserDAO userDAO = (UserDAO) request.getServletContext().getAttribute(ATTRIBUTE_USER_DAO);
        OrderDAO orderDAO = (OrderDAO) request.getServletContext().getAttribute(ATTRIBUTE_ORDER_DAO);

        JSONObject data = new JSONObject();

        data.put(PARAMETER_USER_ORDERS, orderDAO.getAllOrders(userName).stream().filter(order -> order.getStatus().equals(OrderDAO.ACTIVE))
                .map(Order::toJSONMap));

        data.put(PARAMETER_ORDERS, userDAO.getAllUsers().stream()
                .map(currUser -> orderDAO.getAllOrders(currUser.getUserName()).stream()
                        .filter(order -> order.getStatus().equals(OrderDAO.ACTIVE)).map(Order::toJSONMap))
                .reduce(Stream.of(), Stream::concat).collect(Collectors.toList()));

        wrappedResponse.setBody(data);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        String userName = SessionManager.getLoginUser(request).getUserName();

        OrderDAO orderDAO = (OrderDAO) request.getServletContext().getAttribute(ATTRIBUTE_ORDER_DAO);

        JSONResponse wrappedResponse = new JSONResponse(response);

        JSONObject data = JSONRequest.parse(request);
        List<Map<String, String>> source_items = (List<Map<String, String>>) data.get(OrderConstants.JSON_SRC_ITEMS);
        List<Map<String, String>> destination_items = (List<Map<String, String>>) data.get(OrderConstants.JSON_DST_ITEMS);

        Function<List<Map<String, String>>, Map<ID, Integer>> itemParser = items -> items.stream().map(item -> {

                    ID itemID = ID.of(Integer.parseInt(item.get(ItemConstants.JSON_ID)));
                    Integer amount = Integer.parseInt(item.get(ItemConstants.JSON_AMOUNT));

                    return Map.entry(itemID, amount);
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if(orderDAO.addOrder(userName, itemParser.apply(source_items), itemParser.apply(destination_items)) == null) {
            wrappedResponse.setError(HttpServletResponse.SC_CONFLICT, "Couldn't create an order for " + userName);
        } else {
            wrappedResponse.setStatus(response.SC_CREATED);
        }
    }
}
