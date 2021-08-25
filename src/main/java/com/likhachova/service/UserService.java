package com.likhachova.service;

import com.likhachova.dao.UserDao;
import com.likhachova.model.User;

import java.util.List;

public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public List<User> getAll() {
        return userDao.getAll();
    }

    public User getUser(String login) {
        return userDao.getUserByName(login);
    }
}
