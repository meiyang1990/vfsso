/**
 * 
 */
package com.netfinworks.vfsso.auth.resource;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;
import java.util.Map;

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
import com.netfinworks.vfsso.auth.request.AuthResponse;
import com.netfinworks.vfsso.cas.domain.AuthParam;
import com.netfinworks.vfsso.cas.domain.ResultKind;
import com.netfinworks.vfsso.cas.exception.AuthException;
import com.netfinworks.vfsso.cas.exception.AuthFailException;
import com.netfinworks.vfsso.cas.exception.AuthPassException;
import com.netfinworks.vfsso.cas.spec.IAuthenticator;
import com.netfinworks.vfsso.domain.SsoUser;
import com.netfinworks.vfsso.session.IVfSsoSession;
import com.netfinworks.vfsso.session.exceptions.SessionException;

/**
 * <p>鉴权</p>
 * @author huipeng
 * @version $Id: AuthRes.java, v 0.1 Jun 11, 2014 6:30:40 PM knico Exp $
 */
@Component
@WebResource(url = "/auth")
@Render(renderRef = "jsonRender")
public class AuthRes {
    private Logger                                     logger = LoggerFactory.getLogger(getClass());
    @Resource(name = "cachedSessionService")
    private IVfSsoSession<SsoUser>                     userSessionService;

    @Resource(name = "vfsso.cas.authMap")
    private Map<String, List<IAuthenticator<SsoUser>>> authDefs;

    @Verb(HttpVerb.POST)
    public AuthResponse doAuth(@Body AuthParam param) {
        long startTime = 0;
        AuthResponse ret = new AuthResponse();
        SsoUser user = null;
        try {
            if (logger.isInfoEnabled()) {
                startTime = System.currentTimeMillis();
                logger.info("VFSSO auth start auth:{}", param);
            }
            user = userSessionService.get(param.getToken());
            this.authByRole(user, param);
            if (user != null) {
                userSessionService.touch(param.getToken(), user);
            }
            ret.setResultKind(ResultKind.PASS);
        } catch (AuthPassException e) {
            if (user != null) {
                userSessionService.touch(param.getToken(), user);
            }
            ret.setResultKind(ResultKind.PASS);
        } catch (AuthException e) {
            e.fillResponse(ret);
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
        if (user != null) {
            ret.setUser(user);
        }
        if (logger.isInfoEnabled()) {
            logger
                .info(
                    "VFSSO-Auth finished(cost{}ms):[{} {}] token[{}] user[{}] result:{}",
                    new Object[] { System.currentTimeMillis() - startTime, param.getMethod(),
                            param.getUrl(), param.getToken(), user == null ? null : user.getName(),
                            ret });
        }
        return ret;
    }

    private void authByRole(SsoUser user, AuthParam param) throws AuthException {
        List<IAuthenticator<SsoUser>> authenticators = this.getAuthenticatorList(param.getApp());
        if (authenticators == null) {
            throw new AuthFailException("没有定义" + param.getApp() + "的鉴权过程");
        }
        if (param.getUrl() != null) {
            int pos = param.getUrl().indexOf("?");
            if (pos >= 0) {
                param.setUrl(param.getUrl().substring(0, pos));
            }
        } else {
            throw new AuthFailException("没有资源URL");
        }
        for (IAuthenticator<SsoUser> auth : authenticators) {
            auth.auth(user, param);
            logger.debug("Authenticor [{}] finish authenticate.", auth.getClass().getSimpleName());
        }
    }

    /**
     * @param style
     * @return
     */
    private List<IAuthenticator<SsoUser>> getAuthenticatorList(String appId) {
        if (this.authDefs == null) {
            throw new RuntimeException("鉴权未定义");
        }
        List<IAuthenticator<SsoUser>> ret = this.authDefs.get(appId);
        if (ret == null) {
            ret = this.authDefs.get("default");
        }
        return ret;
    }
}
