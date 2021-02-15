package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class BookRepository<E> implements ProjectRepository<Book> {

    Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        if (!book.getAuthor().isEmpty() || !book.getTitle().isEmpty() || book.getSize() != null) {
            book.setId(book.hashCode());
            logger.info("store new book: " + book);
            repo.add(book);
        } else {
            logger.info("it is not possible to save book, at least 1 parameter (author, title, size) must be");
        }
    }

    @Override
    public boolean removeById(Integer bookIdToRemove) {
        for (Book book : retreiveAll()) {
            if (StringUtils.equalsIgnoreCase(book.getId(), bookIdToRemove)) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        logger.info(String.format("book with id: '%s' not found", bookIdToRemove));
        return false;
    }

    public boolean removeByAuthor(String bookAuthorToRemove) {
        List<Book> books = retreiveAll().stream().filter(Objects::nonNull)
                .filter(book -> StringUtils.equalsIgnoreCase(book.getAuthor(), bookAuthorToRemove))
                .collect(Collectors.toList());
        if (!books.isEmpty()) {
            logger.info("remove book completed: " + books);
            return repo.removeAll(books);
        }
        logger.info(String.format("book with author: '%s' not found", bookAuthorToRemove));
        return false;
    }

    public boolean removeByTitle(String bookTitleToRemove) {
        List<Book> books = retreiveAll().stream().filter(Objects::nonNull)
                .filter(book -> StringUtils.equalsIgnoreCase(book.getTitle(), bookTitleToRemove))
                .collect(Collectors.toList());
        if (!books.isEmpty()) {
            logger.info("remove book completed: " + books);
            return repo.removeAll(books);
        }
        logger.info(String.format("book with title: '%s' not found", bookTitleToRemove));
        return false;
    }

    public boolean removeBySize(Integer bookSizeToRemove) {
        List<Book> books = retreiveAll().stream().filter(Objects::nonNull)
                .filter(book -> StringUtils.equalsIgnoreCase(book.getSize(), bookSizeToRemove))
                .collect(Collectors.toList());
        if (!books.isEmpty()) {
            logger.info("remove book completed: " + books);
            return repo.removeAll(books);
        }
        logger.info(String.format("book with size: '%s' not found", bookSizeToRemove));
        return false;
    }

    //remove any book by element
    /*public boolean removeItemByElementName(String elementName) {
        for(Book book : retreiveAll()) {
            if(book.getAuthor().equals(elementName) || book.getTitle().equals(elementName) || book.getSize().toString().equals(elementName)) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        logger.info(String.format("book with parameter: '%s' not found", elementName));
        return false;
    }*/

}
