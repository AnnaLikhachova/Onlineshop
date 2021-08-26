package com.likhachova.web.security;

import com.likhachova.web.controller.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AdminFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AdminFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        logger.info(
                "Starting a transaction for req : {}",
                req.getRequestURI());

        chain.doFilter(request, response);
        logger.info(
                "Committing a transaction for req : {}",
                req.getRequestURI());
    }

    @Override
    public void destroy() {

    }
}
