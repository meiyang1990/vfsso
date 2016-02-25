/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.auth.request;

import com.netfinworks.vfsso.cas.domain.ResourceResponse;
import com.netfinworks.vfsso.domain.SsoUser;

/**
 * <p>认证回应</p>
 * @author huipeng
 * @version $Id: AuthResponse.java, v 0.1 Jun 11, 2014 6:03:02 PM knico Exp $
 */
public class AuthResponse extends ResourceResponse {
    private SsoUser user;

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
}
