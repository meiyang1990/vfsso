/**
 * 
 */
package com.netfinworks.vfsso.ulogin.resource;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.netfinworks.rest.annotation.CookieParam;
import com.netfinworks.rest.annotation.QueryParam;
import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.vfsso.cas.VfSsoConstants;
import com.netfinworks.vfsso.domain.SsoUser;
import com.netfinworks.vfsso.session.IVfSsoSession;
import com.netfinworks.vfsso.session.exceptions.SessionException;
import com.netfinworks.vfsso.ulogin.common.ResBase;
import com.netfinworks.vfsso.ulogin.common.VfSsoCookie;

/**
 * <p>登出</p>
 * @author huipeng
 * @version $Id: LogoutRes.java, v 0.1 Jun 10, 2014 3:59:11 PM knico Exp $
 */
@Component
@WebResource(url = "/logout")
@Render(renderRef = "loginVelocityRender")
public class LogoutRes extends ResBase {
    @Resource(name = "vfsso.session.service")
    private IVfSsoSession<SsoUser> userSessionService;

    @Verb(HttpVerb.GET)
    public void logout(@QueryParam(name = "token") String token,
                       @CookieParam(name = VfSsoConstants.CAS_TOKEN_COOKIE_NAME) String tokenInCookie) {
        if (tokenInCookie != null && tokenInCookie.length() > 0) {
            try {
                userSessionService.remove(tokenInCookie);
                VfSsoCookie.removeCookie();
            } catch (SessionException e) {
                //TODO 没有注销的情况暂不考虑
            }
        }
        if (token != null && token.length() > 0 && !token.equals(tokenInCookie)) {
            try {
                userSessionService.remove(token);
            } catch (SessionException e) {
            }
        }
        //TODO 记录登出日志
    }
}
