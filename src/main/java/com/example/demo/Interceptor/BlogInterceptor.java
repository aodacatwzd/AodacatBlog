package com.example.demo.Interceptor;

import org.apache.catalina.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BlogInterceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)throws Exception
    {
        System.out.println("我是拦截器：我证明我进来了");
        HttpSession session=request.getSession();
        User userInfo = (User)session.getAttribute("userInfo");
        if(userInfo==null)
        {
            System.out.println("我证明用户没有登录");
            response.sendRedirect(request.getContextPath()+"/login.html");
            return false;
        }
        System.out.println("我证明用户已经登录");
        return  true;
    }
    /**
     * 生成视图时执行，可以用来处理异常，并记录在日志中
     */
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object arg2, Exception exception){
        //-----------------//
    }

    /** -
     * 生成视图之前执行，可以修改ModelAndView
     */
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object arg2, ModelAndView arg3)
            throws Exception{
        //----------------------------//
    }
}
