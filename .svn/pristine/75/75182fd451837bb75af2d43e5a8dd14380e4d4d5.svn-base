/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.cas.domain;

import java.io.Serializable;
import java.util.Date;

import com.netfinworks.vfsso.session.enums.SessionStatusKind;

/**
 * <p>注释</p>
 * @author huipeng
 * @version $Id: CasSessionBase.java, v 0.1 Jun 9, 2014 7:28:05 PM knico Exp $
 */
public class CasSessionBase implements ICasSession,Serializable {
    private static final long serialVersionUID = -920310322190882738L;

    private SessionStatusKind sessionStatus;
    private Date              sessionTimestamp;
    private String            authWay;

    /**
     * @param sessionStatus the sessionStatus to set
     */
    public void setSessionStatus(SessionStatusKind sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    /**
     * @param authWay the authWay to set
     */
    public void setAuthWay(String authWay) {
        this.authWay = authWay;
    }

    /**
     * @return the sessionStatus
     */
    public SessionStatusKind getSessionStatus() {
        return sessionStatus;
    }

    /**
     * @return the authWay
     */
    public String getAuthWay() {
        return authWay;
    }

    /**
     * @return the sessionTimestamp
     */
    public Date getSessionTimestamp() {
        return sessionTimestamp;
    }

    /**
     * @param sessionTimestamp the sessionTimestamp to set
     */
    public void setSessionTimestamp(Date sessionTimestamp) {
        this.sessionTimestamp = sessionTimestamp;
    }
}
