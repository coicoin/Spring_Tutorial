package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.exceptions.BookShelfLoginException;
import org.example.app.services.LoginService;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//для распознования как бин
@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private final Logger logger = Logger.getLogger(LoginController.class);
    //loginService будет бином внедренным в контроллер в качестве зависимости
    private LoginService loginService;

    //если not autowired, то добавляем @Service в LoginService класс
    @Autowired //указывает на то, что это бин, который внедрен в контроллер в качестве зависимости
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    //аннотация запроса
    /*@GetMapping(value = "/login")
    public ModelAndView login() {
        logger.info("GET /home returns login_page.html");
        return new ModelAndView("login_page");
    }*/

    @GetMapping
    //добавление model в качестве аргумента
    public String login(Model model) {
        logger.info("GET /home returns login_page.html");
        //в loginForm записывается вводимое имя и пароль для передачи в метод authenticate
        model.addAttribute("loginForm", new LoginForm());
        return "login_page";
    }

    @PostMapping("/auth") // "/login/auth" можно сократить указав в классе аннотацию @RequestMapping
    // принимает вводимое имя и пароль в качестве аргумента LoginForm
    public String authenticate(LoginForm loginForm) throws BookShelfLoginException {
        //if return true перенаправляем пользователя по /books/shelf или возвращаем на /login
        if (loginService.authenticate(loginForm)) {
            logger.info("Login OK redirect to book shelf");
            return "redirect:/books/shelf";
        } else {
            logger.info("Login FAIL redirect back to login");
            throw new BookShelfLoginException("invalid username or password");
        }
    }

    //обработчик ошибки @ExceptionHandler(BookShelfLoginException.class) можно определить здесь, но вынесли в общий ErrorController
    @ExceptionHandler(BookShelfLoginException.class)
    //метод обработчика ошибок. Model - объкт модели, Exception - само исключение, которое было выброшено
    public String handleError(Model model, BookShelfLoginException exception) {
        //добавить аттрибут с именем errorMessage, значение - строка с сообщением в 404.html
        model.addAttribute("errorMessage", exception.getMessage());
        return "errors/404";
    }
}
