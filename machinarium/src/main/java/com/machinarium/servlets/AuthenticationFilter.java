package com.machinarium.servlets;

import com.machinarium.model.user.User;
import com.machinarium.utility.common.ConfiguredLogger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.machinarium.utility.constants.ServletConstants.ATTRIBUTE_USER;

@WebFilter(urlPatterns = {"/GameServlet", "/LobbyServlet", "/OrderServlet", "/UserServlet", "/GarageServlet", "/profile", "/lobby"})
public class AuthenticationFilter implements Filter {

    private final static Logger logger = ConfiguredLogger.getLogger("AuthenticationFilter");

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpSession session = ((HttpServletRequest) request).getSession();

        User user = (User) session.getAttribute(ATTRIBUTE_USER);

        logger.log(Level.INFO, "Received request from: " + user);

        if(user == null) {
            request.getRequestDispatcher("/login").forward(request, response);
            return;
        }

        chain.doFilter(request, response);
    }
}
