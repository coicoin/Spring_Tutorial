package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.h2.util.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository<E> implements ProjectRepository<Book>, ApplicationContextAware {

    Logger logger = Logger.getLogger(BookRepository.class);
    private ApplicationContext context;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Book> retreiveAll() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        return new ArrayList<>(books);
    }

    @Override
    public void store(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size", book.getSize());
        jdbcTemplate.update("INSERT INTO books(author,title,size) VALUES(:author, :title, :size)", parameterSource);
        logger.info("store new book: " + book);
    }

    @Override
    public boolean removeById(Integer bookIdToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", bookIdToRemove);
        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM books WHERE id = :id", parameterSource, Integer.class);
        if (count < 1) {
            logger.info("book didn't found by id: " + bookIdToRemove);
            return false;
        }
        jdbcTemplate.update("DELETE FROM books WHERE id = :id", parameterSource);
        logger.info("remove book completed by id: " + bookIdToRemove);
        return true;
    }

    @Override
    public boolean removeByAuthor(String bookAuthorToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", bookAuthorToRemove);
        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM books WHERE author = :author", parameterSource, Integer.class);
        if (count < 1) {
            logger.info("book didn't found by author: " + bookAuthorToRemove);
            return false;
        }
        jdbcTemplate.update("DELETE FROM books WHERE author = :author", parameterSource);
        logger.info("remove book completed by author: " + bookAuthorToRemove);
        return true;
    }

    @Override
    public boolean removeByTitle(String bookTitleToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("title", bookTitleToRemove);
        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM books WHERE title = :title", parameterSource, Integer.class);
        if (count < 1) {
            logger.info("book didn't found by title: " + bookTitleToRemove);
            return false;
        }
        jdbcTemplate.update("DELETE FROM books WHERE title = :title", parameterSource);
        logger.info("remove book completed by title: " + bookTitleToRemove);
        return true;
    }

    @Override
    public boolean removeBySize(Integer bookSizeToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("size", bookSizeToRemove);
        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM books WHERE size = :size", parameterSource, Integer.class);
        if (count < 1) {
            logger.info("book didn't found by size: " + bookSizeToRemove);
            return false;
        }
        jdbcTemplate.update("DELETE FROM books WHERE size = :size", parameterSource);
        logger.info("remove book completed by size: " + bookSizeToRemove);
        return true;
    }

    @Override
    public List<Book> sortByObject(String sortObject, boolean isDesc) {
        String bookByType;
        if (sortObject != null && !isDesc) {
            bookByType = "SELECT * FROM books ORDER BY " + sortObject;
        } else if (sortObject != null && isDesc) {
            bookByType = "SELECT * FROM books ORDER BY " + sortObject + " DESC";
        } else if (sortObject == null && isDesc) {
            bookByType = "SELECT * FROM books DESC";
        } else {
            bookByType = "SELECT * FROM books";
        }
        List<Book> books = jdbcTemplate.query(bookByType, (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        return new ArrayList<>(books);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public void defaultInit() {
        logger.info("default INIT book repo bean");
    }

    public void defaultDestroy() {
        logger.info("default DESTROY book repo bean");
    }
}
