package com.machinarium.servlets;

import com.machinarium.dao.UserDAO;
import com.machinarium.utility.common.*;
import com.machinarium.utility.validators.PasswordValidator;
import com.machinarium.utility.validators.UserNameValidator;
import com.machinarium.utility.validators.Validator;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.machinarium.utility.constants.RequestConstants.*;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private final static Logger logger = ConfiguredLogger.getLogger("LoginServlet");

    private static final Validator userNameValidator = UserNameValidator.getInstance();
    private static final Validator passwordValidator = PasswordValidator.getInstance();

    private static final String DATA_FIELD_MESSAGE = "message";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletContext contextListener = request.getServletContext();

        JSONObject data = JSONRequest.parse(request);
        JSONResponse wrappedResponse = new JSONResponse(response);

        if(data == null) {
            wrappedResponse.setStatus(response.SC_BAD_REQUEST);
            return;
        }

        String userName = (String) data.get(PARAMETER_USER_NAME);
        String password = (String) data.get(PARAMETER_PASSWORD);
        String email = (String) data.get(PARAMETER_EMAIL);

        logger.log(Level.INFO, "Username: " + userName + "\nPassword: " + password + "\nEmail: " + email);

        if(!userNameValidator.validate(userName)) {
            wrappedResponse.setError(response.SC_CONFLICT,
                    "The user name fails the condition: " + userNameValidator.on(userName));

        } else if(!passwordValidator.validate(password)) {
            wrappedResponse.setError(response.SC_CONFLICT,
                    "The password fails the condition: " + passwordValidator.on(password));

        } else {
            UserDAO userDao = (UserDAO) contextListener.getAttribute(ATTRIBUTE_USER_DAO);

            if (userDao.getUser(userName) != null) {
                wrappedResponse.setError(response.SC_CONFLICT,
                        "The user name \"" + userName + "\" is already used.");
            } else if (userDao.getUser(Email.of(email)) != null) {
                wrappedResponse.setError(response.SC_CONFLICT,
                        "The email \"" + email + "\" is already used.");
            } else {

                if(!userDao.addUser(userName, EncryptedPassword.of(password), email)) {
                    throw new RuntimeException("Failed to add a new user.");
                }
                wrappedResponse.setStatus(response.SC_CREATED);
            }
        }
    }

}
