package com.github.ljl.leanring.spring.transaction;

import com.github.ljl.leanring.spring.transaction.entity.Product;
import com.github.ljl.leanring.spring.transaction.entity.User;
import com.github.ljl.leanring.spring.transaction.service.impl.ProductServiceImpl;
import com.github.ljl.leanring.spring.transaction.service.impl.TestServiceImpl;
import com.github.ljl.leanring.spring.transaction.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.IllegalTransactionStateException;

import javax.annotation.Resource;
import java.math.BigDecimal;


@SpringBootTest
@ActiveProfiles("test")
class TxApplicationTest {

    @Resource
    private ProductServiceImpl productService;

    @Resource
    private UserServiceImpl userService;

    @Resource
    private TestServiceImpl testService;

    /**
     * 全部不加Transactional注解
     */
    @Test
    public void testNoTransactional() {
        User user = userService.getById(1L);
        Product product = productService.getById(1L);

        try {
            testService.testNoTransactional();
        } catch (RuntimeException r) {
            System.out.println("runtime exception caused");
        }

        User user1 = userService.getById(1L);
        Product product1 = productService.getById(1L);
        // 全部不回滚
        Assertions.assertEquals(user.getMoney().subtract(BigDecimal.valueOf(100.0)), user1.getMoney());
        Assertions.assertEquals(product.getStock() - 1, product1.getStock());
    }

    /**
     * 一个开启了@Transactional方法，调用另一个没有加@Transactional的方法
     * 加入事务，有异常全部回滚
     */
    @Test
    public void testCallNoTransactional() {
        User user = userService.getById(1L);
        Product product = productService.getById(1L);

        try {
            testService.testNewCallNoTransactional();
        } catch (RuntimeException r) {
            System.out.println("runtime exception caused");
        }

        User user1 = userService.getById(1L);
        Product product1 = productService.getById(1L);
        // 全部回滚
        Assertions.assertEquals(user.getMoney(), user1.getMoney());
        Assertions.assertEquals(product.getStock(), product1.getStock());
    }

    /**
     * default
     * Required
     * 若当前没有事务，开启一个事务
     * 若当前有事务，加入当前的事务
     */
    @Test
    public void testRequiredPropagation() {
        User user = userService.getById(1L);
        Product product = productService.getById(1L);

        try {
            testService.testRequired();
        } catch (RuntimeException r) {
            System.out.println("runtime exception caused");
        }

        User user1 = userService.getById(1L);
        Product product1 = productService.getById(1L);
        // 全部回滚
        Assertions.assertEquals(user.getMoney(), user1.getMoney());
        Assertions.assertEquals(product.getStock(), product1.getStock());

    }

    /**
     * RequiredNew
     *
     * 开启一个新的事务，和当前的事务是两个事务。
     */
    @Test
    public void testRequiredNewPropagation() {
        User user = userService.getById(1L);
        Product product = productService.getById(1L);

        try {
            testService.testRequiredNew();
        } catch (RuntimeException r) {
            System.out.println("runtime exception caused");
        }

        User user1 = userService.getById(1L);
        Product product1 = productService.getById(1L);
        // 回滚
        Assertions.assertEquals(user.getMoney(), user1.getMoney());
        // 不回滚
        Assertions.assertEquals(product.getStock() - 1, product1.getStock());
    }

    /**
     * Nested 事务嵌套
     * 在当前的事务1内部，开启一个子事务2
     * 和RequiredNew区别在于，子事务2需要等事务1完成后一起提交
     * 因此后续的事务1异常，事务2的操作也会回滚
     */
    @Test
    public void testNestedPropagation() {
        User user = userService.getById(1L);
        Product product = productService.getById(1L);

        try {
            testService.testNested();
        } catch (RuntimeException r) {
            System.out.println("runtime exception caused");
        }

        User user1 = userService.getById(1L);
        Product product1 = productService.getById(1L);
        // 回滚
        Assertions.assertEquals(user.getMoney(), user1.getMoney());
        // 回滚
        Assertions.assertEquals(product.getStock(), product1.getStock());
    }

    /**
     * 测试内嵌事务异常，外部事务不提交
     */
    @Test
    public void testNestedPropagationEx1() {
        User user = userService.getById(1L);
        Product product = productService.getById(1L);

        try {
            testService.testNestedEx1();
        } catch (RuntimeException ignored) {
        }

        User user1 = userService.getById(1L);
        Product product1 = productService.getById(1L);
        // 外部，回滚
        Assertions.assertEquals(user.getMoney(), user1.getMoney());
        // nested内部异常，回滚
        Assertions.assertEquals(product.getStock(), product1.getStock());
    }

    /**
     * 内嵌事务异常，无法提交
     * 但是外部事务捕获并忽略，提交外部事务
     */
    @Test
    public void testNestedPropagationEx2() {
        User user = userService.getById(1L);
        Product product = productService.getById(1L);

        // 内部处理了，不抛出异常
        testService.testNestedEx2();

        User user1 = userService.getById(1L);
        Product product1 = productService.getById(1L);
        // 外部，提交
        Assertions.assertEquals(user.getMoney().subtract(BigDecimal.valueOf(100.0)), user1.getMoney());
        // nested内部异常，回滚，被外部事务捕获，忽略
        Assertions.assertEquals(product.getStock(), product1.getStock());
    }


    /**
     * support:
     *  当前存在事务，则加入该事务
     *  否则，无事务执行
     * support1 都回滚
     */
    @Test
    public void testSupportsPropagation1() {
        User user = userService.getById(1L);
        Product product = productService.getById(1L);

        try {
            // supports case 1
            testService.testSupports1();
        } catch (RuntimeException r) {
            System.out.println("runtime exception caused");
        }

        User user1 = userService.getById(1L);
        Product product1 = productService.getById(1L);
        // 回滚
        Assertions.assertEquals(user.getMoney(), user1.getMoney());
        // 回滚
        Assertions.assertEquals(product.getStock(), product1.getStock());
    }

    /**
     * support:
     *  当前存在事务，则加入该事务
     *  否则，无事务执行
     * support2 都不回滚
     */
    @Test
    public void testSupportsPropagation2() {
        User user = userService.getById(1L);
        Product product = productService.getById(1L);

        try {
            // supports case 2
            testService.testSupports2();
        } catch (RuntimeException r) {
            System.out.println("runtime exception caused");
        }

        User user1 = userService.getById(1L);
        Product product1 = productService.getById(1L);
        // 不回滚
        Assertions.assertEquals(user.getMoney().subtract(BigDecimal.valueOf(100.0)), user1.getMoney());
        // 不回滚
        Assertions.assertEquals(product.getStock() - 1, product1.getStock());
    }

    /**
     * Not support 不开启事务的情况下执行
     */
    @Test
    public void testNotSupported() {
        User user = userService.getById(1L);
        Product product = productService.getById(1L);

        try {
            testService.testNotSupported();
        } catch (RuntimeException ignored) {
        }

        User user1 = userService.getById(1L);
        Product product1 = productService.getById(1L);
        // 不回滚
        Assertions.assertEquals(user.getMoney().subtract(BigDecimal.valueOf(100.0)), user1.getMoney());
        // 回滚
        Assertions.assertEquals(product.getStock(), product1.getStock());
    }

    /**
     * Mandatory：
     * 确保当前有事务
     * 当前没开启事务，就抛异常
     */
    @Test
    public void testMandatory() {
        Assertions.assertDoesNotThrow(() -> {
            User user = userService.getById(1L);
            testService.testMandatory1();
            User user1 = userService.getById(1L);
            Assertions.assertEquals(user.getMoney().subtract(BigDecimal.valueOf(100.0)), user1.getMoney());
        });

        Assertions.assertThrows(IllegalTransactionStateException.class, () -> {
            testService.testMandatory2();
        });
    }

    /**
     * Never:
     * 确保当前无事务
     * 当前在事务中，就抛异常
     */
    @Test
    public void testNever() {
        Assertions.assertDoesNotThrow(() -> {
            Product product = productService.getById(1L);
            testService.testNever1();
            Product product1 = productService.getById(1L);
            Assertions.assertEquals(product.getStock() - 1, product1.getStock());
        });
        Assertions.assertThrows(IllegalTransactionStateException.class, () -> {
            testService.testNever2();
        });
    }
}