/**
 * 
 */
package com.netfinworks.vfsso.client.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Guardian cookie 信息
 * @author bigknife
 *
 */
public class CasCookie {
    public static final String CAS_TOKEN_COOKIE_NAME = "com.vfsso.cas.token";

    public static Cookie makeCasCookieFromQueryString(String queryString) {
        if (queryString != null && queryString.indexOf(CAS_TOKEN_COOKIE_NAME) >= 0) {
            String tmp = queryString.substring(queryString.indexOf(CAS_TOKEN_COOKIE_NAME)
                                               + CAS_TOKEN_COOKIE_NAME.length() + 1);
            if (tmp.indexOf("&") >= 0) {
                tmp = tmp.substring(0, tmp.indexOf("&"));
            }
            VfSsoClientLogger.getLogger().debug(
                "Found vfsso token by url,token:{}, query string: {}", tmp, queryString);
            if (tmp.length() > 0) {
                return makeGuardianCookie(tmp);
            }
        }
        return null;
    }

    /**
     * 获取登录cookie
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (CAS_TOKEN_COOKIE_NAME.equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static Cookie makeGuardianCookie(String token) {
        Cookie cookie = new Cookie(CasCookie.CAS_TOKEN_COOKIE_NAME, token);
        cookie.setPath("/");
        return cookie;
    }
}
