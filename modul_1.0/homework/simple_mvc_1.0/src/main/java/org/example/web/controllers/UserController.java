package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.UserService;
import org.example.web.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/register")
public class UserController {

    private final Logger logger = Logger.getLogger(LoginController.class);
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String register(Model model) {
        logger.info("GET /register returns sign_up_page.html");
        model.addAttribute("user", new User());
        model.addAttribute("userList", userService.getAllUsers());
        return "sign_up_page";
    }

    @PostMapping("/save")
    public String saveUser(User user) {
        if (userService.validateUser(user)) {
            userService.saveUser(user);
            logger.info(String.format("user was saved: %s. Current repository size: %s", user, userService.getAllUsers().size()));
            return "redirect:/login";
        } else {
            return "redirect:/register";
        }
    }
}
