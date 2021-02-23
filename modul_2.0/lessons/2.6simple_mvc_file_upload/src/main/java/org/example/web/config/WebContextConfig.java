package org.example.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

//аналог web-config.xml
@Configuration //аннотация для конфигурации
//для назначения сканируемых пакетов, <context:component-scan base-package="org.example.web"/>
@ComponentScan(basePackages = "org.example.web")
//для mvc аннотаций <mvc:annotation-driven/>
@EnableWebMvc
public class WebContextConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //маппинг ресурсов <mvc:resources mapping="/**" location="classpath:images"/>
        registry.addResourceHandler("/**").addResourceLocations("classpath:images");
    }

    //реализуем thymeleaf аналогично bean id="templateResolver" в web-config.xml
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");

        return templateResolver;
    }

    //реализуем template engine аналогично bean id="templateEngine" в web-config.xml
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        //устанавливаем в качестве аргумента метод templateResolver()
        templateEngine.setTemplateResolver(templateResolver());

        return templateEngine;
    }

    //реализуем ThymeleafViewResolver аналогично <bean class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        //назначаем в качестве аргумента templateEngine()
        viewResolver.setTemplateEngine(templateEngine());
        //порядок запуска первым делом
        viewResolver.setOrder(1);

        return viewResolver;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(5000000); //5mb
        return multipartResolver;
    }

}
