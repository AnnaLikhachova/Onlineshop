package com.likhachova.web.security;

import com.likhachova.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SecurityFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    private SecurityService securityService;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        logger.info("Starting a transaction for req : {}", req.getRequestURI());

        String servletPath = req.getServletPath();
        filterChain.doFilter(req, resp);

        // Allow access to login functionality.
        if (servletPath.equals("/login")) {
            filterChain.doFilter(req, resp);
            return;
        }
        // Allow access to main page.
        if (servletPath.equals("/main")) {
            filterChain.doFilter(req, resp);
            return;
        }
        // All other functionality requires authentication.
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for(Cookie c : cookies) {
                if(c.getName().equals("admin-token")) {
                    Session session = securityService.getSession(c.getValue());
                    if(session != null) {
                        req.setAttribute("session", session);
                        resp.sendRedirect("/admin");
                        return;
                    }
                    else {
                        resp.sendRedirect("/login");
                        return;
                    }
                } else if(c.getName().equals("user-token")){
                    Session session = securityService.getSession(c.getValue());
                    if(session != null) {
                        req.setAttribute("session", session);
                        resp.sendRedirect("allproducts.ftl");
                        return;
                    }
                    else {
                        resp.sendRedirect("/login");
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}
