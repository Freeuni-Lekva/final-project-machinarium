package com.machinarium.servlets;

import com.machinarium.dao.ConnectionPool;
import com.machinarium.dao.UserDAO;
import com.machinarium.dao.implementation.*;
import com.machinarium.model.user.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import static com.machinarium.utility.constants.ServletConstants.*;

@WebListener
public class Listener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    private static final int N_CONNECTIONS = 10;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        connectionPool = BlockingConnectionPool.getInstance(N_CONNECTIONS);

        sce.getServletContext().setAttribute(ATTRIBUTE_USER_DAO, new UserDAOClass(connectionPool));
        sce.getServletContext().setAttribute(ATTRIBUTE_GARAGE_DAO, new GarageDAOClass(connectionPool));
        sce.getServletContext().setAttribute(ATTRIBUTE_STATISTICS_DAO, new StatisticsDAOClass(connectionPool));
        sce.getServletContext().setAttribute(ATTRIBUTE_GAME_DAO, new GameDAOClass(connectionPool));
        sce.getServletContext().setAttribute(ATTRIBUTE_ITEM_DAO, new ItemDAOClass(connectionPool));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        try {
            connectionPool.close();
        } catch (Exception e) {e.printStackTrace();        }
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {

        sessionUser = null;
        se.getSession().setAttribute(ATTRIBUTE_USER, sessionUser);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        sessionUser = null;
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

    private ConnectionPool connectionPool;

    private User sessionUser;
}
