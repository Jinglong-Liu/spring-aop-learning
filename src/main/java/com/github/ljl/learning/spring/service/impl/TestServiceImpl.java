package com.github.ljl.learning.spring.service.impl;

import com.github.ljl.learning.spring.service.TestService;
import com.github.ljl.learning.spring.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: spring-learning
 * @description:
 * @author: ljl
 * @create: 2024-08-16 12:00
 **/

@Service
public class TestServiceImpl implements TestService {
    @Resource
    private UserService userService;

    @Override
    public void test() {
        userService.func3();
        userService.func4();
    }
}
