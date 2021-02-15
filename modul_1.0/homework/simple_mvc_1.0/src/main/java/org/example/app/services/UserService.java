package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    Logger logger = Logger.getLogger(UserService.class);
    private final UserRepository<User> userRepo;

    @Autowired
    public UserService(UserRepositoryImpl<User> userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers() {
        return userRepo.retreiveAll();
    }

    public void saveUser(User user) {
        userRepo.store(user);
    }

    public boolean validateUser(User user) {
        logger.info("validate user: " + user);
        if (!user.getUsername().isEmpty()) {
            for (User u : userRepo.retreiveAll()) {
                if (user.getUsername().equals(u.getUsername())) {
                    logger.info(String.format("username '%s' is busy", user.getUsername()));
                    return false;
                }
            }
        } else {
            logger.info("username is empty");
            return false;
        }
        return true;
    }
}