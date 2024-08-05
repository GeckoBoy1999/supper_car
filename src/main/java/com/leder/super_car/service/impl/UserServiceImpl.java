package com.leder.super_car.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leder.super_car.common.constant.RedisPrefixes;
import com.leder.super_car.common.constant.SmsConstants;
import com.leder.super_car.common.enums.CodeEnum;
import com.leder.super_car.common.exception.ServiceException;
import com.leder.super_car.common.properties.UserProp;
import com.leder.super_car.common.utils.IpUtil;
import com.leder.super_car.common.utils.RedisUtil;
import com.leder.super_car.mapper.InvitationRelationMapper;
import com.leder.super_car.mapper.UserMapper;
import com.leder.super_car.pojo.param.LoginParam;
import com.leder.super_car.pojo.param.MsgParam;
import com.leder.super_car.pojo.param.RegisterParam;
import com.leder.super_car.pojo.po.InvitationRelation;
import com.leder.super_car.pojo.po.User;
import com.leder.super_car.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @className: UserServiceImpl
 * @author: xiaomao
 * @date: 2024/7/29 20:15
 * @Version: 1.0
 * @description: 用户实现类
 */

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserProp userProp;

    @Resource
    private InvitationRelationMapper invitationRelationMapper;


    @Override
    public void doSendMsg(MsgParam msgParam) {

        Object code = redisUtil.get(RedisPrefixes.PROJECT + SmsConstants.REDIS_SMS_KEY_PREFIX + msgParam.getCodeEnum() + msgParam.getMobile());
        if (ObjectUtil.isNotNull(code)) {
            throw new ServiceException("验证码未到期");
        }

        User user = this.baseMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getMobile, msgParam.getMobile()));


        switch (msgParam.getCodeEnum()) {

            case REGISTER -> {
                Assert.isNull(user, () -> {
                    throw new ServiceException("用户已存在");
                });
                //发送注册短信
            }

            case LOGIN -> {

                //发送登录短信

            }

            case FIND_PASSWORD -> {
                Assert.notNull(user, () -> {
                    throw new ServiceException("用户不存在");
                });
                //发送找回密码短信
            }

            case FIND_PAY_PASSWORD -> {
                Assert.notNull(user, () -> {
                    throw new ServiceException("用户不存在");
                });
                //发送找回支付密码短信短信
            }

            default -> throw new ServiceException("发送短信异常");
        }

        //对接短信发送短信

        redisUtil.set(RedisPrefixes.PROJECT + SmsConstants.REDIS_SMS_KEY_PREFIX + msgParam.getCodeEnum() + msgParam.getMobile(), RandomUtil.randomInt(111111, 999999), 1000 * 60 * 5);

    }


    /**
     * @param loginParam:
     * @return cn.dev33.satoken.stp.SaTokenInfo
     * @Description: 用户登录
     * @Author: xiaomao
     * @Date: 2024/8/1 23:27
     */
    @Override
    public SaTokenInfo doLogin(LoginParam loginParam) {

        User user = this.baseMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getMobile, loginParam.getMobile()));
        Assert.notNull(user, () -> {
            throw new ServiceException("用户不存在,请先注册");
        });
        //验证码登录
        if (loginParam.getType() == 1) {

            Object code = redisUtil.get(RedisPrefixes.PROJECT + SmsConstants.REDIS_SMS_KEY_PREFIX + CodeEnum.LOGIN + loginParam.getMobile());
            Assert.notNull(code, () -> {
                throw new ServiceException("验证码不存在");
            });
            Assert.isFalse(code.equals(loginParam.getCode()), () -> {
                throw new ServiceException("验证码错误");
            });
            redisUtil.del(RedisPrefixes.PROJECT + SmsConstants.REDIS_SMS_KEY_PREFIX + CodeEnum.LOGIN + loginParam.getMobile());

        } else {
            //密码登录
            Assert.isFalse(BCrypt.checkpw(loginParam.getPassWord(), user.getPassword()), () -> {
                throw new ServiceException("密码不正确");
            });

        }
        Assert.isTrue(user.getStatus() == 2, () -> {
            throw new ServiceException("用户已禁封");
        });
        StpUtil.login(user.getUserId());
        return StpUtil.getTokenInfo();
    }


    /**
     * @param registerParam:
     * @return cn.dev33.satoken.stp.SaTokenInfo
     * @Description: 注册
     * @Author: xiaomao
     * @Date: 2024/8/4 11:54
     */
    @Override
    public SaTokenInfo doRegister(RegisterParam registerParam) {

        User oldUser = this.baseMapper.selectOne(Wrappers.<User>lambdaQuery().eq(
                User::getMobile, registerParam.getMobile()
        ));

        Assert.isNull(oldUser, () -> {
            throw new ServiceException("当前手机号已经注册");
        });

        Object code = redisUtil.get(RedisPrefixes.PROJECT + SmsConstants.REDIS_SMS_KEY_PREFIX + CodeEnum.REGISTER + registerParam.getMobile());
        Assert.notNull(code,()->{throw new ServiceException("验证码不存在");});
        Assert.isTrue(code.toString().equals(registerParam.getCode()),()->{throw new ServiceException("验证码错误");});
        User user = this.encapsulateTheUser(registerParam.getMobile(), registerParam.getPassWord());


        this.baseMapper.insert(user);
        if (StrUtil.isNotEmpty(registerParam.getInvitationCode())){
            //bindingInvitationRelationships
            this.bindingInvitationRelationships(user.getUserId(),registerParam.getInvitationCode());

        }
        redisUtil.del(RedisPrefixes.PROJECT + SmsConstants.REDIS_SMS_KEY_PREFIX + CodeEnum.REGISTER + registerParam.getMobile());

        StpUtil.login(user.getUserId());
        return StpUtil.getTokenInfo();

    }



//-*************************************************************************************//


    /**
     * @Description: 用户参数填充
     * @Author: xiaomao
     * @Date: 2024/8/4 14:01
     * @param mobile:
     * @param passWor:
     * @return com.leder.super_car.pojo.po.User
     */

    private User encapsulateTheUser(String mobile, String passWor) {
        User newUser = new User();
        newUser.setRegisterIp(IpUtil.getIp());
        newUser.setMobile(mobile);
        newUser.setPassword(BCrypt.hashpw(passWor));
        newUser.setHeadImage(userProp.getHeadImage());
        newUser.setNickname(userProp.getNickNamePrefix()+RandomUtil.randomNumbers(4));
        newUser.setUserCode(RandomUtil.randomString(6));
        newUser.setSource("用户端");
        return newUser;

    }



    /**
     * @Description: 绑定邀请关系
     * @Author: xiaomao
     * @Date: 2024/8/4 20:34
     * @param userId:
     * @param invitation:
     */
    @Async
    public void bindingInvitationRelationships(Long userId,String invitation) {
        User primaryUser = this.baseMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUserCode, invitation));

        if (ObjectUtil.isNotNull(primaryUser)){

            //一级邀请
            InvitationRelation firstOrderRelationship =
                    InvitationRelation.builder().invitedUserId(userId).inviterUserId(primaryUser.getUserId()).level(1).build();
            this.invitationRelationMapper.insert(firstOrderRelationship);



            //二级邀请
            InvitationRelation invitationRelation = this.invitationRelationMapper.selectOne(Wrappers.<InvitationRelation>lambdaQuery().eq(InvitationRelation::getInvitedUserId, primaryUser.getUserId()).eq(InvitationRelation::getLevel, 1));

            User secondaryUser = this.baseMapper.selectOne(Wrappers.<User>lambdaQuery()
                    .eq(User::getUserId, invitationRelation.getInviterUserId()));

            if (ObjectUtil.isNotNull(secondaryUser)){
                InvitationRelation secondLevelInvitationRelationship = InvitationRelation.builder().invitedUserId(userId).inviterUserId(primaryUser.getUserId()).secondaryInviterUserId(secondaryUser.getUserId()).level(2).build();
                this.invitationRelationMapper.insert(secondLevelInvitationRelationship);

            }

        }

    }



}