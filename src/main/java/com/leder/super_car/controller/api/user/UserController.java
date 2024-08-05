package com.leder.super_car.controller.api.user;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.leder.super_car.common.annotate.NoAuthorization;
import com.leder.super_car.pojo.param.LoginParam;
import com.leder.super_car.pojo.param.MsgParam;
import com.leder.super_car.pojo.param.RegisterParam;
import com.leder.super_car.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @className: UserController
 * @author: xiaomao
 * @date: 2024/7/30 19:39
 * @Version: 1.0
 * @description: 用户控制层
 */

@RestController
@RequestMapping("/user")
public class UserController {


    @Value("${api.version}")
    private String apiVersion;


    @Resource
    private UserService userService;




    /**
     * @Description: 发送验证🐎
     * @Author: xiaomao
     * @Date: 2024/7/30 22:16
     * @param msgParam:
     */
    @NoAuthorization
    @PostMapping("/${api.version}/sendMsg")
    public void sendMsg( @Validated  @RequestBody MsgParam msgParam){

        this.userService.doSendMsg(msgParam);


    }

    /**
     * @Description: 登录
     * @Author: xiaomao
     * @Date: 2024/8/1 23:26
     * @param loginParam:
     * @return cn.dev33.satoken.stp.SaTokenInfo
     */
    @NoAuthorization
    @PostMapping("/${api.version}/login")
    public SaTokenInfo login(@Validated @RequestBody LoginParam loginParam){

        return this.userService.doLogin(loginParam);


    }


    /**
     * @Description: 注册
     * @Author: xiaomao
     * @Date: 2024/8/4 11:55
     * @param registerParam:
     * @return cn.dev33.satoken.stp.SaTokenInfo
     */
    @NoAuthorization
    @PostMapping("/register")
    public SaTokenInfo register(@Validated @RequestBody RegisterParam registerParam){

        return this.userService.doRegister(registerParam);
    }

    











}
