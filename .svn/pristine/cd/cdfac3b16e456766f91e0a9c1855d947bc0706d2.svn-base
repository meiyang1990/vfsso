/**
 * 
 */
package com.netfinworks.vfsso.cas.spec;

import com.netfinworks.vfsso.cas.domain.ChangePwdContext;
import com.netfinworks.vfsso.cas.domain.ICasSession;
import com.netfinworks.vfsso.cas.domain.LoginContext;
import com.netfinworks.vfsso.cas.exception.ChangePasswordException;
import com.netfinworks.vfsso.cas.exception.LoginAuthException;

/**
 * @author knico
 * @since Nov 30, 2012
 * 
 */
public interface ILoginAuth<TUser extends ICasSession> {
    /**
     * 登录验证
     * 
     * @param ctx
     * @return
     * @throws LoginAuthException
     */
    boolean auth(LoginContext<TUser> ctx) throws LoginAuthException;

    /**
     * 修改密码
     * 
     * @param user
     * @param password
     * @param newPassword
     */
    void changePassword(ChangePwdContext ctx) throws ChangePasswordException;
}
