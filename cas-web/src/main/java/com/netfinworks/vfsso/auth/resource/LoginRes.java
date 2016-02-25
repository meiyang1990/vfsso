/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.auth.resource;

import java.lang.reflect.UndeclaredThrowableException;

import javax.annotation.Resource;
import javax.xml.bind.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netfinworks.rest.annotation.Body;
import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.vfsso.auth.request.CommonResourceResponse;
import com.netfinworks.vfsso.auth.request.LoginResponse;
import com.netfinworks.vfsso.cas.domain.ResultKind;
import com.netfinworks.vfsso.domain.SsoUser;
import com.netfinworks.vfsso.session.IVfSsoSession;
import com.netfinworks.vfsso.session.exceptions.SessionException;

/**
 * <p>注释</p>
 * @author huipeng
 * @version $Id: LoginRes.java, v 0.1 Jun 26, 2014 6:20:40 PM knico Exp $
 */
@Component
@WebResource(url = "/login")
@Render(renderRef = "jsonRender")
public class LoginRes {
    private Logger                 logger = LoggerFactory.getLogger(getClass());
    @Resource(name = "cachedSessionService")
    private IVfSsoSession<SsoUser> userSessionService;

    @Verb(HttpVerb.POST)
    public CommonResourceResponse<LoginResponse> get(@Body SsoUser user) {
        long startTime = 0;
        CommonResourceResponse<LoginResponse> ret = new CommonResourceResponse<LoginResponse>();
        try {
            if (logger.isInfoEnabled()) {
                startTime = System.currentTimeMillis();
                logger.info("VfSso want login, user:{}", user);
            }
            String token = userSessionService.create(user);
            LoginResponse resp = new LoginResponse();
            resp.setToken(token);
            resp.setStatus(user.getSessionStatus());
            ret.setData(resp);
            ret.setResultKind(ResultKind.SUCCESS);
        } catch (SessionException e) {
            ret.setResultKind(ResultKind.SESSION_EXCP);
            ret.setMsg("会话服务异常，请重试");
        } catch (Exception e) {
            Throwable thr = e;
            if (e instanceof UndeclaredThrowableException || e.getMessage() == null
                && e.getCause() != null) {
                thr = e.getCause();
            }
            if (thr instanceof ValidationException) {
                logger.error("Fail in authenticating with wrong input {}", thr.getMessage());
            } else {
                logger.error("Fail in authenticating {}", e);
            }
            ret.setResultException(thr);
        }
        if (logger.isInfoEnabled()) {
            logger.info("VFSSO login finished.(cost{}ms), result:{}",
                new Object[] { System.currentTimeMillis() - startTime, ret });
        }
        return ret;
    }
}
