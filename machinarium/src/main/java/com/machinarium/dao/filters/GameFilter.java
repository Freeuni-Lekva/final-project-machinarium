package com.machinarium.dao.filters;

import com.machinarium.dao.GameDAO;
import com.machinarium.utility.common.ConfiguredLogger;
import com.machinarium.utility.common.ID;
import com.machinarium.utility.common.JSONResponse;
import com.machinarium.utility.common.SessionManager;
import com.machinarium.utility.constants.ServletConstants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebFilter(urlPatterns = {"/GameServlet", "/OrderServlet", "/lobby", "/game"})
public class GameFilter implements Filter {

    private final static Logger logger = ConfiguredLogger.getLogger("GameFilter");

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        if(!SessionManager.isAuthenticated(request)) {
            chain.doFilter(request, response);
            return;
        }

        ID gameID = SessionManager.getActiveGameID((HttpServletRequest) request);

        GameDAO gameDAO = (GameDAO) request.getServletContext().getAttribute(ServletConstants.ATTRIBUTE_GAME_DAO);

        logger.log(Level.INFO, "Received request for game with ID: " + gameID);
        String requestedURL = ((HttpServletRequest) request).getRequestURL().toString();

        if(gameID == null) {

            JSONResponse wrappedResponse = new JSONResponse((HttpServletResponse) response);
            wrappedResponse.setError(wrappedResponse.SC_NOT_FOUND, "The user is not participating in a game.");
            return;
        } else if(requestedURL.endsWith("/game") && gameDAO.getGameStage(gameID).equals(GameDAO.GameStage.IN_LOBBY)) {

            request.getRequestDispatcher("/lobby").forward(request, response);
            return;
        }

        chain.doFilter(request, response);
    }
}
