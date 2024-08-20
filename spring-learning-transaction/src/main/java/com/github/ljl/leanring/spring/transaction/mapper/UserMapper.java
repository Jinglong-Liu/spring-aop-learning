package com.github.ljl.leanring.spring.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.ljl.leanring.spring.transaction.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * @program: spring-learning
 * @description:
 * @author: ljl
 * @create: 2024-08-20 12:00
 **/

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Update("update users set users.money = users.money - #{money} where id = #{userId};")
    void deductMoney(@Param("userId") Long userId, @Param("money") BigDecimal money);
}
