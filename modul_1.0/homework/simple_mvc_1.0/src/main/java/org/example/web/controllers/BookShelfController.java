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

import java.util.Comparator;


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
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        logger.info("sortType " + sortType);
        switch (sortType) {
            case "sortById":
                model.addAttribute("sort", Comparator.comparing(Book::getId));
                break;
            case "sortByAuthor":
                model.addAttribute("sort", Comparator.comparing(Book::getAuthor));
                break;
            case "sortByTitle":
                model.addAttribute("sort", Comparator.comparing(Book::getTitle));
                break;
            case "sortBySize":
                model.addAttribute("sort", Comparator.comparing(Book::getSize));
                break;
        }
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
        switch (selectedTypeToRemove) {
            case "bookIdToRemove":
                if (bookService.removeBookById(value.trim().matches("^-?[0-9]+$") ? Integer.parseInt(value.trim()) : 0)) {
                    return "redirect:/books/shelf";
                }
                break;
            case "bookAuthorToRemove":
                if (bookService.removeBookByAuthor(value.trim())) {
                    return "redirect:/books/shelf";
                }
                break;
            case "bookTitleToRemove":
                if (bookService.removeBookByTitle(value.trim())) {
                    return "redirect:/books/shelf";
                }
                break;
            case "bookSizeToRemove":
                if (bookService.removeBookBySize(value.trim().matches("^-?[0-9]+$") ? Integer.parseInt(value.trim()) : 0)) {
                    return "redirect:/books/shelf";
                }
                break;
        }
        return "redirect:/books/shelf";
    }
}
