package org.example.app.services;

import org.example.web.dto.User;

import java.util.List;

public interface UserRepository<T> {
    List<User> retreiveAll();

    void store(User user);

    boolean removeById(Integer userIdToRemove);
}
