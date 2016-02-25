/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.auth.request;

import com.netfinworks.vfsso.session.enums.SessionStatusKind;

/**
 * <p>注释</p>
 * @author huipeng
 * @version $Id: LoginResponse.java, v 0.1 Jun 26, 2014 6:25:29 PM knico Exp $
 */
public class LoginResponse {
    private String            token;
    private SessionStatusKind status;

    /**
     * @return the status
     */
    public SessionStatusKind getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(SessionStatusKind status) {
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
