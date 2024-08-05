package com.leder.super_car.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leder.super_car.pojo.po.UserAddr;
import com.leder.super_car.pojo.vo.UserAddrVo;

import java.util.List;


/**
 * @className: UserAddrService
 * @author: xiaomao
 * @date: 2024/7/29 20:15
 * @Version: 1.0
 * @description: 用户地址接口
 */

public interface UserAddrService extends IService<UserAddr>{


    /**
     * @Description: 获取用户地址列表
     * @Author: xiaomao
     * @Date: 2024/7/31 22:14
     * @return java.util.List<com.leder.super_car.pojo.po.UserAddr>
     */
    List<UserAddrVo> doGetUserAddrList();

}
