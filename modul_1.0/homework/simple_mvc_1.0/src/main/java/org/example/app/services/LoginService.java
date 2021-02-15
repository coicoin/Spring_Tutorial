package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.example.web.dto.User;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
public class LoginService {

    private final Logger logger = Logger.getLogger(LoginService.class);

    public boolean authenticate(LoginForm loginForm, List<User> users) {
        logger.info("try auth with user-form: " + loginForm);
        return users.stream().filter(Objects::nonNull)
                .anyMatch(u -> StringUtils.equals(loginForm.getUsername(), u.getUsername())
                        && StringUtils.equals(loginForm.getPassword(), u.getPassword()));
    }
}
