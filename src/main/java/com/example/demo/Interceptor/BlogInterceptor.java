package com.example.demo.Interceptor;

import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.util.UUID;

public class BlogInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserService userService = new UserService();

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)throws Exception
    {
        //System.out.println("我是拦截器：我证明我进来了");
        HttpSession session=request.getSession();
        session.setAttribute("url",request.getRequestURL());
        session.setMaxInactiveInterval(180);
        //System.out.println(session.getId());
        Object username=session.getAttribute("username");
        if(request.getRequestURL().indexOf("articleOpen")!=-1 && request.getMethod().equals("GET")){
            System.out.println(111);
            return true;
        }
        if(username==null)
        {
            System.out.println("not login");
            response.sendRedirect("/login");
            return false;
        }
        System.out.println("user login");
        return true;
    }
    /**
     * 生成视图时执行，可以用来处理异常，并记录在日志中
     */
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object arg2, Exception exception){

    }

    /** -
     * 生成视图之前执行，可以修改ModelAndView
     */
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object arg2, ModelAndView arg3) {

    }

}
