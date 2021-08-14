package com.machinarium.servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;
import java.io.IOException;

import static com.machinarium.utility.constants.ServletConstants.ATTRIBUTE_USER;

@WebFilter(urlPatterns = {"/UserServlet", "GarageServlet", "/profile"})
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpSession session = ((HttpServletRequest) request).getSession();
        if(session.getAttribute(ATTRIBUTE_USER) == null) {
            request.getRequestDispatcher("/login").forward(request, response);
        }

        chain.doFilter(request, response);
    }
}
