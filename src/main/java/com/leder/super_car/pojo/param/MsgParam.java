package com.leder.super_car.pojo.param;

import com.leder.super_car.common.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @className: MsgParam
 * @author: xiaomao
 * @date: 2024/7/30 22:03
 * @Version: 1.0
 * @description: 短信参数
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgParam {

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
     * 验证码类型
     *
     * @NotNull
     */
    @NotNull(message = "验证码类型不能为空")
    private CodeEnum codeEnum;

}
