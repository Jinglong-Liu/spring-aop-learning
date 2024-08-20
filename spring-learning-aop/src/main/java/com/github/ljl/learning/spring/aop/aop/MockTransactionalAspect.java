package com.github.ljl.learning.spring.aop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @program: spring-learning
 * @description:
 * @author: ljl
 * @create: 2024-08-15 18:25
 **/

@Aspect
@Component
public class MockTransactionalAspect {
    @Before("@annotation(mockTransactional)")
    public void beforeMethod(JoinPoint joinPoint, MockTransactional mockTransactional) {
        // 模拟事务开始
        System.out.println("Transaction started for method: " + joinPoint.getSignature().getName());
    }

    @After("@annotation(mockTransactional)")
    public void afterMethod(JoinPoint joinPoint, MockTransactional mockTransactional) {
        // 模拟事务提交
        System.out.println("Transaction committed for method: " + joinPoint.getSignature().getName());
    }
}
