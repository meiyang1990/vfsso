/**
 * 
 */
package com.netfinworks.vfsso.client.cas.listener;

import java.io.IOException;

import org.slf4j.Logger;

import com.netfinworks.vfsso.client.common.CasCookie;
import com.netfinworks.vfsso.client.common.CasEvent;
import com.netfinworks.vfsso.client.common.VfSsoClientLogger;

/**
 * <p>默认的SSO Filter listener</p>
 * @author huipeng
 * @version $Id: DefaultListener.java, v 0.1 Jun 18, 2014 3:25:35 PM knico Exp $
 */
public class DefaultListener extends BaseCasClientListener {
    private static Logger logger = VfSsoClientLogger.getLogger();

    @Override
    public void handleNotLoginEvent(CasEvent event) {
        logger.info("vfsso redirect to login from url : {}", event.getRequest().getRequestURI());
        String returnUrl = event.getRequest().getRequestURL().toString();
        String queryString = event.getRequest().getQueryString();
        // 去掉queryString中的GuardianCookie信息
        queryString = removeCookieParam(queryString);
        if (queryString != null) {
            returnUrl = returnUrl + "?" + queryString;
        }
        // 未登录 跳转到登录页面
        try {
            event.getResponse().sendRedirect(
                event.getConfig().getApiConfig().buildLoginUrl(returnUrl));
        } catch (IOException e) {
            logger.error("重定向异常", e);
        }

    }

    public static String removeCookieParam(String queryString) {
        if (queryString != null && queryString.indexOf(CasCookie.CAS_TOKEN_COOKIE_NAME) >= 0) {
            if (queryString.indexOf("&") < 0) {
                int queryStart = queryString.indexOf("?");
                if (queryStart < 0) {
                    return null;
                } else {
                    return queryString.substring(0, queryStart);
                }
            }
            StringBuffer buf = new StringBuffer();
            int fromIndex = queryString.indexOf(CasCookie.CAS_TOKEN_COOKIE_NAME);
            int endIndex = queryString.indexOf("&", fromIndex);

            buf.append(queryString.subSequence(0,
                queryString.indexOf(CasCookie.CAS_TOKEN_COOKIE_NAME)));
            if (endIndex >= 0) {
                buf.append(queryString.substring(endIndex + 1));
            }
            String str = buf.toString();
            while (str.endsWith("&")) {
                str = str.substring(0, str.length() - 1);
            }
            if (str.indexOf(CasCookie.CAS_TOKEN_COOKIE_NAME) >= 0) {
                return removeCookieParam(str);
            }
            return str;
        }
        return queryString;
    }

    public static void main(String[] args) {
        String str = CasCookie.CAS_TOKEN_COOKIE_NAME + "=231231243&hellow=www&sss=1";
        System.out.println(removeCookieParam(str));
        str = "http://www.com/saaa?" + CasCookie.CAS_TOKEN_COOKIE_NAME
              + "=231231243&hellow=www&sss=1";
        System.out.println(removeCookieParam(str));
        str = "http://www.com/saaa?" + CasCookie.CAS_TOKEN_COOKIE_NAME + "=231231243";
        System.out.println(removeCookieParam(str));
        str = "http://www.com/saaa?hellow=www&sss=1&" + CasCookie.CAS_TOKEN_COOKIE_NAME
              + "=231231243&hellow=www&sss=1";
        System.out.println(removeCookieParam(str));
        str = "http://www.com/saaa?hellow=www&sss=1&" + CasCookie.CAS_TOKEN_COOKIE_NAME
              + "=231231243";
        System.out.println(removeCookieParam(str));
        str = "hellow=www&sss=1&" + CasCookie.CAS_TOKEN_COOKIE_NAME + "=231231243";
        System.out.println(removeCookieParam(str));
    }
}
