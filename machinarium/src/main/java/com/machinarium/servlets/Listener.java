package com.machinarium.servlets;

import com.machinarium.dao.ConnectionPool;
import com.machinarium.dao.UserDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebListener
public class Listener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    public static final String ATTRIBUTE_USER_DAO = "user_dao";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // TODO connPool will be initialized here.
        // TODO UserDAO will be initialized here.

//        sce.getServletContext().setAttribute(ATTRIBUTE_USER_DAO, userDao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }

//    private final ConnectionPool connPool;
//    private final UserDAO userDao;
}
