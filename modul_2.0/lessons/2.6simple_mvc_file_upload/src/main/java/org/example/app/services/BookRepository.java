package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository<E> implements ProjectRepository<Book>, ApplicationContextAware {

    Logger logger = Logger.getLogger(BookRepository.class);
    //private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Book> retreiveAll() {
        //language=SQL + реализация извлечения данных из таблицы с помощью лямбда выражений
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
                });
        //возврат заполненной лямбдой таблицы
        return new ArrayList<>(books);
    }

    @Override
    public void store(Book book) {
        //book.setId(context.getBean(IdProvider.class).provideId(book));
        //объект-контейнер для значений предназначающихся VALUES в SQL
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size", book.getSize());
        //:author синтаксис jdbcTemplate для перечисления полей
        jdbcTemplate.update("INSERT INTO books(author,title,size) VALUES(:author, :title, :size)", parameterSource);
        logger.info("store new book: " + book);
        //repo.add(book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        //for(Book book : retreiveAll()) {
            //if(book.getId().equals(bookIdToRemove)) {
                MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                parameterSource.addValue("id", bookIdToRemove);
                jdbcTemplate.update("DELETE FROM books WHERE id = :id", parameterSource);
                logger.info("remove book completed by id: " + bookIdToRemove);
                //bookRepository требует возвращать истину, поэтому true
                return true;//repo.remove(book);
            //}
        //}
        //return false;
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
