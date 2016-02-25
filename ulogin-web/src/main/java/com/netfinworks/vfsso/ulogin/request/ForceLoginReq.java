/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.ulogin.request;

/**
 * <p>强制登录请求</p>
 * @author huipeng
 * @version $Id: ForceLoginReq.java, v 0.1 Jun 10, 2014 10:55:32 AM knico Exp $
 */
public class ForceLoginReq {
    private String requestId;
    private String captcha;

    /**
     * @return the requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * @return the captcha
     */
    public String getCaptcha() {
        return captcha;
    }

    /**
     * @param captcha the captcha to set
     */
    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
