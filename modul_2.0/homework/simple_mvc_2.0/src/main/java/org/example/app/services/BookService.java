package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;
    Logger logger = Logger.getLogger(BookService.class);

    @Autowired
    public BookService(BookRepository<Book> bookRepo) {
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

    public List<Book> getAllSortedBooks(String sortObject, String desc) {
        return bookRepo.sortByObject(sortObject, StringUtils.equals("true", desc));
    }

    public List<File> getFiles() throws Exception {
        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "external_uploads");
        if (!dir.exists()) {
            dir.mkdir();
        }

        return ofNullable(dir.listFiles()).map(files -> Arrays.stream(files).collect(Collectors.toList())).orElse(new ArrayList<>());
    }

    public void defaultInit() {
        logger.info("default INIT book repo bean");
    }

    public void defaultDestroy() {
        logger.info("default DESTROY book repo bean");
    }
}
