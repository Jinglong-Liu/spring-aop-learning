package com.github.ljl.learning.spring.aop.service;


import com.github.ljl.learning.spring.aop.aop.MockTransactional;

/**
 * @program: spring-learning
 * @description:
 * @author: ljl
 * @create: 2024-08-15 17:54
 **/

public interface UserService {
    void func1();
    @MockTransactional
    void func2();
    void func3();
    void func4();

    void setUserService(UserService userService);
}
