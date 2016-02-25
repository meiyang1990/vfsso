/**
 * 
 */
package com.netfinworks.vfsso.ext.author;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.vfsso.cas.domain.ICasSession;
import com.netfinworks.vfsso.cas.domain.LoginContext;
import com.netfinworks.vfsso.cas.enums.CasFailTypeKind;
import com.netfinworks.vfsso.cas.exception.LoginAuthException;
import com.netfinworks.vfsso.cas.spec.ILoginAuth;
import com.netfinworks.vfsso.domain.SsoUser;

/**
 * @author knico
 * @since Jan 28, 2013
 * 
 */
public abstract class LoginAuthBase implements ILoginAuth<SsoUser> {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String domain;

    /**
     * @return the logger
     */
    protected Logger getLogger() {
        return logger;
    }

    /**
     * @return the domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @param domain the domain to set
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAuthName(String user) {
        int idx = user.indexOf("@");
        if (idx >= 0) {
            user = user.substring(0, idx);
        }
        if (getDomain() != null) {
            user = user + getDomain();
        }
        return user;
    }

    public String getAuthName(LoginContext<? extends ICasSession> ctx) {
        return getAuthName(ctx.getLoginName());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.netfinworks.guardian.unilogin.auth.ILoginAuth#auth(com.netfinworks.guardian.unilogin.auth.LoginContext
     * )
     */
    @Override
    public final boolean auth(LoginContext<SsoUser> ctx) throws LoginAuthException {
        boolean ret = false;
        if (ctx != null) {
            SsoUser user = doAuth(this.getAuthName(ctx), ctx.getPassword(), ctx.getDynamicCode());
            ctx.setUser(user);
            ret = user != null;
        } else {
            throw new LoginAuthException(CasFailTypeKind.failed, "上下文参数异常");
        }
        return ret;
    }

    /**
     * @param authName
     * @param password
     * @param dynamicCode
     * @return
     */
    protected abstract SsoUser doAuth(String authName, String password, String dynamicCode)
                                                                                           throws LoginAuthException;
}
