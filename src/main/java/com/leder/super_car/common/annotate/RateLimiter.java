package com.leder.super_car.common.annotate;

import com.leder.super_car.common.enums.LimitTypeEnum;

import java.lang.annotation.*;

/**
 * @annotationName: RateLimiter
 * @author: xiaomao
 * @date: 2024/7/28 11:51
 * @Version: 1.0
 * @description: 限流注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimiter {


    /**限流key**/
    String key() default "RATE-L-";


    /**限流时间，单位秒**/
    int time() default  60;


    /**限流次数**/
    int count() default 100;


    /**限流类型**/
    LimitTypeEnum limitType() default LimitTypeEnum.DEFAULT;

}
