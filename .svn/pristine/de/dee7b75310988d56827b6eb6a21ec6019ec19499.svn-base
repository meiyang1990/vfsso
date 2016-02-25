/**
 * 
 */
package com.netfinworks.vfsso.cas.domain;

/**
 * <p>登录上下文</p>
 * @author huipeng
 * @version $Id: LoginContext.java, v 0.1 Jun 10, 2014 6:44:13 PM knico Exp $
 */
public class LoginContext<TSession extends ICasSession> extends CasContext {
    private String   loginName;
    private String   password;
    private String   dynamicCode;
    private TSession user;

    public LoginContext(String userName, String password) {
        this(userName, password, null);
    }

    public LoginContext(String loginName, String password, String dynamicCode) {
        this.loginName = loginName;
        this.password = password;
        this.dynamicCode = dynamicCode;
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
     * @return the dynamicCode
     */
    public String getDynamicCode() {
        return dynamicCode;
    }

    /**
     * @param dynamicCode the dynamicCode to set
     */
    public void setDynamicCode(String dynamicCode) {
        this.dynamicCode = dynamicCode;
    }

    /**
     * @return the user
     */
    public TSession getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(TSession user) {
        this.user = user;
    }
}
