package com.leder.super_car.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leder.super_car.common.utils.ThreadLocalUtils;
import com.leder.super_car.mapper.UserAddrMapper;
import com.leder.super_car.pojo.po.UserAddr;
import com.leder.super_car.pojo.vo.UserAddrVo;
import com.leder.super_car.service.UserAddrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @className: UserAddrServiceImpl
 * @author: xiaomao
 * @date: 2024/7/29 20:15
 * @Version: 1.0
 * @description: 用户地址实现类
 */

@Slf4j
@Service
public class UserAddrServiceImpl extends ServiceImpl<UserAddrMapper, UserAddr> implements UserAddrService {


    /**
     * @Description: 获取用户地址列表
     * @Author: xiaomao
     * @Date: 2024/7/31 22:14
     * @return java.util.List<com.leder.super_car.pojo.po.UserAddr>
     */
    @Override
    public List<UserAddrVo> doGetUserAddrList() {

        String userId = ThreadLocalUtils.get("userId").toString();

        List<UserAddr> userAddrList = this.baseMapper.selectList(Wrappers.<UserAddr>lambdaQuery()
                .eq(UserAddr::getUserId, userId)
                .orderByDesc(UserAddr::getCommonAddr)
                .orderByDesc(UserAddr::getCreateTime));

        return BeanUtil.copyToList(userAddrList, UserAddrVo.class);
    }



}
