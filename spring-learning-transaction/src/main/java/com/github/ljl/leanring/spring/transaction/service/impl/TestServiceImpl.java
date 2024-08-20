package com.github.ljl.leanring.spring.transaction.service.impl;

import com.github.ljl.leanring.spring.transaction.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @program: spring-learning
 * @description:
 * @author: ljl
 * @create: 2024-08-20 13:17
 **/

@Service
public class TestServiceImpl {
    @Resource
    private UserServiceImpl userService;

    @Resource
    private ProductServiceImpl productService;

    @Transactional(propagation = Propagation.REQUIRED)
    public void testRequired() {
        // 回滚
        userService.deduceMoneyRequired(1L, BigDecimal.valueOf(100.0));
        // 回滚
        productService.deduceStockRequired(1L, 1);
        if (true) {
            throw new RuntimeException("Test required rollback");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void testRequiredNew() {
        // 开启事务1
        // 加入事务1，不提交
        userService.deduceMoneyRequired(1L, BigDecimal.valueOf(100.0));
        // 挂起事务1，开启事务2，事务2结束，提交事务2
        productService.deduceStockNewRequired(1L, 1);
        // 回到事务1
        if (true) {
            // 事务1 异常，回滚userService的操作
            throw new RuntimeException("Test required rollback");
        }
    }

    /**
     * 嵌套事务
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void testNested() {
        // 开启事务1
        // 加入事务1，不提交
        userService.deduceMoneyRequired(1L, BigDecimal.valueOf(100.0));
        // 在事务1 内开启子事务1.1
        productService.deduceStockNested(1L, 1);
        // 结束子事务1.1
        if (true) {
            // 事务1异常，事务1.1也要回滚
            throw new RuntimeException("Test required rollback");
        }
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void testNestedEx1() {
        // 开启事务1
        // 加入事务1，不提交
        userService.deduceMoneyRequired(1L, BigDecimal.valueOf(100.0));
        // 在事务1 内开启子事务1.1, 内部异常，userService的事务1也无法提交
        try {
            productService.deduceStockNestedEx(1L, 1);
        } catch (RuntimeException e) {
            throw e;
        }
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void testNestedEx2() {
        // 开启事务1
        // 加入事务1，不提交
        userService.deduceMoneyRequired(1L, BigDecimal.valueOf(100.0));
        // 在事务1 内开启子事务1.1, 内部异常，事务1.1无法提交，但是事务1中忽略了异常，提交事务1
        // userService的操作被提交
        // 不推荐这么做
        try {
            productService.deduceStockNestedEx(1L, 1);
        } catch (RuntimeException ignored) {
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void testSupports1() {
        // 开启事务1
        try {
            // 加入事务1，里面抛异常， deduceMoney回滚
            userService.deduceMoneySupports(1L, BigDecimal.valueOf(100.0));
        } catch (RuntimeException ignored) {

        }
        // 继续执行事务1

        // support 加入事务1，正常执行
        productService.deduceStockSupport(1L, 1);
        if (true) {
            // 事务1 异常，deduceStock回滚
            throw new RuntimeException("Test required rollback");
        }
    }

    public void testSupports2() {
        // 没有事务
        try {
            // 没有事务，deduceMoney在mapper执行后抛异常，不回滚
            userService.deduceMoneySupports(1L, BigDecimal.valueOf(100.0));
        } catch (RuntimeException ignored) {

        }
        // 继续执行

        // support, 不开启事务，正常执行
        productService.deduceStockSupport(1L, 1);
        if (true) {
            // 异常，但deduceStock已经执行完成，不回滚
            throw new RuntimeException("Test required rollback");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void testNotSupported() {
        // 开启事务1
        try {
            // 挂起事务1
            // deduceMoney在mapper执行后抛异常，不回滚
            userService.deduceMoneyNotSupport(1L, BigDecimal.valueOf(100.0));
        } catch (RuntimeException ignored) {

        }
        // 回到事务1

        // 正常执行，加入事务1
        productService.deduceStockSupport(1L, 1);
        if (true) {
            // 事务1 异常，productService回滚
            throw new RuntimeException("Test required rollback");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void testMandatory1() {
        userService.deduceMoneyMandatory(1L, BigDecimal.valueOf(100.0));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void testMandatory2() {
        userService.deduceMoneyMandatory(1L, BigDecimal.valueOf(100.0));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void testNever1() {
        productService.deduceStockNever(1L, 1);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void testNever2() {
        productService.deduceStockNever(1L, 1);
    }

}
