package com.machinarium.servlets;

import com.machinarium.model.user.User;
import com.machinarium.dao.UserDAO;
import com.machinarium.utility.validators.PasswordValidator;
import com.machinarium.utility.validators.UserNameValidator;
import com.machinarium.utility.validators.Validator;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.machinarium.servlets.RequestConstants.*;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private static final Validator userNameValidator = UserNameValidator.getInstance();
    private static final Validator passwordValidator = PasswordValidator.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletContext contextListener = request.getServletContext();

        String userName = request.getParameter(PARAMETER_USER_NAME);
        String password = request.getParameter(PARAMETER_PASSWORD);

        if(!userNameValidator.validate(userName)) {
            response.sendError(response.SC_CONFLICT, "The user name fails the condition: " + userNameValidator.on(userName));
            return;
        }

        if(!passwordValidator.validate(password)) {
            response.sendError(response.SC_CONFLICT, "The password fails the condition: " + passwordValidator.on(password));
            return;
        }

        User newUser = new User(
                request.getParameter(PARAMETER_USER_NAME),
                request.getParameter(PARAMETER_PASSWORD),
                request.getParameter(PARAMETER_EMAIL)
        );

        UserDAO userDao = (UserDAO) contextListener.getAttribute(Listener.ATTRIBUTE_USER_DAO);

        if (userDao.addUser(newUser)) {
            response.setStatus(response.SC_CREATED);
        } else {
            response.sendError(response.SC_CONFLICT,  "The user name \"" + newUser.getUserName() + "\" is already used.");
        }
    }
}
