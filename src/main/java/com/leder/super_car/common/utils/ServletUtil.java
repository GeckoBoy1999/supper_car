package com.leder.super_car.common.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @className: ServletUtil
 * @author: xiaomao
 * @date: 2024/7/28 12:20
 * @Version: 1.0
 * @description: 请求响应工具
 */

public class ServletUtil {



    /**
     * @Description: 获取request
     * @Author: xiaomao
     * @Date: 2024/7/28 12:25
     * @return javax.servlet.http.HttpServletRequest
     */
    public static HttpServletRequest getRequest(){
        return getRequestAttributes().getRequest();
    }

    /**
     * @Description: 获取response
     * @Author: xiaomao
     * @Date: 2024/7/28 12:25
     * @return javax.servlet.http.HttpServletResponse
     */
    public static HttpServletResponse getResponse(){
        return getRequestAttributes().getResponse();
    }


    /**
     * @Description: 获取session
     * @Author: xiaomao
     * @Date: 2024/7/28 12:25
     * @return javax.servlet.http.HttpSession
     */
    public static HttpSession getSession(){
        return getRequest().getSession();
    }



    /**
     * @Description: 获取请求响应对象
     * @Author: xiaomao
     * @Date: 2024/7/28 12:27
     * @return org.springframework.web.context.request.ServletRequestAttributes
     */
    public static ServletRequestAttributes getRequestAttributes(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) requestAttributes;
    }
}
