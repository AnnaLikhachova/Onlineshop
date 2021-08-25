package com.likhachova.service;

import com.likhachova.model.User;
import com.likhachova.web.security.PasswordEncoder;
import com.likhachova.web.security.Session;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class SecurityService {

    private UserService userService;
    private Map<String, Session> sessionMap = new HashMap<>();
    private PasswordEncoder passwordEncoder;

    public SecurityService(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String userName, String userPassword){
        if(isUserAuthorized(userName, userPassword)){
            return generateAndRegisterTokenForUser(new User(userName,userPassword));
        }
        return null;
    }

    public void logout(String token){
        sessionMap.remove(token);
    }

    public Session getSession(String token){
        Session session = sessionMap.get(token);
        if(session == null){
            return null;
        }
        if(session.getExpireDate().isBefore(LocalDate.now())){
            sessionMap.remove(token);
            return null;
        }
        return session;
    }

    private String generateAndRegisterTokenForUser(User user){
        String token = UUID.randomUUID().toString();
        sessionMap.put(token, new Session(LocalDate.now(), user));
        return token;
    }

    private boolean isUserAuthorized(String userName, String userPassword){
        User daoUser = userService.getUser(userName);
        if(daoUser != null){
            return passwordEncoder.checkPassword(daoUser.getPassword(), userPassword);
        }
        return false;
    }

    public boolean isUserAdmin(String userName, String userPassword){
        if(Objects.equals(userName,"admin") && Objects.equals(userPassword, "admin")){
            User daoUser = userService.getUser(userName);
            if(daoUser != null){
                return passwordEncoder.checkPassword(daoUser.getPassword(), userPassword);
            }
        }
        return false;
    }

}
