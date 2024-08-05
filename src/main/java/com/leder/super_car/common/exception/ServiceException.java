package com.leder.super_car.common.exception;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @className: ServiceException
 * @author: xiaomao
 * @date: 2024/7/30 22:25
 * @Version: 1.0
 * @description: 业务异常类
 */

@ExceptionMapper(code = "401", msg = "业务异常", msgReplaceable = true)
public class ServiceException extends RuntimeException {


    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
