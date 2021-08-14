package com.machinarium.servlets;

import com.machinarium.dao.OrderDAO;
import com.machinarium.dao.UserDAO;
import com.machinarium.model.user.User;
import com.machinarium.utility.common.JSONResponse;
import org.json.simple.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.machinarium.utility.constants.ServletConstants.*;

@WebServlet(name = "OrderServlet", value = "/OrderServlet")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        assert user != null;

        String userName = user.getUserName();

        JSONResponse wrappedResponse = new JSONResponse(response);

        UserDAO userDAO = (UserDAO) request.getServletContext().getAttribute(ATTRIBUTE_USER_DAO);
        OrderDAO orderDAO = (OrderDAO) request.getServletContext().getAttribute(ATTRIBUTE_ORDER_DAO);

        JSONObject data = new JSONObject();
        data.put(PARAMETER_USER_ORDERS, orderDAO.getAllOrders(userName));
        data.put(PARAMETER_ORDERS, userDAO.getAllUsers().stream().map(currUser -> orderDAO.getAllOrders(currUser.getUserName()))
                .reduce(List.of(), (prevList, currList) -> Stream.concat(prevList.stream(), currList.stream()).collect(Collectors.toList())));

        wrappedResponse.setBody(data);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
