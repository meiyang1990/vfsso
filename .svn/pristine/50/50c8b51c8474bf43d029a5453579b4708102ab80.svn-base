/**
 *
 */
package com.netfinworks.vfsso.client.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.netfinworks.vfsso.client.authapi.VfSsoUser;

/**
 * <p>VFSSO CAS公共配置对象</p>
 * @author huipeng
 * @version $Id: GuardianAPIConfig.java, v 0.1 Jun 4, 2014 9:28:22 AM knico Exp $
 */
public class SsoApiConfig {
    /**
     * 
     */
    private static final String URL_SPLITTER   = "/";
    private String              casUrl;
    private String              appId;
    private String              authPath       = "/auth";
    private String              loginPath      = "/login";
    private String              sessionPath    = "/session/";
    private String              sessionExtPath = "/session/ext/";

    private String              loginUrl;
    private String              logoutUrl;

    private String              returnUrlName  = "returnUrl";

    public String buildLogoutUrl(HttpServletRequest request) {
        return this.buildLogoutUrl(getToken(request));
    }

    public String buildLogoutUrl(String token) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getLogoutUrl()).append(getLogoutUrl().indexOf("?") > 0 ? "&" : "?");
        sb.append("token=");
        sb.append(token);
        sb.append("&timestamp=").append(new Date().getTime());
        return sb.toString();
    }

    /**
     * @param returnUrl
     * @return
     * @throws UnsupportedEncodingException
     */
    public String buildLoginUrl(String returnUrl) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getLoginUrl()).append(getLoginUrl().indexOf("?") > 0 ? "&" : "?")
            .append("app=").append(getAppId());
        if (returnUrl != null && returnUrl.length() > 0) {
            returnUrl = URLEncoder.encode(returnUrl, "utf-8");
            sb.append("&").append(getReturnUrlName()).append("=").append(returnUrl);
        }
        return sb.toString();
    }

    /**
     * @return the casUrl
     */
    public String getCasUrl() {
        return casUrl;
    }

    /**
     * @param casUrl the casUrl to set
     */
    public void setCasUrl(String casUrl) {
        if (casUrl != null && casUrl.endsWith(URL_SPLITTER)) {
            casUrl = casUrl.substring(0, casUrl.length() - 1);
        }
        this.casUrl = casUrl;
    }

    /**
     * @return the appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * @param appId the appId to set
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * @return the loginUrl
     */
    public String getLoginUrl() {
        return loginUrl;
    }

    /**
     * @param loginUrl the loginUrl to set
     */
    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    /**
     * @return the logoutUrl
     */
    public String getLogoutUrl() {
        return logoutUrl;
    }

    /**
     * @param logoutUrl the logoutUrl to set
     */
    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    /**
     * @return the returnUrlName
     */
    public String getReturnUrlName() {
        return returnUrlName;
    }

    /**
     * @param returnUrlName the returnUrlName to set
     */
    public void setReturnUrlName(String returnUrlName) {
        this.returnUrlName = returnUrlName;
    }

    /**
     * @return the authPath
     */
    public String getAuthPath() {
        return authPath;
    }

    /**
     * @param authPath the authPath to set
     */
    public void setAuthPath(String authPath) {
        if (authPath != null && !authPath.startsWith(URL_SPLITTER)) {
            authPath = URL_SPLITTER + authPath;
        }
        this.authPath = authPath;
    }

    /**
     * @return the sessionPath
     */
    public String getSessionPath() {
        return sessionPath;
    }

    /**
     * @param sessionPath the sessionPath to set
     */
    public void setSessionPath(String sessionPath) {
        if (sessionPath != null) {
            if (!sessionPath.startsWith(URL_SPLITTER)) {
                sessionPath = URL_SPLITTER + sessionPath;
            }
            if (!sessionPath.endsWith(URL_SPLITTER)) {
                sessionPath = sessionPath + URL_SPLITTER;
            }
        }
        this.sessionPath = sessionPath;
    }

    /**
     * @return the sessionExtPath
     */
    public String getSessionExtPath() {
        return sessionExtPath;
    }

    /**
     * @param sessionExtPath the sessionExtPath to set
     */
    public void setSessionExtPath(String sessionExtPath) {
        if (sessionExtPath != null) {
            if (!sessionExtPath.startsWith(URL_SPLITTER)) {
                sessionExtPath = URL_SPLITTER + sessionExtPath;
            }
            if (!sessionExtPath.endsWith(URL_SPLITTER)) {
                sessionExtPath = sessionExtPath + URL_SPLITTER;
            }
        }
        this.sessionExtPath = sessionExtPath;
    }

    /**
     * @return the loginPath
     */
    public String getLoginPath() {
        return loginPath;
    }

    /**
     * @param loginPath the loginPath to set
     */
    public void setLoginPath(String loginPath) {
        if (loginPath != null && !loginPath.startsWith(URL_SPLITTER)) {
            loginPath = URL_SPLITTER + loginPath;
        }
        this.loginPath = loginPath;
    }

    public final static String getToken(HttpServletRequest request) {
        String token = VfSsoUser.getCurrentToken();
        if (token == null) {
            Cookie cookie = CasCookie.getCookie(request);
            if (cookie != null) {
                token = cookie.getValue();
            }
        }
        return token;
    }

    public String buildCasLoginUrl() {
        return this.getCasUrl() + this.getLoginPath();
    }

    /**
     * @return
     */
    public String buildSessionUrl(String token) {
        return this.getCasUrl() + this.getSessionPath() + token;
    }

    /**
     * @return
     */
    public String buildSessionExtUrl() {
        return this.getCasUrl() + this.getSessionExtPath();
    }

    /**
     * @return
     */
    public String buildAuthUrl() {
        return this.getCasUrl() + this.getAuthPath();
    }
}
