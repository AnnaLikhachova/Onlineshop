package com.likhachova.configuration;

import com.likhachova.web.security.AdminFilter;
import com.likhachova.web.security.SecurityFilter;
import com.likhachova.web.security.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

public class FilterConfig {

    public FilterRegistrationBean<SecurityFilter> adminFilter() {

        FilterRegistrationBean<SecurityFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new SecurityFilter());

        registrationBean.addUrlPatterns("/*");

        return registrationBean;

    }

    public FilterRegistrationBean<UserFilter> userFilter() {

        FilterRegistrationBean<UserFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new UserFilter());

        registrationBean.addUrlPatterns("/allproducts/*", "/cart");

        return registrationBean;

    }

    public FilterRegistrationBean<AdminFilter> securityFilter() {

        FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AdminFilter());

        registrationBean.addUrlPatterns("/products/*", "/admin", "/addproduct");

        return registrationBean;

    }
}
