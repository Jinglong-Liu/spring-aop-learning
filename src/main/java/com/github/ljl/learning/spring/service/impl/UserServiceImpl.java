package com.github.ljl.learning.spring.service.impl;

import com.github.ljl.learning.spring.aop.MockTransactional;
import com.github.ljl.learning.spring.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: spring-learning
 * @description:
 * @author: ljl
 * @create: 2024-08-15 17:54
 **/
@Service
public class UserServiceImpl implements UserService {

    @Resource
    @Lazy
    private UserService userService;

    @MockTransactional
    @Override
    public void func1() {
        System.out.println("func1()");
    }

    @Override
    public void func2() {
        System.out.println("func2()");
    }

    @Override
    public void func3() {
        this.func1();
        System.out.println("this.func1 end\n");
        this.func2();
        System.out.println("this.func2 end\n");
    }
    @Override
    public void func4() {
        userService.func1();
        System.out.println("userService.func1 end\n");
        userService.func2();
        System.out.println("userService.func2 end\n");
    }

    @Override
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
