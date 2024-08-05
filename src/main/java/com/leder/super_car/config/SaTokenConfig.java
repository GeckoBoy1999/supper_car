package com.leder.super_car.config;

import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @className: SaTokenConfig
 * @author: xiaomao
 * @date: 2024/7/28 18:27
 * @Version: 1.0
 * @description: saToken配置类
 */
@Configuration
public class SaTokenConfig {

    // Sa-Token 整合 jwt (Simple 简单模式)
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }

}
