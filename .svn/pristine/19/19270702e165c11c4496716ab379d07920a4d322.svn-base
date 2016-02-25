/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.client.provider;

import com.netfinworks.rest.render.FrameDataProvider;
import com.netfinworks.vfsso.client.authapi.VfSsoUser;
import com.netfinworks.vfsso.client.authapi.domain.User;
import com.netfinworks.vfsso.client.exception.CasServiceException;
import com.netfinworks.vfsso.client.provider.domain.UserData;

/**
 * <p>当前用户信息提供者（Restful framework support）</p>
 * @author huipeng
 * @version $Id: CurrentUserProvider.java, v 0.1 Jun 18, 2014 4:02:35 PM knico Exp $
 */
public class CurrentUserProvider implements FrameDataProvider {

    /* (non-Javadoc)
     * @see com.netfinworks.rest.render.FrameDataProvider#provide()
     */
    @Override
    public UserData provide() {
        User user;
        try {
            user = VfSsoUser.get();
        } catch (CasServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
        if (user != null) {
            String currentToken = VfSsoUser.getCurrentToken();
            UserData cud = new UserData();
            cud.setLoginName(user.getLoginName());
            cud.setName(user.getName());
            cud.setOpName(user.getOpName());
            cud.setStatus(user.getSessionStatus());
            cud.setUserType(user.getUserType());
            cud.setToken(currentToken);

            return cud;
        }
        return null;
    }

}
