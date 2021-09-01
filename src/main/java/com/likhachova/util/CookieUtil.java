package com.likhachova.util;

import com.likhachova.service.SecurityService;
import com.likhachova.web.security.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    @Autowired
    SecurityService securityService;

    public Session getUserSessionByCookie(HttpServletRequest httpServletRequest){
        Session session;
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies != null) {
            for(Cookie c : cookies) {
                if(c.getValue().equals("user-token")) {
                    session = securityService.getSession(c.getName());
                    if(session != null){
                        return session;
                    }
                }
            }
        }
        return null;
    }

    public Session getAdminSessionByCookie(HttpServletRequest httpServletRequest){
        Session session;
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies != null) {
            for(Cookie c : cookies) {
                if(c.getValue().equals("admin-token")) {
                    session = securityService.getSession(c.getName());
                    if(session != null){
                        return session;
                    }
                }
            }
        }
        return null;
    }

    public void eraseCookie(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
    }
}
