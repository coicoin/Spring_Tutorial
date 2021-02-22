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

    //@Autowired чтобы bookRepo подтянулся в BookService в качестве зависимости
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

    public boolean removeBookById(Integer bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    public void defaultInit() {
        logger.info("default INIT book repo bean");
    }

    public void defaultDestroy() {
        logger.info("default DESTROY book repo bean");
    }
}
