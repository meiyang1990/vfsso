/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.client.cas.domain;

import com.netfinworks.vfsso.client.authapi.domain.User;

/**
 * <p>鉴权调用结果</p>
 * @author huipeng
 * @version $Id: AuthResp.java, v 0.1 Jun 12, 2014 5:27:39 PM knico Exp $
 */
public class AuthResp {
    private String code;
    private String msg;
    private User   user;

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return the data
     */
    public User getUser() {
        return user;
    }

    /**
     * @param data the data to set
     */
    public void setUser(User data) {
        this.user = data;
    }
}
