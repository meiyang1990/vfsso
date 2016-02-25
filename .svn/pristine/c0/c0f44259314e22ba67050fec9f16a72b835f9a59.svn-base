/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.ulogin.resource;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.netfinworks.rest.annotation.Body;
import com.netfinworks.rest.annotation.CookieParam;
import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.rest.util.UrlMatchKind;
import com.netfinworks.vfsso.cas.VfSsoConstants;
import com.netfinworks.vfsso.cas.spec.ICaptchaProvider;
import com.netfinworks.vfsso.cas.spec.ILoginAuth;
import com.netfinworks.vfsso.domain.SsoUser;
import com.netfinworks.vfsso.session.IVfSsoSession;
import com.netfinworks.vfsso.session.enums.SessionStatusKind;
import com.netfinworks.vfsso.session.exceptions.SessionException;
import com.netfinworks.vfsso.ulogin.common.ResBase;
import com.netfinworks.vfsso.ulogin.common.ResourceResponse;
import com.netfinworks.vfsso.ulogin.common.ResultKind;
import com.netfinworks.vfsso.ulogin.request.ForceLoginReq;

/**
 * <p>强制登录</p>
 * @author huipeng
 * @version $Id: ForceLoginRes.java, v 0.1 Jun 10, 2014 10:42:55 AM knico Exp $
 */
@WebResource(url = "/login/force", matchKind = UrlMatchKind.Cautious)
@Component
public class ForceLoginRes extends ResBase {
    @Resource(name = "vfsso.login.auth")
    private ILoginAuth<SsoUser>    loginAuth;

    @Resource(name = "vfsso.login.captcha")
    private ICaptchaProvider       captchaProvider;

    @Resource(name = "vfsso.session.service")
    private IVfSsoSession<SsoUser> userSessionService;

    @Verb(HttpVerb.PUT)
    @Render(renderRef = "jsonRender")
    public ResourceResponse<Object> change(@CookieParam(name = VfSsoConstants.CAS_TOKEN_COOKIE_NAME) String token,
                                           @Body ForceLoginReq req) {
        ResourceResponse<Object> ret = null;
        SsoUser u;
        try {
            u = userSessionService.get(token);
        } catch (SessionException e) {
            return ResourceResponse.newInstance(ResultKind.EXCEPTION.getCode(), "会话服务异常，请重试");
        }
        if (u != null) {
            if (SessionStatusKind.pending.equals(u.getSessionStatus())) {
                boolean canlogin = true;
                if (captchaProvider != null) {
                    canlogin = captchaProvider
                        .validateCaptcha(req.getRequestId(), req.getCaptcha());
                }
                if (canlogin) {
                    try {
                        if (userSessionService.forceIn(token, u)) {
                            ret = ResourceResponse.successful();
                        } else {
                            ret = ResourceResponse.newInstance(ResultKind.FAILED.getCode(), "登录失败");
                        }
                    } catch (SessionException e) {
                        ret = ResourceResponse.newInstance(ResultKind.EXCEPTION.getCode(), "会话服务异常，请重试");
                    }
                } else {
                    ret = ResourceResponse.newInstance(ResultKind.FAILED.getCode(), "验证码校验失败");
                }
            } else if (SessionStatusKind.login.equals(u.getSessionStatus())) {
                getLogger().info("User({}) aready login by token:{}", u.getLoginName(), token);
                ret = ResourceResponse.successful();
            } else {
                ret = ResourceResponse.newInstance(ResultKind.FAILED.getCode(), "登录状态异常");
            }
        } else {
            ret = ResourceResponse.newInstance(ResultKind.FAILED.getCode(), "未登录");
        }
        return ret;
    }
}
