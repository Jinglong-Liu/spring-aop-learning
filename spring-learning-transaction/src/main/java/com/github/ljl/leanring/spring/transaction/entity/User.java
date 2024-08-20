package com.github.ljl.leanring.spring.transaction.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @program: spring-learning
 * @description:
 * @author: ljl
 * @create: 2024-08-20 11:29
 **/

@Data
@TableName("users")
public class User {

    private Long id;

    private String username;

    private String password;

    private BigDecimal money;

    private String email;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
