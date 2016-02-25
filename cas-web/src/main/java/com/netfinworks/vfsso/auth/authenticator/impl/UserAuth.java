/**
 * 
 */
package com.netfinworks.vfsso.auth.authenticator.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.vfsso.cas.domain.AuthParam;
import com.netfinworks.vfsso.cas.domain.ResultKind;
import com.netfinworks.vfsso.cas.exception.AuthException;
import com.netfinworks.vfsso.cas.exception.AuthFailException;
import com.netfinworks.vfsso.cas.spec.IAuthenticator;
import com.netfinworks.vfsso.domain.SsoUser;

/**
 * @author knico
 * @since Oct 24, 2012
 * 
 */
public class UserAuth implements IAuthenticator<SsoUser> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void auth(SsoUser user, AuthParam param) throws AuthException {
        if (user == null) {
            throw new AuthFailException(ResultKind.EXPIRED, null);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("VFSSO-Auth found user [{},{}] by token [{}].",
                new Object[] { user.getLoginName(), user.getSessionStatus(), param.getToken() });
        }
        switch (user.getSessionStatus()) {
            case login:
                break;
            case pending:
                throw new AuthFailException(ResultKind.PENDING, "该帐号正在其他地方登录，请认证登录或退出");
            case kicked:
                throw new AuthFailException(ResultKind.KICKED, "您已被踢出");
            default:
                throw new AuthFailException(ResultKind.EXPIRED, null);
        }
    }
}
