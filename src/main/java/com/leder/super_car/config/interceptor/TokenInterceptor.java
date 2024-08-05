package com.leder.super_car.config.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leder.super_car.common.annotate.NoAuthorization;
import com.leder.super_car.common.utils.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @className: TokenInterceptor
 * @author: xiaomao
 * @date: 2024/7/31 23:43
 * @Version: 1.0
 * @description: 登录认证拦截器
 */

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {


    /**
     * @Description: 前置拦截器
     * @Author: xiaomao
     * @Date: 2024/7/31 23:45
     * @param request:
     * @param response:
     * @param handler:
     * @return boolean
     */
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        NoAuthorization annotation = handlerMethod.getMethod().getAnnotation(NoAuthorization.class);

        if (ObjectUtil.isNotNull(annotation)){
            return true;
        }

        StpUtil.checkLogin();
        String userId = StpUtil.getLoginId().toString();
        ThreadLocalUtils.set("userId",userId);
        return true;
    }





    /**
     * @Description: 后置拦截器
     * @Author: xiaomao
     * @Date: 2024/7/31 23:45
     * @param request:
     * @param response:
     * @param handler:
     * @param modelAndView:
     */
    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, ModelAndView modelAndView)  {
    }



    /**
     * @Description: 拦截器执行完成
     * @Author: xiaomao
     * @Date: 2024/7/31 23:45
     * @param request:
     * @param response:
     * @param handler:
     * @param ex:
     */
    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex)  {
        ThreadLocalUtils.remove();
    }

}
