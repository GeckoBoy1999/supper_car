package com.leder.super_car.pojo.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @className: RegisterParam
 * @author: xiaomao
 * @date: 2024/8/3 23:48
 * @Version: 1.0
 * @description: 注册参数
 */

@Data
public class RegisterParam {

    /**
     * 手机号码，遵循特定的格式和长度规则
     */
    @Pattern(regexp = "[0-9-()（）]{7,18}",message = "手机号码格式错误")
    @NotBlank(message = "手机号码不能为空")
    private String mobile;

    /**
     * 密码，不能为空
     */
    @NotEmpty(message = "密码不能为空")
    private String passWord;

    /**
     * 验证码，用于验证用户身份的有效性
     */
    private String code;

    /**
     * 类型标识，用于区分不同类型的用户或操作
     */
    private Integer type;

    /**
     * 邀请码，用于追踪用户注册来源或关联推荐人
     */
    private String invitationCode;

}



