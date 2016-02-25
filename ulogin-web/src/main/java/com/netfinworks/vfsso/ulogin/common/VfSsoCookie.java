/**
 * 
 */
package com.netfinworks.vfsso.ulogin.common;

import javax.servlet.http.Cookie;

import com.netfinworks.rest.filter.RawHttpHolder;
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

    public static final void removeCookie() {
        Cookie cookie = new Cookie(VfSsoConstants.CAS_TOKEN_COOKIE_NAME, "");
        cookie.setMaxAge(0);
        RawHttpHolder.setCookie(cookie);
    }

    public static void setCookie(String token) {
        RawHttpHolder.setCookie(makeCookie(token));
    }

    /**
     * 获取登录cookie
     * @return
     */
    public static Cookie getCookie() {
        return RawHttpHolder.getCookie(VfSsoConstants.CAS_TOKEN_COOKIE_NAME);
    }
}
