/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.cas.domain;

import com.netfinworks.vfsso.session.spec.ISession;

/**
 * <p>CAS Session Interface</p>
 * @author huipeng
 * @version $Id: ICasSession.java, v 0.1 Jun 9, 2014 7:24:28 PM knico Exp $
 */
public interface ICasSession extends ISession {
    /**
     * 认证方式
     * @return
     */
    String getAuthWay();

}
