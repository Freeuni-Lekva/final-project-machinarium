package com.machinarium.servlets;

import java.io.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class HelloServlet extends HttpServlet {

	private static final 

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext contextListener = request.getServletContext();

		String userName = request.getParameter("user_name");
		String password = request.getParameter("password");


		AccountManager accountManager = (AccountManager) contextListener.getAttribute("account_manager");

		if(accountManager.isCorrectPassword(userName, password)) request.getRequestDispatcher("/welcome.jsp").forward(request, response);
		else response.sendRedirect("incorrect-information.jsp");
	}