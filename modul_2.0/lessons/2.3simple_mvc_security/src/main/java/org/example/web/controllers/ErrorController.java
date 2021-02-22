package org.example.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@Controller, если обработчик ошибок отдельно
//@ControllerAdvice для центролизования всех @ExceptionHandler методов в одном классе
@Controller
public class ErrorController {

    @GetMapping("/404")
    public String notFoundError() {
        return "errors/404";
    }

    //закоментирован, так как @ControllerAdvice не обрабатывает внутренние ошибки сервера
    /*@ExceptionHandler(BookShelfLoginException.class)
    //метод обработчика ошибок. Model - объкт модели, Exception - само исключение, которое было выброшено
    public String handleError(Model model, BookShelfLoginException exception) {
        //добавить аттрибут с именем errorMessage, значение - строка с сообщением в 404.html
        model.addAttribute("errorMessage", exception.getMessage());
        return "errors/404";
    }*/

}
