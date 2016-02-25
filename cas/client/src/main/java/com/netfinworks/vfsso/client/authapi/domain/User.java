/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.client.authapi.domain;

import java.io.Serializable;

/**
 * <p>用户（Session）</p>
 * @author huipeng
 * @version $Id: User.java, v 0.1 Jun 12, 2014 6:00:37 PM knico Exp $
 */
public class User implements Serializable {
    /**
    *
    */
    private static final long serialVersionUID = 2338122148878857814L;
    private String            id;
    private String            opId;
    private String            loginName;
    private String            name;
    private String            opName;
    private String            userType;
    private String            sessionStatus;
    private String            group;
    private String            ext;

    /**
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the loginName
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName the loginName to set
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the opId
     */
    public String getOpId() {
        return opId;
    }

    /**
     * @param opId the opId to set
     */
    public void setOpId(String opId) {
        this.opId = opId;
    }

    /**
     * @return the opName
     */
    public String getOpName() {
        return opName;
    }

    /**
     * @param opName the opName to set
     */
    public void setOpName(String opName) {
        this.opName = opName;
    }

    /**
     * @return the userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * @return the sessionStatus
     */
    public String getSessionStatus() {
        return sessionStatus;
    }

    /**
     * @param sessionStatus the sessionStatus to set
     */
    public void setSessionStatus(String sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    /**
     * @return the ext
     */
    public String getExt() {
        return ext;
    }

    /**
     * @param ext the ext to set
     */
    public void setExt(String ext) {
        this.ext = ext;
    }

}