package com.example.homework2.dao.api;

import com.example.homework2.model.User;

import java.util.List;

public interface UserDao {
    void save(User user);
    void update(User user);
    List<User> getAll();
    User read(Long id);
    void delete(User user);
}
