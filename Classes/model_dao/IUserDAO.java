package com.example.chav.mapsproject.model_dao;

import com.example.chav.mapsproject.User;

import java.util.List;

/**
 * Created by Chav on 3/2/2016.
 */
public interface IUserDAO {

    void addUser(User user);

    User getUser(int id);

    List<User> getAllUsers();

    void deleteUser();


}
