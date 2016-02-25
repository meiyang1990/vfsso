/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.auth.authenticator.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.authorize.ws.clientservice.IOperatorService;
import com.netfinworks.authorize.ws.request.impl.CheckFunctionFromOperatorRequest;
import com.netfinworks.authorize.ws.response.impl.CheckFunctionFromOperatorResponse;
import com.netfinworks.vfsso.cas.domain.AuthParam;
import com.netfinworks.vfsso.cas.domain.ResultKind;
import com.netfinworks.vfsso.cas.enums.CasAuthModeKind;
import com.netfinworks.vfsso.cas.enums.CasUrlStyleKind;
import com.netfinworks.vfsso.cas.exception.AuthException;
import com.netfinworks.vfsso.cas.spec.IAuthenticator;
import com.netfinworks.vfsso.domain.SsoUser;

/**
 * <p>MA-Authorize操作员鉴权</p>
 * @author huipeng
 * @version $Id: MaAuthAuth.java, v 0.1 Jun 25, 2014 12:19:46 PM knico Exp $
 */
public class MaAuthAuth implements IAuthenticator<SsoUser> {
    private Logger                logger = LoggerFactory.getLogger(getClass());
    private CasAuthModeKind       mode   = CasAuthModeKind.DENIED_EXT;

    @Resource(name = "authOperatorClient")
    private IOperatorService      authOperatorClient;
    @Resource(name = "authcoreOperatorClient")
    private IOperatorService      authcoreOperatorClient;
    @Resource(name = "authorizeHeaderGener")
    private MaAuthHeaderGener     headerGener;

    private MaAuthorizeFuncHolder funcHolder;

    /**
     * @return the mode
     */
    public CasAuthModeKind getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(CasAuthModeKind mode) {
        this.mode = mode;
    }

    /**
     * @return the funcHolder
     */
    public MaAuthorizeFuncHolder getFuncHolder() {
        return funcHolder;
    }

    /**
     * @param funcHolder the funcHolder to set
     */
    public void setFuncHolder(MaAuthorizeFuncHolder funcHolder) {
        this.funcHolder = funcHolder;
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.cas.spec.IAuthenticator#auth(com.netfinworks.vfsso.cas.domain.ICasSession, com.netfinworks.vfsso.cas.domain.AuthParam)
     */
    @Override
    public void auth(SsoUser user, AuthParam param) throws AuthException {
        if (user.getOpId() == null || user.getOpId().length() == 0) {
            throw new AuthException(ResultKind.DENIED, "不是企业操作员");
        }
        String url = param.getUrl();
        if (CasUrlStyleKind.rpc.equals(param.getStyle()) && param.getMethod() != null
            && param.getMethod().length() > 0) {
            url = param.getUrl() + "?" + param.getMethodName() + "=" + param.getMethod();
        }

        String[] funcId = funcHolder.getFuncIdByUrl(url);
        if (funcId == null || funcId.length == 0) {
            switch (mode) {
                case PASS_EXT:
                    if (logger.isDebugEnabled()) {
                        logger.debug("'{}:{}'不在管理列表中，放行", param.getApp(), param.getUrl());
                    }
                    return;
                default:
                    if (logger.isDebugEnabled()) {
                        logger.debug("'{}:{}'不在管理列表中，禁止访问", param.getApp(), param.getUrl());
                    }
                    throw new AuthException(ResultKind.DENIED, "url不在管理列表中");
            }
        }
        if (!authFunc(user, funcId)) {
            throw new AuthException(ResultKind.DENIED, "没有权限");
        }
    }

    public boolean authFunc(SsoUser user, String[] funcIds) throws AuthException {
        try {
            long beginTime = System.currentTimeMillis();
            if (logger.isInfoEnabled()) {
                logger.info("验证会员({},{})权限：{}", new Object[] { user.getId(), user.getOpId(),
                        funcIds });
            }
            boolean ret = false;
            //一次通过就OK
            for (String funcId : funcIds) {
                CheckFunctionFromOperatorRequest request = createAuthReq(user, funcId);
                CheckFunctionFromOperatorResponse response = authcoreOperatorClient
                    .checkFunctionFromOperator(request);
                ret = response.isAuthorized();
                long consumeTime = System.currentTimeMillis() - beginTime;
                if (logger.isInfoEnabled()) {
                    logger.info("验证耗时:{} (ms); 响应结果:{} ", new Object[] { consumeTime, response });
                }
                if (ret) {
                    break;
                }
            }
            return ret;
        } catch (Exception e) {
            logger.error("操作员({})远程鉴权异常:{}", user.getOpId(), e.getMessage(), e);
            throw new AuthException(ResultKind.EXCEPTION,"MA鉴权调用异常："+e.getMessage());
        }
    }

    /**
     * @param user
     * @param funcId
     * @return
     */
    private CheckFunctionFromOperatorRequest createAuthReq(SsoUser user, String funcId) {
        CheckFunctionFromOperatorRequest ret = new CheckFunctionFromOperatorRequest();
        ret.setMemberId(user.getId());
        ret.setOperatorId(user.getOpId());
        ret.setFunctionId(funcId);
        ret.setHeader(headerGener.generate(funcHolder.getAppId(), "CHECK"));
        return ret;
    }
}
