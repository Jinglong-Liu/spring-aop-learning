package com.github.ljl.learning.spring.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @program: spring-learning
 * @description:
 * @author: ljl
 * @create: 2024-08-15 17:53
 **/

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(Application.class);
    }
}
