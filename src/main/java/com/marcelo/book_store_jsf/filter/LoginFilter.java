package com.marcelo.book_store_jsf.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "authFilter")
public class LoginFilter implements Filter {
    public LoginFilter() {}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String requestURI = req.getRequestURI();

        boolean isLoginReq = requestURI.contains("login");

        boolean isLoggedIn = (session != null && session.getAttribute("customer") != null);


        boolean isAdminReq = requestURI.contains("admin");

        boolean isAdminLoggedIn = (session != null && session.getAttribute("role") == "admin");

        System.out.println("Request URI: " + requestURI);

        if (isLoginReq || isLoggedIn || requestURI.contains("register") || requestURI.contains("index")) {
            chain.doFilter(request, response);
        }
        else {
            res.sendRedirect(req.getContextPath() + "/login.xhtml");
        }
    }

    @Override
    public void destroy() {}
}
