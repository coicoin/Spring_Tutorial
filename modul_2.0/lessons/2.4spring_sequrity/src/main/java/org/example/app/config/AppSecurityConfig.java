package org.example.app.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    Logger logger = Logger.getLogger(AppContextConfig.class);

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.info("populate inmemory auth user");
        auth
                .inMemoryAuthentication() //аутентификация пользователя
                .withUser("root") //имя пользователя
                .password(passwordEncoder().encode("123")) //пароль закодирован
                .roles("USER"); //роль пользователя - user

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //способ шифрования BCrypt
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("config http security");
        http
                .csrf().disable() //отключяем cross site reference forgery (не рекомендуется отключать, это неправильно)
                .authorizeRequests() //хотим авторизовать реквесты
                .antMatchers("/login*").permitAll() //запросы разрешены которые начинаются с /login*
                .anyRequest().authenticated() //все реквесты должны проходить аутентификацию
                .and() //а также
                .formLogin() //предоставляем свою собственную форму логина
                .loginPage("/login") //наша логин страница
                .loginProcessingUrl("/login/auth") //страница обработки аутентификации
                .defaultSuccessUrl("/books/shelf",true) //страница по-умолчанию в случае успешного логина
                .failureUrl("/login"); //страница перехода в случае неуспешной аутентификации
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        logger.info("config web security");
        web
                .ignoring() //security будет пропускать к статическим ресурсам
                .antMatchers("/images/**"); //игнорируется все запросы которые начинаются с /images**
    }
}
