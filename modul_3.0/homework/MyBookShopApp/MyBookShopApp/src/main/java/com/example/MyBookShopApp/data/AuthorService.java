package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static java.util.Optional.ofNullable;

@Service
public class AuthorService {

    private final JdbcTemplate jdbcTemplate;
    private BookService bookService;

    @Autowired
    public AuthorService(JdbcTemplate jdbcTemplate, BookService bookService) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookService = bookService;
    }

    public void saveAuthors() {
        List<Book> booksWithAuthors = ofNullable(bookService).map(BookService::getBooksData).orElse(new ArrayList<>());
        Logger.getLogger(AuthorService.class.getName()).info("store new author: " + booksWithAuthors);
        booksWithAuthors.stream().filter(Objects::nonNull).forEach(book -> {
            jdbcTemplate.update("INSERT INTO authors(author,book_id) VALUES(?,?)", book.getAuthor(), book.getId());
        });
    }

    public List<Author> getAuthorsData() {
        List<Author> authors = jdbcTemplate.query("SELECT * FROM authors", (ResultSet rs, int rollNum) -> {
            Author author = new Author();
            author.setId(rs.getInt("id"));
            author.setAuthor(rs.getString("author"));
            author.setBookId(rs.getInt("book_id"));
            return author;
        });
        Logger.getLogger(AuthorService.class.getName()).info("get author: " + new ArrayList<>(authors));
        return new ArrayList<>(authors);
    }
}
