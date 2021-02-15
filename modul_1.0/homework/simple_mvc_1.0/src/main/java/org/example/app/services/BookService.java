package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.controllers.BookShelfController;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.*;

@Service
public class BookService {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(BookRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
    }

    public List<Book> getSortedBooks(String sortType) {
        List<Book> bookList = new ArrayList<>();
        switch (sortType) {
            case "sortById":
                bookList = getAllBooks().stream().sorted(comparing(Book::getId)).collect(Collectors.toList());
                break;
            case "sortByAuthor":
                bookList = getAllBooks().stream().sorted(comparing(Book::getAuthor)).collect(Collectors.toList());
                break;
            case "sortByTitle":
                bookList = getAllBooks().stream().sorted(comparing(Book::getTitle)).collect(Collectors.toList());
                break;
            case "sortBySize":
                bookList = getAllBooks().stream().sorted(nullsFirst(comparing(Book::getSize, nullsFirst(naturalOrder()))))
                        .collect(Collectors.toList());
                break;
        }
        return bookList;
    }

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public void removeBookByOption(String selectedTypeToRemove, String value) {
        switch (selectedTypeToRemove) {
            case "bookIdToRemove":
                bookRepo.removeById(value.trim().matches("^-?[0-9]+$") ? Integer.parseInt(value.trim()) : 0);
                break;
            case "bookAuthorToRemove":
                bookRepo.removeByAuthor(value.trim());
                break;
            case "bookTitleToRemove":
                bookRepo.removeByTitle(value.trim());
                break;
            case "bookSizeToRemove":
                bookRepo.removeBySize(value.trim().matches("^-?[0-9]+$") ? Integer.parseInt(value.trim()) : 0);
                break;
        }
    }

}
