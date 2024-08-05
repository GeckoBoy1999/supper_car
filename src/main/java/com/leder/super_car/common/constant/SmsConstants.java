package com.leder.super_car.common.constant;

/**
 * @className: SmsConstants
 * @author: xiaomao
 * @date: 2024/7/30 22:28
 * @Version: 1.0
 * @description: SMS相关的常量定义
 */

public class SmsConstants {


    /**
     * 在Redis中存储短信数据的键前缀。
     * 使用"sms:"作为前缀，用于标识与短信相关联的数据。
     */
    public static final String REDIS_SMS_KEY_PREFIX = "sms:";

    /**
     * 短信验证码在Redis中的过期时间（秒）。
     * 设置为300秒（5分钟），以确保验证码在合理的时间内有效。
     */
    public static final int REDIS_SMS_CODE_EXPIRE_SECONDS = 300;

    /**
     * 每日发送短信的次数限制。
     * 设置为5次，以防止短信发送功能被滥用。
     */
    public static final int REDIS_SMS_SEND_LIMIT = 5;

    /**
     * 短信发送次数限制的过期时间（秒）。
     * 设置为86400秒（一天），用于控制每日的短信发送频率。
     */
    public static final int REDIS_SMS_SEND_LIMIT_EXPIRE_SECONDS = 86400;

    /**
     * 短信重试延迟时间（秒）。
     * 设置为60秒，当短信发送失败后，系统将在60秒后尝试重新发送。
     */
    public static final int REDIS_SMS_RETRY_DELAY_SECONDS = 60;


    //短信验证码(注册)
    public final static String SMS_REGISTER = "sms-register:";

    //短信验证码(登录)
    public final static String SMS_LOGIN = "sms-login:";

    //短信验证码(修改密码)
    public final static String SMS_ALTER = "sms-alter:";

    //短信验证码(修改支付密码)
    public final static String SMS_PAY_ALTER = "sms-pay-alter:";

    //短信验证码(绑定手机号)
    public final static String SMS_BOUND = "sms-bound:";

    //短信验证码(解绑手机号)
    public final static String SMS_UNBIND = "sms-unbind:";







}
