package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = "/books")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String showBooks(@RequestParam(value = "sortType", required = false, defaultValue = "sortById") String sortType, Model model) {
        logger.info("got book shelf with sortType: " + sortType);
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getSortedBooks(sortType));
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book) {
        bookService.saveBook(book);
        logger.info("current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(@RequestParam(value = "value", required = false) String value,
                             @RequestParam(value = "selectedTypeToRemove", required = false) String selectedTypeToRemove) {
        logger.info(value + " " + selectedTypeToRemove);
        bookService.removeBookByOption(selectedTypeToRemove, value);
        return "redirect:/books/shelf";
    }
}
