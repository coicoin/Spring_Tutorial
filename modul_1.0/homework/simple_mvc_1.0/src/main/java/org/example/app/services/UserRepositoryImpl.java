package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl<E> implements UserRepository<User> {

    private final Logger logger = Logger.getLogger(UserRepositoryImpl.class);
    private final List<User> repo = new ArrayList<>();
    private int userCounter = 1;

    @Override
    public List<User> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(User user) {
        if (!user.getUsername().isEmpty() && !user.getPassword().isEmpty()) {
            user.setId(userCounter);
            logger.info("store new user: " + user);
            repo.add(user);
            userCounter++;
        } else {
            logger.info("it's not possible to register new user without username or password");
        }
    }

    @Override
    public boolean removeById(Integer userIdToRemove) {
        for (User user : retreiveAll()) {
            if (user.getId().equals(userIdToRemove)) {
                logger.info("remove user completed: " + user);
                return repo.remove(user);
            }
        }
        logger.info(String.format("user with id: '%s' not found", userIdToRemove));
        return false;
    }

}
