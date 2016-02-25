/**
 * 
 */
package com.netfinworks.vfsso.auth.resource;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Map;

import javax.xml.bind.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.UrlParam;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.vfsso.auth.request.SessionExtResponse;
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
@WebResource(url = "/session/ext/{token}")
@Render(renderRef = "jsonRender")
public class SessionExtRes {
    private Logger                        logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IVfSsoSession<SsoUser>        userSessionService;

    @Verb(HttpVerb.GET)
    public SessionExtResponse doAuth(@UrlParam(name="token") String token) {
        long startTime = 0;
        SessionExtResponse ret = new SessionExtResponse();
        try {
            if (logger.isInfoEnabled()) {
                startTime = System.currentTimeMillis();
                logger.info("VfSso get session ext, token:{}", token);
            }
            Map<String, String> ext = userSessionService.getExt(token);
            ret.setData(ext);
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
            logger
                .info(
                    "VFSSO get session ext finished(cost{}ms),token:{}, result:{}",
                    new Object[] { System.currentTimeMillis() - startTime, token,
                            ret });
        }
        return ret;
    }
}
