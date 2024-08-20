package com.github.ljl.leanring.spring.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.ljl.leanring.spring.transaction.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @program: spring-learning
 * @description:
 * @author: ljl
 * @create: 2024-08-20 12:00
 **/

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    @Update("update products set stock = stock - #{stock} where id = #{id}")
    void deductStock(@Param("id") Long id, @Param("stock") Integer stock);
}
