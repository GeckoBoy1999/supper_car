package com.leder.super_car.common.exception;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @className: RatException
 * @author: xiaomao
 * @date: 2024/7/27 20:42
 * @Version: 1.0
 * @description: 自定义异常
 */
@ExceptionMapper(code = "500", msg = "有内鬼，终止交易", msgReplaceable = true)
public class RatException extends RuntimeException{


    public RatException() {
    }

    public RatException(String message) {
        super(message);
    }

    public RatException(String message, Throwable cause) {
        super(message, cause);
    }

    public RatException(Throwable cause) {
        super(cause);
    }

    public RatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
