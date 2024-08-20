package com.github.ljl.learning.spring.aop.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: spring-learning
 * @description:
 * @author: ljl
 * @create: 2024-08-15 18:23
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MockTransactional {

}
