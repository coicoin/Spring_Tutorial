package com.example.time_serv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication //включает в себя 3 аннотации: @SpringBootConfiguration, @ComponentScan, @EnableAutoConfiguration
//@SpringBootConfiguration
//@ComponentScan(basePackages = "com.example")
//@EnableAutoConfiguration
public class TimeServApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeServApplication.class, args);
	}

}
