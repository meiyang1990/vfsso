/**
 * 
 */
package com.netfinworks.vfsso.ulogin.common;

import com.netfinworks.vfsso.domain.SsoUser;

/**
 * 登录响应对象
 * @author bigknife
 *
 */
public class LoginResponse {
    private String  returnUrl;
    private SsoUser user;
    private String  token;

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the user
     */
    public SsoUser getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(SsoUser user) {
        this.user = user;
    }

    /**
     * @return the returnUrl
     */
    public String getReturnUrl() {
        return returnUrl;
    }

    /**
     * @param returnUrl the returnUrl to set
     */
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

}
