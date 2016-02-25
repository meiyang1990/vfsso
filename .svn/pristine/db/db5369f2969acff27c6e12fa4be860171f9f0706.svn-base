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
import com.netfinworks.vfsso.cas.domain.ChangePwdContext;
import com.netfinworks.vfsso.cas.exception.ChangePasswordException;
import com.netfinworks.vfsso.cas.spec.ILoginAuth;
import com.netfinworks.vfsso.domain.SsoUser;
import com.netfinworks.vfsso.session.IVfSsoSession;
import com.netfinworks.vfsso.session.enums.SessionStatusKind;
import com.netfinworks.vfsso.session.exceptions.SessionException;
import com.netfinworks.vfsso.ulogin.common.ResBase;
import com.netfinworks.vfsso.ulogin.common.ResourceResponse;
import com.netfinworks.vfsso.ulogin.common.ResultKind;

/**
 * <p>修改密码</p>
 * @author huipeng
 * @version $Id: ChangepassRes.java, v 0.1 Apr 29, 2014 5:11:42 PM knico Exp $
 */
@WebResource(url = "/changepass", matchKind = UrlMatchKind.Cautious)
@Component
public class ChangepassRes extends ResBase {
    @Resource(name = "vfsso.session.service")
    private IVfSsoSession<SsoUser> userSessionService;
    @Resource(name = "vfsso.login.auth")
    private ILoginAuth<SsoUser>             loginAuth;

    @Verb(HttpVerb.GET)
    @Render(renderRef = "blankVelocityRender")
    public ResourceResponse<Boolean> get() {
        return ResourceResponse.successful(true);
    }

    @Verb(HttpVerb.PUT)
    @Render(renderRef = "jsonRender")
    public ResourceResponse<Object> change(@CookieParam(name = VfSsoConstants.CAS_TOKEN_COOKIE_NAME) String token,
                                           @Body ChangePwdContext ctx) {
        ResourceResponse<Object> ret = null;
        SsoUser u;
        try {
            u = userSessionService.get(token);
        } catch (SessionException e) {
            return ResourceResponse.newInstance(ResultKind.EXCEPTION.getCode(), "会话服务异常，请重试");
        }
        if (u != null) {
            if (SessionStatusKind.login.equals(u.getSessionStatus())) {
                try {
                    ctx.setUser(u);
                    loginAuth.changePassword(ctx);
                    ret = ResourceResponse.successful();
                } catch (ChangePasswordException e) {
                    ret = ResourceResponse.newInstance(ResultKind.EXCEPTION.getCode(), "变更密码失败");
                }
            } else {

                ret = ResourceResponse.newInstance(ResultKind.FAILED.getCode(), "登录状态异常");
            }
        } else {
            ret = ResourceResponse.newInstance(ResultKind.FAILED.getCode(), "未登录");
        }
        return ret;
    }
}
