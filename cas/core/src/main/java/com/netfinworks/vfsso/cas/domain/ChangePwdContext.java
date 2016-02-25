/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.cas.domain;

/**
 * <p>修改密码上下文</p>
 * @author huipeng
 * @version $Id: ChangePwdContext.java, v 0.1 Jun 10, 2014 10:27:14 AM knico Exp $
 */
public class ChangePwdContext extends CasContext {
    private ICasSession user;
    private String      password;
    private String      newPassword;

    /**
     * @return the user
     */
    public ICasSession getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(ICasSession user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
