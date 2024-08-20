package com.github.ljl.learning.spring.aop.cglib;

import com.github.ljl.learning.spring.aop.aop.MockTransactional;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @program: spring-learning
 * @description:
 * @author: ljl
 * @create: 2024-08-16 09:36
 **/

public class MockTransactionInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if (method.isAnnotationPresent(MockTransactional.class)) {
            System.out.println("Mock Transaction started for method: " + method.getName());
        }
        Object result = methodProxy.invokeSuper(obj, args);
        if (method.isAnnotationPresent(MockTransactional.class)) {
            System.out.println("Mock Transaction committed for method: " + method.getName());
        }
        return result;
    }
}
