/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.client.provider.domain;

/**
 * <p>提供页面的用户数据</p>
 * @author huipeng
 * @version $Id: UserData.java, v 0.1 Jun 18, 2014 4:06:36 PM knico Exp $
 */
public class UserData {
    private String loginName;
    private String Name;
    private String opName;
    private String userType;
    private String status;
    private String token;

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
        return Name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        Name = name;
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
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }
}
