package org.example.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    //источник данных, JDBC URL: jdbc:h2:mem:book_store
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(false) //false чтобы не устанавливать уникальное значение
                .setName("book_store") //имя бд
                .setType(EmbeddedDatabaseType.H2) //тип бд h2
                .addDefaultScripts() //скрипт популяции бд и данных
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true) //игнорировать фэйл дропы
                .build();
    }

    //JDBC template прикимающий источник данных
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

}
