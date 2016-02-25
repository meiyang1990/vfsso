/**
 * Copyright 2013 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.ulogin.resource;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.netfinworks.rest.annotation.CookieParam;
import com.netfinworks.rest.annotation.QueryParam;
import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.rest.util.UrlMatchKind;
import com.netfinworks.vfsso.cas.VfSsoConstants;
import com.netfinworks.vfsso.domain.SsoUser;
import com.netfinworks.vfsso.session.IVfSsoSession;
import com.netfinworks.vfsso.session.exceptions.SessionException;
import com.netfinworks.vfsso.ulogin.common.ResBase;

/**
 * <p>ajax自动登录</p>
 * @author huipeng
 * @version $Id: AuthJsonPRes.java, v 0.1 Dec 10, 2013 2:52:51 PM knico Exp $
 */
@WebResource(url = "/relogin.js", matchKind = UrlMatchKind.Cautious)
@Component
public class AuthJsonPRes extends ResBase {
    @Resource(name = "vfsso.session.service")
    private IVfSsoSession<SsoUser> userSessionService;

    @Verb(HttpVerb.GET)
    @Render(renderRef = "txtRender")
    public String init(@CookieParam(name = VfSsoConstants.CAS_TOKEN_COOKIE_NAME) String token,
                       @QueryParam(name = "forwordUrl") String forwordUrl,
                       @QueryParam(name = "callback") String callback) {
        String ret = null;
        if (StringUtils.hasText(token)) {
            SsoUser usr;
            try {
                usr = userSessionService.get(token);
            } catch (SessionException e) {
                return buildRetryJs(callback, token, forwordUrl);
            }
            if (usr != null) {
                if (StringUtils.hasText(callback)) {
                    ret = buildCallbackJs(callback, token, forwordUrl);
                }
            }
        }
        if (ret == null) {
            ret = buildLoginForwork(forwordUrl);
        }
        return ret;
    }

    /**
     * 重试
     * @param callback
     * @param token
     * @param forwordUrl
     * @return
     */
    private String buildRetryJs(String callback, String token, String forwordUrl) {
        return callback + "({token:\"" + token + "\", logined:true})";
    }

    private String buildCallbackJs(String callback, String token, String forwordUrl) {
        return callback + "({token:\"" + token + "\", logined:true})";
    }

    private String buildLoginForwork(String forwordUrl) {
        return "";
    }
}
