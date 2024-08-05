package com.leder.super_car.config;

import com.leder.super_car.config.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @className: webConfig
 * @author: xiaomao
 * @date: 2024/7/31 23:38
 * @Version: 1.0
 * @description: 网络访问配置
 */

@Configuration
public class webConfig implements WebMvcConfigurer {


    @Resource
    private TokenInterceptor tokenInterceptor;

    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPa-thPatterns 用于添加拦截规则
        //excludePathPatterns 用于排除拦截
        /*要执行的拦截器*/
        registry.addInterceptor(tokenInterceptor).
                addPathPatterns("/**")
                .excludePathPatterns("/**/*.html","/**/*.css","/**/*.js");;
    }



}
