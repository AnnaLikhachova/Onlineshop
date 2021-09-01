package com.likhachova.web.security;

import com.likhachova.util.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SecurityFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    private CookieUtil cookieUtil;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        logger.info("Starting a transaction for req : {}", req.getRequestURI());

        String servletPath = req.getRequestURI();

        // Allow access to login page.
        if(servletPath.equals("/login")) {
            filterChain.doFilter(req, resp);
            return;
        }


        // Allow access to resources.
        if(servletPath.equals("/resources")) {
            filterChain.doFilter(req, resp);
            return;
        }

        // Allow access to user pages.
        if(servletPath.equals("/cart") || servletPath.equals("/user/cart/increase")
                || servletPath.equals("/user/cart/decrease") || servletPath.equals("/user/allproducts/search")
                || servletPath.contains("/user/allproducts/addtocart/") || servletPath.equals("/allproducts")) {

            Session session = cookieUtil.getUserSessionByCookie(req);
            checkSession(request, response, filterChain, req, resp, session);
        }

        // Allow access to admin pages.
        if(servletPath.equals("/admin") || servletPath.equals("/admin/products/update/")
                || servletPath.equals("/addproduct") || servletPath.contains("/admin/products/updateproduct")
                || servletPath.equals("/admin/products/delete") || servletPath.equals("/admin/products/add")) {
            Session session = cookieUtil.getAdminSessionByCookie(req);
            checkSession(request, response, filterChain, req, resp, session);
            return;
        }

        // Allow access to main page.
        if(servletPath.equals("/main") || servletPath.equals("/")) {
            filterChain.doFilter(req, resp);
            return;
        }

        // Allow access to logout.
        if(servletPath.equals("/logout")) {
            filterChain.doFilter(req, resp);
        }
    }

    static void checkSession(ServletRequest request, ServletResponse response, FilterChain filterChain, HttpServletRequest req, HttpServletResponse resp, Session session) throws IOException, ServletException {
        if(session != null) {
            req.setAttribute("session", session);
            filterChain.doFilter(request, response);
        }
        else {
            resp.sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {
    }
}
