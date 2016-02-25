/**
 * 
 */
package com.netfinworks.vfsso.cas.sevlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netfinworks.vfsso.cas.VfSsoConstants;

/**
 * <p>VFSSO Cookie</p>
 * @author huipeng
 * @version $Id: VfSsoCookie.java, v 0.1 Jun 10, 2014 3:58:21 PM knico Exp $
 */
public class VfSsoCookie {

    public static Cookie makeCookie(String token) {
        Cookie cookie = new Cookie(VfSsoConstants.CAS_TOKEN_COOKIE_NAME, token);
        cookie.setPath("/");
        return cookie;
    }

    public static final void removeCookie(HttpServletResponse resp) {
        Cookie cookie = new Cookie(VfSsoConstants.CAS_TOKEN_COOKIE_NAME, "");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }

    public static final void removeCookie(HttpServletResponse resp, String domain) {
        Cookie cookie = new Cookie(VfSsoConstants.CAS_TOKEN_COOKIE_NAME, "");
        cookie.setDomain(domain);
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }

    public static void setCookie(String token, HttpServletResponse resp) {
        resp.addCookie(makeCookie(token));
    }

    public static void setCookie(String token, HttpServletResponse resp, String domain, String path) {
        Cookie cookie = makeCookie(token);
        if (domain != null) {
            cookie.setDomain(domain);
        }
        if (path != null) {
            cookie.setPath(path);
        }
        resp.addCookie(cookie);
    }

    /**
     * 获取登录cookie
     * @return
     */
    public static Cookie getCookie(HttpServletRequest hsr) {
        Cookie[] cookies = hsr.getCookies();
        Cookie ret = null;
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (VfSsoConstants.CAS_TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                    ret = cookie;
                    break;
                }
            }
        }
        return ret;
    }
}
