package com.leder.super_car.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

/**
 * @className: LettuceConfig
 * @author: xiaomao
 * @date: 2024/7/28 11:22
 * @Version: 1.0
 * @description: lettuce配置类
 */
@Configuration
public class LettuceConfig extends CachingConfigurerSupport{


    @Resource
    private RedisConnectionFactory connectionFactory;


    /**
     * @Description:  redis 模板工厂
     * @Author: lqy
     * @return: org.springframework.data.redis.core.RedisTemplate<java.lang.String,java.lang.Object>
     * @Date: 2023/8/27
     * @Time: 13:00
     */

    @Bean
    public RedisTemplate<String,Object> redisTemplate(){

        RedisTemplate<String,Object> template = new RedisTemplate<>();

        // 设置连接工厂
        template.setConnectionFactory(connectionFactory);

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance , ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        jackson2JsonRedisSerializer.setObjectMapper(om);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();

        return template;
    }


    @Bean
    public DefaultRedisScript<Long> limitScript(){
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(limitScriptText());
        redisScript.setResultType(Long.class);
        return redisScript;

    }


    /**
     * @Description: redis限流脚本
     * @Author: xiaomao
     * @Date: 2024/7/28 11:39
     * @return java.lang.String
     */
    private String limitScriptText(){
        return """
                local key = KEYS[1]
                local count = tonumber(ARGV[1])
                local time = tonumber(ARGV[2])
                local current = redis.call('get', key);
                if current and tonumber(current) > count then
                    return tonumber(current);
                end
                current = redis.call('incr', key)
                if tonumber(current) == 1 then
                    redis.call('expire', key, time)
                end
                return tonumber(current);""";
    }
}
