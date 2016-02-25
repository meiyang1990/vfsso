/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.auth.authenticator.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.authorize.ws.clientservice.IFunctionService;
import com.netfinworks.authorize.ws.request.impl.GetAllGrantedAuthorityRequest;
import com.netfinworks.authorize.ws.response.impl.GetAllGrantedAuthorityResponse;

/**
 * <p>Ma鉴权系统权限持有者</p>
 * @author huipeng
 * @version $Id: MaAuthorizeFuncHolder.java, v 0.1 Jun 25, 2014 5:31:50 PM knico Exp $
 */
public class MaAuthorizeFuncHolder {
    private Logger                logger = LoggerFactory.getLogger(getClass());
    private String                appId;
    private long                  invalidateMiliTime;
    private Map<String, String[]> funcMap;
    private long                  lastUpdate;
    @Resource(name = "authcoreFunctionClient")
    private IFunctionService      authcoreFunctionClient;
    @Resource(name = "authorizeHeaderGener")
    private MaAuthHeaderGener     headerGener;

    /**
     * @return the appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * @param appId the appId to set
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * @return the invalidateMiliTime
     */
    public long getInvalidateMiliTime() {
        return invalidateMiliTime;
    }

    /**
     * @param invalidateMiliTime the invalidateMiliTime to set
     */
    public void setInvalidateMiliTime(long invalidateMiliTime) {
        this.invalidateMiliTime = invalidateMiliTime;
    }

    /**
     * @param url
     * @return 
     */
    public String[] getFuncIdByUrl(String url) {
        if (funcMap == null) {
            synchronized (this) {
                if (funcMap == null) {
                    doRetrieveFuncMap();
                }
            }
            if (funcMap == null) {
                throw new RuntimeException("MA Authorize访问异常。");
            }
        } else {
            checkForUpdate();
        }
        return funcMap.get(url);
    }

    /**
     * 
     */
    private void checkForUpdate() {
        long now = System.currentTimeMillis();
        long pass = now - lastUpdate;
        if (pass > invalidateMiliTime) {
            synchronized (this) {
                pass = now - lastUpdate;
                if (pass > invalidateMiliTime) {
                    lastUpdate = now;
                    if (logger.isInfoEnabled()) {
                        logger.info("Func map data too old({} s), start update...", pass / 1000f);
                    }
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                doRetrieveFuncMap();
                            } catch (Exception e) {
                                setUpdateTimeForFail();
                            }
                        }
                    }).start();
                }
            }
        }
    }

    /**
     * 
     */
    protected void setUpdateTimeForFail() {
        // retry after 10s when another visit
        lastUpdate = System.currentTimeMillis() - invalidateMiliTime + 10000;
    }

    /**
     * 
     */
    private void doRetrieveFuncMap() {
        try {
            long beginTime = System.currentTimeMillis();
            if (logger.isInfoEnabled()) {
                logger.info("获取系统授权列表：{}", appId);
            }
            GetAllGrantedAuthorityRequest request = createAllGrantedReq();
            GetAllGrantedAuthorityResponse response = authcoreFunctionClient
                .getAllGrantedAuthority(request);
            long consumeTime = System.currentTimeMillis() - beginTime;
            if (logger.isInfoEnabled()) {
                logger.info("验证耗时:{} (ms); 响应结果:{} ", new Object[] { consumeTime, response });
            }
            if ("0".endsWith(response.getResponse().getResponseCode())) {
                funcMap = response.getAuthorities();
                if (funcMap == null) {
                    funcMap = new HashMap<String, String[]>();
                }
            }
        } catch (Exception e) {
            logger.error("获取系统授权列表：{}", appId, e);
            throw new RuntimeException(e.getMessage());
        }
        lastUpdate = System.currentTimeMillis();
    }

    /**
     * @return
     */
    private GetAllGrantedAuthorityRequest createAllGrantedReq() {
        GetAllGrantedAuthorityRequest ret = new GetAllGrantedAuthorityRequest();
        ret.setHeader(headerGener.generate(getAppId(), "ALL_GRANTED"));
        return ret;
    }

}
