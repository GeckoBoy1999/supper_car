package com.leder.super_car.pojo.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @className: LoginParam
 * @author: xiaomao
 * @date: 2024/8/1 23:07
 * @Version: 1.0
 * @description: 登录参数
 */

@Data
public class LoginParam {




    /**
     * 手机号码，遵循特定的格式和非空规则
     *
     * @Pattern注解用于校验手机号码的格式是否符合正则表达式"[0-9-()（）]{7,18}"，该正则表达式定义了手机号码的合法字符和长度范围
     * @NotBlank注解用于确保手机号码字段不为空或仅包含空白字符
     */
    @Pattern(regexp = "[0-9-()（）]{7,18}",message = "手机号码格式错误")
    @NotBlank(message = "手机号码不能为空")
    private String mobile;




    /**
     * 密码字段，必须非空
     *
     * @NotEmpty注解用于确保密码字段不为空
     */
    @NotEmpty(message = "密码不能为空")
    private String passWord;



    /**
     * 验证码字段，用于用户注册或登录时的额外验证
     */
    private String code;



    /**
     * 类型字段，用于区分或标识不同类型的用户或操作
     */
    private Integer type;






}
