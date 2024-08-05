package com.leder.super_car.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.leder.super_car.pojo.param.LoginParam;
import com.leder.super_car.pojo.param.MsgParam;
import com.leder.super_car.pojo.param.RegisterParam;
import com.leder.super_car.pojo.po.User;
import com.baomidou.mybatisplus.extension.service.IService;



/**
 * @className: UserService
 * @author: xiaomao
 * @date: 2024/7/29 20:15
 * @Version: 1.0
 * @description: 用户接口
 */

public interface UserService extends IService<User>{


    /**
     * @Description: 发送验证🐎
     * @Author: xiaomao
     * @Date: 2024/7/30 22:23
     * @param msgParam:
     */
    void doSendMsg(MsgParam msgParam);

    /**
     * @Description: 登录
     * @Author: xiaomao
     * @Date: 2024/8/1 23:27
     * @param loginParam:
     * @return cn.dev33.satoken.stp.SaTokenInfo
     */
    SaTokenInfo doLogin(LoginParam loginParam);

    /**
     * @Description: 注册
     * @Author: xiaomao
     * @Date: 2024/8/4 11:54
     * @param registerParam:
     * @return cn.dev33.satoken.stp.SaTokenInfo
     */
    SaTokenInfo doRegister(RegisterParam registerParam);
}
