package com.leder.super_car.controller.api.user;

import com.leder.super_car.pojo.vo.UserAddrVo;
import com.leder.super_car.service.UserAddrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @className: UserAddrController
 * @author: xiaomao
 * @date: 2024/7/31 21:58
 * @Version: 1.0
 * @description: 用户地址控制层
 */

@RestController
@RequestMapping("/userAddr")
public class UserAddrController {


    @Resource
    private UserAddrService userAddrService;

    @Value("${api.version}")
    private String apiVersion;



    /**
     * @Description: 获取用户地址列表
     * @Author: xiaomao
     * @Date: 2024/7/31 22:05
     * @return java.util.List<com.leder.super_car.pojo.po.UserAddr>
     */

    @GetMapping("/${api.version}/getUserAddrList")
    public List<UserAddrVo> getUserAddrList(){

        return userAddrService.doGetUserAddrList();

    }


}
