package com.example.demo.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CookieUtils
{
    public static void setCookie(String cookieName, String token)
    {
        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();

        Cookie addCookie = new Cookie(cookieName, token);
        addCookie.setMaxAge(604800);
        addCookie.setPath("/");
        response.addCookie(addCookie);
    }

    public static String getCookie(String cookieName)
    {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();

        Cookie[] cookieArr = request.getCookies();
        if ((cookieArr != null) && (cookieArr.length > 0)) {
            for (int i = 0; i < cookieArr.length; i++) {
                Cookie cookie = cookieArr[i];
                if (cookie.getName().equals(cookieName)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        deleteCookie(cookieName, response);
                    }
                }
            }

        }

        return "";
    }

    public static void deleteCookie(String token)
    {
        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();

        Cookie deleteCookie = new Cookie(token, "");
        deleteCookie.setPath("/");
        deleteCookie.setMaxAge(0);
        response.addCookie(deleteCookie);
    }

    public static void deleteCookie(String token, HttpServletResponse response)
    {
        Cookie deleteCookie = new Cookie(token, "");
        deleteCookie.setPath("/");
        deleteCookie.setMaxAge(0);
        response.addCookie(deleteCookie);
    }
}