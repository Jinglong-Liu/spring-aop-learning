package com.github.ljl.learning.spring.service.impl;

import com.github.ljl.learning.spring.cglib.MockTransactionInterceptor;
import com.github.ljl.learning.spring.proxy.MockTransactionHandler;
import com.github.ljl.learning.spring.service.TestService;
import com.github.ljl.learning.spring.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.proxy.Enhancer;

import javax.annotation.Resource;
import java.lang.reflect.Proxy;

@SpringBootTest
class UserServiceImplTest {

    @Resource
    private TestService testService;

    @Test
    public void testMockTransactional() {
        testService.test();
    }

    @Test
    public void testJdkProxy() {
        UserService proxy = (UserService) Proxy.newProxyInstance(
                UserServiceImpl.class.getClassLoader(),
                new Class<?>[]{UserService.class},
                new MockTransactionHandler(new UserServiceImpl())
        );
        proxy.setUserService(proxy);
        proxy.func3();
        proxy.func4();
    }

    @Test
    public void testCglibProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserServiceImpl.class);
        enhancer.setCallback(new MockTransactionInterceptor());

        UserService proxy = (UserService) enhancer.create();
        proxy.setUserService(proxy);
        proxy.func3();
        proxy.func4();
    }
}