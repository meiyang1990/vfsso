/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.session.spec;

import java.util.Date;

import com.netfinworks.vfsso.session.enums.SessionStatusKind;

/**
 * <p>Session对象接口</p>
 * @author huipeng
 * @version $Id: ISession.java, v 0.1 Jun 9, 2014 4:58:42 PM knico Exp $
 */
public interface ISession {
    /**
     * 设置状态
     * @param status
     */
    void setSessionStatus(SessionStatusKind status);

    /**
     * Session状态
     * @return
     */
    SessionStatusKind getSessionStatus();

    /**
     * 设置状态
     * @param status
     */
    void setSessionTimestamp(Date time);

    /**
     * Session状态
     * @return
     */
    Date getSessionTimestamp();
}
