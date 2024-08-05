package com.leder.super_car.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @className: UserProp
 * @author: xiaomao
 * @date: 2024/8/4 12:11
 * @Version: 1.0
 * @description: 用户默认信息
 */

@Data
@Component
@ConfigurationProperties(prefix = "default")
public class UserProp {

    /**
     * @Description: 昵称前缀
     * @Author: xiaomao
     * @Date: 2024/8/4 12:13
     */
    private String nickNamePrefix;



    /**
     * @Description: 默认头像
     * @Author: xiaomao
     * @Date: 2024/8/4 12:13
     */
    private String headImage;

}
