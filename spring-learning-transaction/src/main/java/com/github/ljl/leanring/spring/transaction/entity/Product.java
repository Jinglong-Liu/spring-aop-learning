package com.github.ljl.leanring.spring.transaction.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @program: spring-learning
 * @description:
 * @author: ljl
 * @create: 2024-08-20 11:40
 **/

@Data
@TableName("products")
public class Product {

    private Long id;

    private String name;

    private BigDecimal price;

    private Integer stock;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
