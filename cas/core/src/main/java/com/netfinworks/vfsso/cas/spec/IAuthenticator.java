/**
 * 
 */
package com.netfinworks.vfsso.cas.spec;

import com.netfinworks.vfsso.cas.domain.AuthParam;
import com.netfinworks.vfsso.cas.domain.ICasSession;
import com.netfinworks.vfsso.cas.exception.AuthException;

/**
 * @author knico
 * @since Oct 24, 2012
 *
 */
public interface IAuthenticator<TUser extends ICasSession> {

    public abstract void auth(TUser user, AuthParam param) throws AuthException;

}
