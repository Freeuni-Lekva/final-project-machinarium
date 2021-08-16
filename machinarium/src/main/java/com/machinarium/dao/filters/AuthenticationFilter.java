package com.machinarium.dao.filters;

import com.machinarium.utility.common.ConfiguredLogger;
import com.machinarium.utility.common.SessionManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.logging.Logger;

@WebFilter(urlPatterns = {"/GameServlet", "/LobbyServlet", "/OrderServlet", "/UserServlet", "/GarageServlet", "/CarServlet", "/profile", "/lobby", "/game"})
public class AuthenticationFilter implements Filter {

    private final static Logger logger = ConfiguredLogger.getLogger("AuthenticationFilter");

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        if(!SessionManager.isAuthenticated(request)) {
            request.getRequestDispatcher("/login").forward(request, response);
            return;
        }

        chain.doFilter(request, response);
    }
}
