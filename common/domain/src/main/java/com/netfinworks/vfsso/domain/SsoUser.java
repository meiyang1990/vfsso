/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.domain;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.netfinworks.vfsso.cas.domain.CasSessionBase;

/**
 * <p>单点登录用户</p>
 * @author huipeng
 * @version $Id: SsoUser.java, v 0.1 Jun 4, 2014 5:46:45 PM knico Exp $
 */
public class SsoUser extends CasSessionBase {
    private static final long serialVersionUID = 3413078746827611940L;

    private String            id;
    private String            opId;
    private String            loginName;
    private String            name;
    private String            opName;
    private String            userType;
    private String            status;
    private String            group;
    private String            loginIP;
    private Date              loginTime;
    private String            ext;

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
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

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
     * @return the loginIP
     */
    public String getLoginIP() {
        return loginIP;
    }

    /**
     * @param loginIP the loginIP to set
     */
    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }

    /**
     * @return the loginTime
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * @param loginTime the loginTime to set
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
