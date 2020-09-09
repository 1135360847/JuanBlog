package com.zzw.common.core.util;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：zhangzhiwen
 * @date ：Created in 2019/10/29 21:08
 * @description：cookie工具
 * @modified By：
 */
public class CookieUtil {

    /**
     * @param response
     * @param name
     * @param value
     * @param maxAge   过期时间
     */
    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }


    public static Cookie get(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (ObjectUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
