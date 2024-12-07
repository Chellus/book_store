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

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String requestURI = req.getRequestURI();

        // Detecta si la solicitud es para el login o registro
        boolean isLoginReq = requestURI.contains("login");
        boolean isRegisterReq = requestURI.contains("register");
        boolean isIndexReq = requestURI.contains("index");
        boolean isHomeReq = requestURI.contains("home");

        // Verifica si el usuario está autenticado
        boolean isLoggedIn = (session != null && session.getAttribute("customer") != null);

        // Verifica si la solicitud es para un recurso de administrador
        boolean isAdminReq = requestURI.contains("admin");

        // Verifica si el usuario tiene rol de administrador
        boolean isAdmin = (session != null && "admin".equals(session.getAttribute("role")));

        // Permite acceso a login, registro e index sin autenticación
        if (isLoginReq || isRegisterReq || isIndexReq || isHomeReq) {
            chain.doFilter(request, response);
            return;
        }

        // Permite acceso si el usuario está autenticado
        if (isLoggedIn) {
            if (isAdminReq && !isAdmin) {
                // Redirige si un usuario no administrador intenta acceder a recursos de administrador
                res.sendRedirect(req.getContextPath() + "/unauthorized.xhtml");
            } else {
                // Permite acceso si es administrador o el recurso no requiere privilegios
                chain.doFilter(request, response);
            }
        } else {
            // Redirige a la página de login si no está autenticado
            res.sendRedirect(req.getContextPath() + "/login.xhtml");
        }
    }

    @Override
    public void destroy() {}
}
