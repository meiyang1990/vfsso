/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.client.provider;

import java.io.UnsupportedEncodingException;

import com.netfinworks.rest.render.FrameDataProvider;
import com.netfinworks.vfsso.client.common.SsoApiConfig;
import com.netfinworks.vfsso.client.common.VfSsoClientConfig;
import com.netfinworks.vfsso.client.common.VfSsoClientLogger;

/**
 * <p>登录Url提供者</p>
 * @author huipeng
 * @version $Id: LoginUrlProvider.java, v 0.1 Jun 18, 2014 4:11:38 PM knico Exp $
 */
public class LoginUrlProvider implements FrameDataProvider {
    private String appIndexUrl;

    /**
     * @return the appIndexUrl
     */
    public String getAppIndexUrl() {
        return appIndexUrl;
    }

    /**
     * @param appIndexUrl the appIndexUrl to set
     */
    public void setAppIndexUrl(String appIndexUrl) {
        this.appIndexUrl = appIndexUrl;
    }

    /* (non-Javadoc)
     * @see com.netfinworks.rest.render.FrameDataProvider#provide()
     */
    @Override
    public Object provide() {
        SsoApiConfig config = VfSsoClientConfig.getDefaultApiConfig();
        try {
            return config != null ? config.buildLoginUrl(appIndexUrl) : "";
        } catch (UnsupportedEncodingException e) {
            VfSsoClientLogger.getLogger().error("provide: get login url", e);
        }
        return "";
    }

}
