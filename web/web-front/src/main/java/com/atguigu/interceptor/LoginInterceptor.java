package com.atguigu.interceptor;

import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.util.WebUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 包名:com.atguigu.interceptor
 *
 * @author Leevi
 * 日期2022-03-30  20:53
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否已登录
        if (request.getSession().getAttribute("USER") == null) {
            //未登录
            WebUtil.writeJSON(response,Result.build("未登录", ResultCodeEnum.LOGIN_AUTH));
            return false;
        }
        return true;
    }
}