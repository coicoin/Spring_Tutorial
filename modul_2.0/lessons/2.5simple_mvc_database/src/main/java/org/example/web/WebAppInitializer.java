package org.example.web;

import org.apache.log4j.Logger;
import org.example.app.config.AppContextConfig;
import org.example.web.config.WebContextConfig;
import org.h2.server.web.WebServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

//перенос конфигурации web.xml в JAVA class
public class WebAppInitializer implements WebApplicationInitializer {

    Logger logger = Logger.getLogger(WebAppInitializer.class);

    //обязательна зависимость servlet-api для ServletException, ServletRegistration
    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {

        logger.info("loading app config");
        //XmlWebApplicationContext закомментирован так как вместо app-config.xml создан AppContextConfig
        //XmlWebApplicationContext appContext = new XmlWebApplicationContext();
        //равен пути из web.xml context-param.param-value
        //appContext.setConfigLocation("classpath:app-config.xml");
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(AppContextConfig.class);
        //равен пути из web.xml listener.listener-class org.springframework.web.context.ContextLoaderListener
        servletContext.addListener(new ContextLoaderListener(appContext));

        logger.info("loading web config");
        //XmlWebApplicationContext закомментирован так как нет web-config.xml
        //XmlWebApplicationContext webContext = new XmlWebApplicationContext();
        //указываем на web-config, равен пути из web.xml servlet.init-param.param-value
        //webContext.setConfigLocation("classpath:web-config.xml");

        //
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        //WebContextConfig класс содержащий конфигурацию web-config.xml
        webContext.register(WebContextConfig.class);

        //передаем в качестве аргумента webContext в DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);

        //динамическая регистрация сервлетов
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        //равен значению из web.xml servlet.load-on-startup
        dispatcher.setLoadOnStartup(1); //должен быть загружен первым
        //равен значению из web.xml servlet-mapping.url-pattern
        dispatcher.addMapping("/");
        logger.info("dispatcher ready");

        //h2-database конфигурация
        ServletRegistration.Dynamic servlet = servletContext.addServlet("h2-console",new WebServlet());
        servlet.setLoadOnStartup(2);
        servlet.addMapping("/console/*");
    }
}
