package com.machinarium.utility.common;

import com.machinarium.dao.GameDAO;
import com.machinarium.model.user.User;
import com.machinarium.utility.constants.ServletConstants;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.machinarium.utility.constants.ServletConstants.ATTRIBUTE_USER;

public class SessionManager {

    private final static Logger logger = ConfiguredLogger.getLogger("SessionManager");

    private static final int MAX_INACTIVE_TIME = 1000; // seconds

    /**
     * @param request The {@link HttpServletRequest} send to a servlet.
     * @return True if there's an active login session and the session user is authenticated, false otherwise.
     */
    public static boolean isAuthenticated(ServletRequest request) {

        HttpSession session = ((HttpServletRequest) request).getSession();

        User user = (User) session.getAttribute(ATTRIBUTE_USER);

        logger.log(Level.INFO, "Received request from: " + user);

        return user != null;
    }

    /**
     * Creates a new Login Session for the specified request. Invalidates the
     * previous one if one exists.
     *
     * @param request The received request as a {@link HttpServletRequest} object.
     */
    public static void createLoginSession(HttpServletRequest request, User user) {

        HttpSession oldSession = request.getSession(false);
        if(oldSession != null) oldSession.invalidate();

        logger.log(Level.INFO, "Creating Login Session for user: " + user);

        HttpSession newSession = request.getSession(true);
        newSession.setMaxInactiveInterval(MAX_INACTIVE_TIME);
        newSession.setAttribute(ATTRIBUTE_USER, user);
    }

    /**
     * Returns the user for the current login session.
     *
     * @param request The current Http request.
     *
     * @return The user as a {@link User} object.
     */
    public static User getLoginUser(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);

        if(user == null) throw new RuntimeException("There are no logged in users for this session.");

        return user;
    }

    /**
     * @return The id of the currently active game for the session user, or null if there is none.
     */
    public static ID getActiveGameID(HttpServletRequest request) {

        String userName = SessionManager.getLoginUser(request).getUserName();
        ServletContext contextListener = request.getServletContext();

        GameDAO gameDAO = (GameDAO) contextListener.getAttribute(ServletConstants.ATTRIBUTE_GAME_DAO);

        return gameDAO.getActiveGame(userName);
    }
}
