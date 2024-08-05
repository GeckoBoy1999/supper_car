package com.leder.super_car.common.aspects;

import cn.hutool.core.util.ObjectUtil;
import com.leder.super_car.common.annotate.RateLimiter;
import com.leder.super_car.common.enums.LimitTypeEnum;
import com.leder.super_car.common.exception.RatException;
import com.leder.super_car.common.utils.IpUtil;
import com.leder.super_car.common.utils.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * @className: RateLimiterAspect
 * @author: xiaomao
 * @date: 2024/7/28 11:57
 * @Version: 1.0
 * @description: 限流切面
 */
@Component
@Aspect
@Slf4j
public class RateLimiterAspect {

    @Resource
    private RedisTemplate<Object,Object> redisTemplate;

    @Resource
    private RedisScript<Long> limitScript;



    @Before("@annotation(rateLimiter)")
    public void doBefore(JoinPoint point, RateLimiter rateLimiter){
        int count = rateLimiter.count();

        int time = rateLimiter.time();

        LimitTypeEnum limitType = rateLimiter.limitType();

        String key = rateLimiter.key();

        String combineKey = getCombineKey(rateLimiter, point);
        List<Object> keys = Collections.singletonList(combineKey);

        Long number = redisTemplate.execute(limitScript, keys, count, time);

        if (ObjectUtil.isNull(number) || number.intValue() > count){
            throw new RatException("访问过于频繁，请稍后再试");
        }

        log.info("限制请求{},当前请求{}",count,number.intValue());

    }


    /**
     * @Description: 获取键
     * @Author: xiaomao
     * @Date: 2024/7/28 12:34
     * @param rateLimiter:
     * @param point:
     * @return java.lang.String
     */
    public String getCombineKey(RateLimiter rateLimiter, JoinPoint point) {
        StringBuilder stringBuffer = new StringBuilder(rateLimiter.key());

        MethodSignature signature = (MethodSignature) point.getSignature();

        if (rateLimiter.limitType().equals(LimitTypeEnum.IP)) {

            stringBuffer.append(IpUtil.getIpAddr(ServletUtil.getRequest())).append("-");

        }else if (rateLimiter.limitType().equals(LimitTypeEnum.PARAM)){

            Object[] args = point.getArgs();
            if (args.length > 0){
                stringBuffer.append(args[0]).append("-");
            }
        }
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        stringBuffer.append(targetClass.getName()).append("-").append(method.getName());
        stringBuffer.append(method.getName());
        return stringBuffer.toString();
    }
}
