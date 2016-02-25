/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.client.cas.domain;

/**
 * <p>注释</p>
 * @author huipeng
 * @version $Id: LoginResult.java, v 0.1 Jun 26, 2014 6:25:29 PM knico Exp $
 */
public class LoginInfo {
    private String token;
    private String status;

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

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
}
