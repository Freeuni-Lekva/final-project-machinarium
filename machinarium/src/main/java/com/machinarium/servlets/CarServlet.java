package com.machinarium.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CarServlet", value = "/CarServlet")
public class CarServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setStatus(response.SC_SERVICE_UNAVAILABLE);
    }
}
