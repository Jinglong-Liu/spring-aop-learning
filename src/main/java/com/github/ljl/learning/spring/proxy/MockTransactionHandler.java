package com.github.ljl.learning.spring.proxy;

import com.github.ljl.learning.spring.aop.MockTransactional;
import com.github.ljl.learning.spring.aop.MockTransactionalAspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @program: spring-learning
 * @description:
 * @author: ljl
 * @create: 2024-08-15 22:44
 **/

public class MockTransactionHandler implements InvocationHandler {

    private final Object target;

    public MockTransactionHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(MockTransactional.class)) {
            // 模拟事务开始
            System.out.println("Mock Transaction started for method: " + method.getName());
        }
        final Object result = method.invoke(target, args);
        if (method.isAnnotationPresent(MockTransactional.class)) {
            // 模拟事务提交
            System.out.println("Mock Transaction committed for method: " + method.getName());
        }
        return result;
    }
}
