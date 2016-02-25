/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.client.provider;

import com.netfinworks.rest.render.FrameDataProvider;
import com.netfinworks.vfsso.client.authapi.VfSsoUser;
import com.netfinworks.vfsso.client.common.SsoApiConfig;
import com.netfinworks.vfsso.client.common.VfSsoClientConfig;

/**
 * <p>登出Url提供者</p>
 * @author huipeng
 * @version $Id: LoginUrlProvider.java, v 0.1 Jun 18, 2014 4:11:38 PM knico Exp $
 */
public class LogoutUrlProvider implements FrameDataProvider {
    /* (non-Javadoc)
     * @see com.netfinworks.rest.render.FrameDataProvider#provide()
     */
    @Override
    public Object provide() {
        SsoApiConfig config = VfSsoClientConfig.getDefaultApiConfig();
        return config != null ? config.buildLogoutUrl(VfSsoUser.getCurrentToken()) : "";
    }

}
