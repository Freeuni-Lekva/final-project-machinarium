package com.machinarium.utility.common;

import com.machinarium.model.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.machinarium.utility.constants.ServletConstants.ATTRIBUTE_USER;

public class SessionManager {

    private static final int MAX_INACTIVE_TIME = 1000; // seconds

    /**
     * Creates a new Login Session for the specified request. Invalidates the
     * previous one if one exists.
     *
     * @param request The received request as a {@link HttpServletRequest} object.
     */
    public static void createLoginSession(HttpServletRequest request, User user) {

        HttpSession oldSession = request.getSession(false);
        if(oldSession != null) oldSession.invalidate();

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
}
