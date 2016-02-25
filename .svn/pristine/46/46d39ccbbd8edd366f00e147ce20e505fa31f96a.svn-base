/**
 * 
 */
package com.netfinworks.vfsso.cas.spec;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.vfsso.cas.domain.CasContext;
import com.netfinworks.vfsso.cas.domain.ChangePwdContext;
import com.netfinworks.vfsso.cas.domain.ICasSession;
import com.netfinworks.vfsso.cas.domain.LoginContext;
import com.netfinworks.vfsso.cas.exception.ChangePasswordException;
import com.netfinworks.vfsso.cas.exception.LoginAuthException;

/**
 * @author knico
 * @since Dec 24, 2012
 * 
 */
public class LoginAuthHolder<TUser extends ICasSession> implements ILoginAuth<TUser> {
    private Logger                  logger = LoggerFactory.getLogger(getClass());
    private IAuthorIdentity         authorIdentity;
    private Map<String, ILoginAuth<TUser>> loginAuths;
    private String                  defaultLoginAuthRef;

    /**
     * @return the authorIdentity
     */
    public IAuthorIdentity getAuthorIdentity() {
        return authorIdentity;
    }

    /**
     * @return the logger
     */
    protected Logger getLogger() {
        return logger;
    }

    /**
     * @param authorIdentity the authorIdentity to set
     */
    public void setAuthorIdentity(IAuthorIdentity authorIdentity) {
        this.authorIdentity = authorIdentity;
    }

    /**
     * @return the loginAuths
     */
    public Map<String, ILoginAuth<TUser>> getLoginAuths() {
        return loginAuths;
    }

    /**
     * @param loginAuths the loginAuths to set
     */
    public void setLoginAuths(Map<String, ILoginAuth<TUser>> loginAuths) {
        this.loginAuths = loginAuths;
    }

    /**
     * @return the defaultLoginAuthRef
     */
    public String getDefaultLoginAuthRef() {
        return defaultLoginAuthRef;
    }

    /**
     * @param defaultLoginAuthRef the defaultLoginAuthRef to set
     */
    public void setDefaultLoginAuthRef(String defaultLoginAuthRef) {
        this.defaultLoginAuthRef = defaultLoginAuthRef;
    }

    public ILoginAuth<TUser> getLoginAuth(CasContext ctx) {
        String authWay = getAuthorWay(ctx);
        ILoginAuth<TUser> loginAuth = null;
        if (authWay != null) {
            loginAuth = loginAuths.get(authWay);
        }
        if (loginAuth == null) {
            loginAuth = loginAuths.get(defaultLoginAuthRef);
            ctx.setAuthWay(defaultLoginAuthRef);
        } else {
            ctx.setAuthWay(authWay);
        }
        return loginAuth;
    }

    /**
     * @param ctx
     * @return
     */
    private String getAuthorWay(CasContext ctx) {
        return authorIdentity != null ? authorIdentity.getLoginAuthWay(ctx) : null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.netfinworks.guardian.unilogin.auth.ILoginAuth#auth(com.netfinworks.guardian.unilogin.auth.LoginContext
     * )
     */
    @Override
    public boolean auth(LoginContext<TUser> ctx) throws LoginAuthException {
        return this.getLoginAuth(ctx).auth(ctx);
    }

    public String getLoginName(LoginContext<TUser> ctx) {
        String user = ctx.getLoginName();
        int idx = user.indexOf("@");
        if (idx >= 0) {
            user = user.substring(0, idx);
        }
        return user;
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.cas.spec.ILoginAuth#changePassword(com.netfinworks.vfsso.cas.domain.ChangePwdContext)
     */
    @Override
    public void changePassword(ChangePwdContext ctx) throws ChangePasswordException {
        getLoginAuth(ctx).changePassword(ctx);
    }
}
