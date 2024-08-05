package com.leder.super_car.common.enums;

/**
 * @enumName: LimitType
 * @author: xiaomao
 * @date: 2024/7/28 11:44
 * @Version: 1.0
 * @description: 限流类型
 */

public enum LimitTypeEnum {


    /**默认策略全局限流**/
    DEFAULT,




    /**根据请求者ip进行限流**/
    IP,




    /**根据请求参数进行限流**/
    PARAM;
}
