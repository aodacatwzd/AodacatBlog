package com.example.demo.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SessionUtils
{
    public static void setSession(String sessionName, Object obj)
    {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        request.getSession().setAttribute(sessionName, obj);
    }

    public static <T> T getSession(String sessionName)
    {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        return (T) session.getAttribute(sessionName);
    }
}
