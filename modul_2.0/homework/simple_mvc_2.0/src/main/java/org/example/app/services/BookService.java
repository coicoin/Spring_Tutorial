package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;
    Logger logger = Logger.getLogger(BookService.class);

    @Autowired
    public  BookService(BookRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
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

    public void defaultInit() {
        logger.info("default INIT book repo bean");
    }

    public void defaultDestroy() {
        logger.info("default DESTROY book repo bean");
    }
}
