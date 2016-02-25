/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.auth.authenticator.impl;

import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.netfinworks.authorize.ws.request.impl.RequestHeader;
import com.netfinworks.rest.filter.RawHttpHolder;

/**
 * <p>Ma Authorize头文件生成器</p>
 * @author huipeng
 * @version $Id: MaAuthHeaderGener.java, v 0.1 Jun 26, 2014 10:23:59 AM knico Exp $
 */
public class MaAuthHeaderGener {

    private String requestOperator;
    private String version;
    private String localIp;

    /**
     * @return the requestOperator
     */
    public String getRequestOperator() {
        return requestOperator;
    }

    /**
     * @param requestOperator the requestOperator to set
     */
    public void setRequestOperator(String requestOperator) {
        this.requestOperator = requestOperator + "-" + new Random().nextInt(100);
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @param appId
     * @param operatorType 
     * @return
     */
    public RequestHeader generate(String appId, String operatorType) {
        RequestHeader ret = new RequestHeader();
        ret.setOperatorType(operatorType);
        ret.setRequestNo(genRequestNo());
        ret.setRequestTime(new Date());
        ret.setSourceId(appId);
        ret.setVersion(version);
        ret.setRequestOperator(requestOperator);
        ret.setIp(getLocalIp());
        return ret;
    }

    private static long requestCount;

    /**
     * @return
     */
    private String genRequestNo() {
        return "" + (requestCount++);
    }

    /**
     * @return
     */
    private String getLocalIp() {
        if (localIp == null) {
            HttpServletRequest hsr = RawHttpHolder.getServletRequest();
            if (hsr != null) {
                localIp = hsr.getLocalAddr();
            }
        }
        return localIp;
    }

}
