package com.leder.super_car.common.annotate;

import com.leder.super_car.common.enums.AuthenticationTypeEnum;

import java.lang.annotation.*;

/**
 * @annotationName: NoAuthorization
 * @author: xiaomao
 * @date: 2024/7/31 23:49
 * @Version: 1.0
 * @description: 放行请求
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoAuthorization {


    /**
     * 是否需要校验访问权限 默认不校验
     *
     * @return
     */
    boolean permission() default false;

    /**
     * 验证类型，默认游客
     *
     * @return
     */
    AuthenticationTypeEnum type() default AuthenticationTypeEnum.VISITOR;



}
