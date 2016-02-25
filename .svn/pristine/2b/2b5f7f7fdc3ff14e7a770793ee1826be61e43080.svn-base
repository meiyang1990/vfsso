/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.ext.author.impl;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.netfinworks.vfsso.cas.domain.ChangePwdContext;
import com.netfinworks.vfsso.cas.enums.CasFailTypeKind;
import com.netfinworks.vfsso.cas.exception.ChangePasswordException;
import com.netfinworks.vfsso.cas.exception.LoginAuthException;
import com.netfinworks.vfsso.domain.SsoUser;
import com.netfinworks.vfsso.ext.author.LoginAuthBase;

/**
 * <p>基于文件的登录认证</p>
 * @author huipeng
 * @version $Id: FileCfgLoginAuth.java, v 0.1 Jun 10, 2014 3:11:51 PM knico Exp $
 */
public class FileCfgLoginAuth extends LoginAuthBase {
    private static Properties config;
    private String            authFile;

    /**
     * @return the authFile
     */
    public String getAuthFile() {
        return authFile;
    }

    /**
     * @param authFile the authFile to set
     */
    public void setAuthFile(String authFile) {
        this.authFile = authFile;
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.cas.spec.ILoginAuth#changePassword(com.netfinworks.vfsso.cas.domain.ChangePwdContext)
     */
    @Override
    public void changePassword(ChangePwdContext ctx) throws ChangePasswordException {
        throw new ChangePasswordException(CasFailTypeKind.unsupported, "不支持修改密码");
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.ext.author.LoginAuthBase#doAuth(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    protected SsoUser doAuth(String loginName, String password, String dynamicCode)
                                                                                   throws LoginAuthException {
        SsoUser ret = null;
        String info = getAuthConfig().getProperty(loginName);
        if (info != null) {
            String[] props = info.split(",");
            if (props.length < 2) {
                throw new LoginAuthException(CasFailTypeKind.exception, "用户数据异常");
            }
            if (password.equals(props[1])) {
                ret = new SsoUser();
                ret.setLoginName(loginName);
                ret.setName(props[0]);
            } else {
                throw new LoginAuthException(CasFailTypeKind.failed, "密码不正确");
            }
        } else {
            throw new LoginAuthException(CasFailTypeKind.noaccount, "登录名无效");
        }
        return ret;
    }

    /**
     * @return 
     * @throws LoginAuthException 
     * 
     */
    private Properties getAuthConfig() throws LoginAuthException {
        if (config == null) {
            synchronized (this) {
                if (config == null) {
                    config = new Properties();
                    try {
                        config.load(new InputStreamReader(new FileInputStream(authFile), "UTF-8"));
                    } catch (Exception e) {
                        getLogger().error("Load auth config file.", e);
                        throw new LoginAuthException(CasFailTypeKind.failed,
                            "加载用户文件错误" + (getLogger().isDebugEnabled() ? e.getMessage() : ""));
                    }
                }
            }
        }
        return config;
    }

}
