package com.likhachova.dao;

import com.likhachova.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAll();

    User getUserByName(String name);

}
