package com.machinarium.servlets;

import com.machinarium.model.user.User;
import com.machinarium.utility.common.ConfiguredLogger;
import com.machinarium.utility.common.ID;
import com.machinarium.utility.common.JSONResponse;
import com.machinarium.utility.common.SessionManager;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.machinarium.utility.constants.ServletConstants.ATTRIBUTE_USER;

@WebFilter(urlPatterns = {"/GameServlet", "/OrderServlet"})
public class GameFilter implements Filter {

    private final static Logger logger = ConfiguredLogger.getLogger("GameFilter");

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        ID gameID = SessionManager.getActiveGameID((HttpServletRequest) request);

        logger.log(Level.INFO, "Received request for game with ID: " + gameID);

        if(gameID == null) {
            JSONResponse wrappedResponse = new JSONResponse((HttpServletResponse) response);

            wrappedResponse.setError(wrappedResponse.SC_NOT_FOUND, "The user is not participating in a game.");
            return;
        }

        chain.doFilter(request, response);
    }
}
