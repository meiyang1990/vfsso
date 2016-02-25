/**
 * 
 */
package com.netfinworks.vfsso.auth.resource;

import java.lang.reflect.UndeclaredThrowableException;

import javax.annotation.Resource;
import javax.xml.bind.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.UrlParam;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.vfsso.auth.request.CommonResourceResponse;
import com.netfinworks.vfsso.cas.domain.ResultKind;
import com.netfinworks.vfsso.domain.SsoUser;
import com.netfinworks.vfsso.session.IVfSsoSession;
import com.netfinworks.vfsso.session.exceptions.SessionException;

/**
 * <p>获取扩展信息</p>
 * @author huipeng
 * @version $Id: SessionExtRes.java, v 0.1 Jun 11, 2014 6:30:40 PM knico Exp $
 */
@Component
@WebResource(url = "/session/{token}")
@Render(renderRef = "jsonRender")
public class SessionRes {
    private Logger                 logger = LoggerFactory.getLogger(getClass());
    @Resource(name = "cachedSessionService")
    private IVfSsoSession<SsoUser> userSessionService;

    @Verb(HttpVerb.GET)
    public CommonResourceResponse<SsoUser> get(@UrlParam(name = "token") String token) {
        long startTime = 0;
        CommonResourceResponse<SsoUser> ret = new CommonResourceResponse<SsoUser>();
        try {
            if (logger.isInfoEnabled()) {
                startTime = System.currentTimeMillis();
                logger.info("VfSso get session, token:{}", token);
            }
            SsoUser user = userSessionService.get(token);
            if (logger.isDebugEnabled()) {
                logger.debug("found session:{}", user);
            }
            ret.setData(user);
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
            logger.info("VFSSO get session finished(cost{}ms),token:{}, result:{}", new Object[] {
                    System.currentTimeMillis() - startTime, token, ret });
        }
        return ret;
    }

    @Verb(HttpVerb.PUT)
    public CommonResourceResponse<Boolean> forcein(@UrlParam(name = "token") String token) {
        long startTime = 0;
        CommonResourceResponse<Boolean> ret = new CommonResourceResponse<Boolean>();
        try {
            if (logger.isInfoEnabled()) {
                startTime = System.currentTimeMillis();
                logger.info("VfSso forcein, token:{}", token);
            }
            SsoUser session = userSessionService.get(token);
            switch (session.getSessionStatus()) {
                case pending:
                    ret.setData(userSessionService.forceIn(token, session));
                    break;
                case login:
                    ret.setData(true);
                    break;
                default:
                    ret.setData(false);
                    ret.setMsg("登录状态异常：" + session.getSessionStatus());
                    break;
            }
            ret.setResultKind(ResultKind.SUCCESS);
        } catch (SessionException e) {
            ret.setResultKind(ResultKind.SESSION_EXCP);
            ret.setMsg("会话服务异常，请重试");
        } catch (Exception e) {
            ret.setResultException(e);
        }
        if (logger.isInfoEnabled()) {
            logger.info("VFSSO logout finished(cost{}ms),token:{}, result:{}", new Object[] {
                    System.currentTimeMillis() - startTime, token, ret });
        }
        return ret;
    }

    @Verb(HttpVerb.DELETE)
    public CommonResourceResponse<Boolean> logout(@UrlParam(name = "token") String token) {
        long startTime = 0;
        CommonResourceResponse<Boolean> ret = new CommonResourceResponse<Boolean>();
        try {
            if (logger.isInfoEnabled()) {
                startTime = System.currentTimeMillis();
                logger.info("VfSso logout, token:{}", token);
            }
            ret.setData(userSessionService.remove(token));
            ret.setResultKind(ResultKind.SUCCESS);
        } catch (SessionException e) {
            ret.setResultKind(ResultKind.SESSION_EXCP);
            ret.setMsg("会话服务异常，请重试");
        } catch (Exception e) {
            ret.setResultException(e);
        }
        if (logger.isInfoEnabled()) {
            logger.info("VFSSO logout finished(cost{}ms),token:{}, result:{}", new Object[] {
                    System.currentTimeMillis() - startTime, token, ret });
        }
        return ret;
    }
}
