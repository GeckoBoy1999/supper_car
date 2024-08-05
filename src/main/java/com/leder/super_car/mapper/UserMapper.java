package com.leder.super_car.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.leder.super_car.pojo.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @interfaceName: UserMapper
 * @author: xiaomao
 * @date: 2024/7/29 20:42
 * @Version: 1.0
 * @description: 用户持久层
 */

@Mapper
public interface UserMapper extends MPJBaseMapper<User> {



    /**
     * @Description: 查询用户代币
     * @Author: xiaomao
     * @Date: 2024/7/30 19:36
     * @param userid:
     * @return java.math.BigDecimal
     */
    BigDecimal selectUserCoinBalance(@Param("userid") Long userid);


}