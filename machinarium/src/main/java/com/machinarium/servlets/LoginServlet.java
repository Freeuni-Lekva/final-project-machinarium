package com.machinarium.servlets;

import com.machinarium.common.RequestBody;
import com.machinarium.utility.EncryptedPassword;
import com.machinarium.dao.UserDAO;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.machinarium.servlets.RequestConstants.PARAMETER_PASSWORD;
import static com.machinarium.servlets.RequestConstants.PARAMETER_USER_NAME;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		ServletContext contextListener = request.getServletContext();

		JSONObject data = RequestBody.parse(request);

		if(data == null) {
			response.sendError(response.SC_BAD_REQUEST);
			return;
		}

		String userName = (String) data.get(PARAMETER_USER_NAME);
		String password = (String) data.get(PARAMETER_PASSWORD);

		System.out.println("Username: " + userName + "\nPassword: " + password);

		UserDAO userDao = (UserDAO) contextListener.getAttribute(Listener.ATTRIBUTE_USER_DAO);

		if (userName == null) {
			response.sendError(response.SC_UNAUTHORIZED, "The user name must be specified to authenticate.");
		}
		else if(password == null) {
			response.sendError(response.SC_UNAUTHORIZED, "The password must be specified to authenticate.");
		}
		else if(!userDao.getUser(userName).getPassword().equals(EncryptedPassword.of(password))) {
			response.sendError(response.SC_UNAUTHORIZED, "The specified user name or password is incorrect.");
		} else {
			response.setStatus(response.SC_SEE_OTHER);
		}
	}
}
