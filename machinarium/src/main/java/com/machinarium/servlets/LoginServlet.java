package com.machinarium.servlets;

import com.machinarium.model.user.User;
import com.machinarium.utility.common.ConfiguredLogger;
import com.machinarium.utility.common.EncryptedPassword;
import com.machinarium.dao.UserDAO;
import com.machinarium.utility.common.JSONRequest;
import com.machinarium.utility.common.JSONResponse;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.machinarium.utility.constants.RequestConstants.PARAMETER_PASSWORD;
import static com.machinarium.utility.constants.RequestConstants.PARAMETER_USER_NAME;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

	private final static Logger logger = ConfiguredLogger.getLogger("LoginServlet");

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		ServletContext contextListener = request.getServletContext();

		JSONObject data = JSONRequest.parse(request);
		JSONResponse wrappedResponse = new JSONResponse(response);

		if(data == null) {
			wrappedResponse.setStatus(response.SC_BAD_REQUEST);
			return;
		}

		String userName = (String) data.get(PARAMETER_USER_NAME);
		String password = (String) data.get(PARAMETER_PASSWORD);

		UserDAO userDao = (UserDAO) contextListener.getAttribute(Listener.ATTRIBUTE_USER_DAO);

		logger.log(Level.INFO, "Username: " + userName + "\nPassword: " + password);

		if (userName == null) {
			wrappedResponse.setError(response.SC_UNAUTHORIZED, "The user name must be specified to authenticate.");
		}
		else if(password == null) {
			wrappedResponse.setError(response.SC_UNAUTHORIZED, "The password must be specified to authenticate.");
		}
		else{
			User user = userDao.getUser(userName);

			if(user == null || !user.getPassword().equals(EncryptedPassword.of(password))) {
				wrappedResponse.setError(response.SC_UNAUTHORIZED, "The specified user name or password is incorrect.");
			} else {
				wrappedResponse.setStatus(response.SC_SEE_OTHER);
			}
		}
	}
}
