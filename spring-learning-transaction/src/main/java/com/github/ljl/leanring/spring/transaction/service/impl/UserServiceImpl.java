package com.github.ljl.leanring.spring.transaction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.ljl.leanring.spring.transaction.entity.User;
import com.github.ljl.leanring.spring.transaction.mapper.UserMapper;
import com.github.ljl.leanring.spring.transaction.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @program: spring-learning
 * @description:
 * @author: ljl
 * @create: 2024-08-20 11:38
 **/

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public void deduceMoneyRequired(Long userId, BigDecimal money) {
        userMapper.deductMoney(userId, money);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deduceMoneySupports(Long userId, BigDecimal money) {
        userMapper.deductMoney(userId, money);
        if (true) {
            throw new RuntimeException("deduceMoneySupports");
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void deduceMoneyNotSupport(Long userId, BigDecimal money) {
        userMapper.deductMoney(userId, money);
        if (true) {
            throw new RuntimeException("deduceMoneyNotSupports");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void deduceMoneyMandatory(Long userId, BigDecimal money) {
        userMapper.deductMoney(userId, money);
    }
}
