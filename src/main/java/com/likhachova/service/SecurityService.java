package com.likhachova.service;

import com.likhachova.model.User;
import com.likhachova.web.security.PasswordEncoder;
import com.likhachova.web.security.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SecurityService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    private UserService userService;
    private Map<String, Session> sessionMap = new ConcurrentHashMap<>();
    private PasswordEncoder passwordEncoder;

    public SecurityService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String userName, String userPassword) {
        if(isUserAuthorized(userName, userPassword)) {
            logger.debug("isUserAuthorized() is executed");
            return generateAndRegisterTokenForUser(new User(userName, userPassword));
        }
        return null;
    }

    public void logout(String token) {
        sessionMap.remove(token);
    }

    public Session getSession(String token) {
        Session session = sessionMap.get(token);
        if(session == null) {
            logger.debug("getSession() is executed, session is null");
            return null;
        }
        if(session.getExpireDate().isAfter(LocalDate.now())) {
            sessionMap.remove(token);
            logger.debug("getSession() is executed, session is expired");
            return null;
        }
        return session;
    }

    public boolean isUserAdmin(String userName, String userPassword) {
        if(Objects.equals(userName, "admin") && Objects.equals(userPassword, "admin")) {
            User daoUser = userService.getUser(userName);
            if(daoUser != null) {
                logger.debug("isUserAdmin() is executed");
                return passwordEncoder.checkPassword(daoUser.getPassword(), userPassword);
            }
        }
        return false;
    }

    private String generateAndRegisterTokenForUser(User user) {
        String token = UUID.randomUUID().toString();
        sessionMap.put(token, new Session(LocalDate.now(), user));
        return token;
    }

    private boolean isUserAuthorized(String userName, String userPassword) {
        User daoUser = userService.getUser(userName);
        if(daoUser != null) {
            return passwordEncoder.checkPassword(daoUser.getPassword(), userPassword);
        }
        return false;
    }

}
