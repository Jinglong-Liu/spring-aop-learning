package com.github.ljl.leanring.spring.transaction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.ljl.leanring.spring.transaction.entity.Product;
import com.github.ljl.leanring.spring.transaction.entity.User;
import com.github.ljl.leanring.spring.transaction.mapper.ProductMapper;
import com.github.ljl.leanring.spring.transaction.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @program: spring-learning
 * @description:
 * @author: ljl
 * @create: 2024-08-20 11:39
 **/

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public void deduceStockRequired(Long id, Integer stock) {
        productMapper.deductStock(id, stock);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deduceStockNewRequired(Long id, Integer stock) {
        productMapper.deductStock(id, stock);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void deduceStockNested(Long id, Integer stock) {
        productMapper.deductStock(id, stock);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void deduceStockNestedEx(Long id, Integer stock) {
        productMapper.deductStock(id, stock);
        if (true) {
            // 模拟内嵌事务1.1异常
            throw new RuntimeException("nested required rollback");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deduceStockSupport(Long id, Integer stock) {
        productMapper.deductStock(id, stock);
    }

    @Transactional(propagation = Propagation.NEVER)
    public void deduceStockNever(Long id, Integer stock) {
        productMapper.deductStock(id, stock);
    }
}
