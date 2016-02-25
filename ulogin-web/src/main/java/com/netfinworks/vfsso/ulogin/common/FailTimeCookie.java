/**
 * 
 */
package com.netfinworks.vfsso.ulogin.common;

import javax.servlet.http.Cookie;

import com.netfinworks.rest.filter.RawHttpHolder;

/**
 * Guardian cookie 信息
 * 
 * @author bigknife
 * 
 */
public class FailTimeCookie {
	private static final String COOKIE_NAME_FAIL_TIME = "__fail_time";

	public static void clearFailCount() {
		Cookie cookie = new Cookie(COOKIE_NAME_FAIL_TIME, "0");
		cookie.setMaxAge(0);
		RawHttpHolder.setCookie(cookie);
	}

	public static int getFailCount() {
		Cookie cookie = RawHttpHolder.getCookie(COOKIE_NAME_FAIL_TIME);
		return cookie == null ? 0 : Integer.parseInt(cookie.getValue());
	}

	public static void increaseFailCount() {
		Cookie cookie = new Cookie(COOKIE_NAME_FAIL_TIME, "" + (getFailCount() + 1));
		cookie.setMaxAge(3600);
		RawHttpHolder.setCookie(cookie);
	}
}
