package com.leder.super_car.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;

/**
 * @className: JacksonConfig
 * @author: xiaomao
 * @date: 2024/8/3 23:34
 * @Version: 1.0
 * @description: json序列化配置
 */
@Configuration
public class JacksonConfig {


    @Bean
    public ObjectMapper jacksonObjectMapper() {
        // 创建 ObjectMapper 实例
        ObjectMapper objectMapper = new ObjectMapper();

        // 注册Java 8日期时间模块
        objectMapper.registerModule(new JavaTimeModule());

        // 配置 ObjectMapper 使用 ToStringSerializer 序列化 Long 类型
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.findAndRegisterModules();

        // 注册自定义序列化器
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, new ToStringSerializer());
        module.addSerializer(Long.TYPE, new ToStringSerializer());
        objectMapper.registerModule(module);


        // 禁用某些可能导致敏感数据泄露的特性
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 设置日期格式
        objectMapper.setDateFormat(DateFormat.getDateTimeInstance());




        return objectMapper;
    }
}
