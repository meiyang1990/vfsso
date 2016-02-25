/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.session.cache;

import java.util.Map;

import com.netfinworks.basis.inf.ucs.client.CacheRespone;
import com.netfinworks.basis.inf.ucs.memcached.XUCache;
import com.netfinworks.vfsso.domain.SsoUser;
import com.netfinworks.vfsso.session.core.SessionServiceBase;
import com.netfinworks.vfsso.session.exceptions.SessionException;

/**
 * <p>Session的统一缓存实现</p>
 * @author huipeng
 * @version $Id: CachedSessionService.java, v 0.1 Jun 5, 2014 2:18:37 PM knico Exp $
 */
public class CachedSessionService extends SessionServiceBase<SsoUser> {
    public static final String NAMESPACE_SESSION_TOKEN = "SessionToken";
    public static final String NAMESPACE_SESSION_EXT   = "SessionExt";
    public static final String NAMESPACE_MUTEX_INFO    = "MutexInfo";

    private XUCache<Object>    sessionCache;
    private int                mutexExpired            = 300;           //默认5min

    private int                sessionExpired          = 1800;          //默认30min
    private int                extExpired              = 3600;          //默认1hour

    /**
     * @return the sessionCache
     */
    public XUCache<Object> getSessionCache() {
        return sessionCache;
    }

    /**
     * @param sessionCache the sessionCache to set
     */
    public void setSessionCache(XUCache<Object> sessionCache) {
        this.sessionCache = sessionCache;
    }

    /**
     * @return the mutexExpired
     */
    public int getMutexExpired() {
        return mutexExpired;
    }

    /**
     * @param mutexExpired the mutexExpired to set
     */
    public void setMutexExpired(int mutexExpired) {
        this.mutexExpired = mutexExpired;
    }

    /**
     * @return the sessionExpired
     */
    public int getSessionExpired() {
        return sessionExpired;
    }

    /**
     * @param sessionExpired the sessionExpired to set
     */
    public void setSessionExpired(int sessionExpired) {
        this.sessionExpired = sessionExpired;
    }

    /**
     * @return the extExpired
     */
    public int getExtExpired() {
        return extExpired;
    }

    /**
     * @param extExpired the extExpired to set
     */
    public void setExtExpired(int extExpired) {
        this.extExpired = extExpired;
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.session.core.SessionServiceBase#getMutexId(java.lang.Object)
     */
    @Override
    protected String getMutexId(SsoUser session) {
        return session.getLoginName();
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.session.core.SessionServiceBase#addMutexInfo(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    protected boolean addMutexInfo(String appCode, String mutexId, String token) {
        return sessionCache.add(genMutexInfoKey(appCode, mutexId), token, mutexExpired);
    }

    /**
     * @param appCode
     * @param mutexId
     * @return
     */
    public String genMutexInfoKey(String appCode, String mutexId) {
        return NAMESPACE_MUTEX_INFO + appCode + mutexId;
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.session.core.SessionServiceBase#getMutexInfo(java.lang.String, java.lang.String)
     */
    @Override
    protected String getMutexInfo(String appCode, String mutexId) {
        //TODO 异常情况
        return (String) sessionCache.get(genMutexInfoKey(appCode, mutexId)).get();
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.session.core.SessionServiceBase#addToken(java.lang.String, java.lang.String, java.lang.Object)
     */
    @Override
    protected boolean addToken(String appCode, String token, SsoUser session) {
        return sessionCache.add(genSessionKey(appCode, token), session, sessionExpired);
    }

    /**
     * @param appCode
     * @param token
     * @return
     */
    private String genSessionKey(String appCode, String token) {
        return NAMESPACE_SESSION_TOKEN + appCode + token;
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.session.core.SessionServiceBase#removeMutexInfo(java.lang.String, java.lang.String)
     */
    @Override
    protected boolean removeMutexInfo(String appCode, String mutexId) {
        return sessionCache.delete(genMutexInfoKey(appCode, mutexId));
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.session.core.SessionServiceBase#removeSession(java.lang.String, java.lang.String)
     */
    @Override
    protected boolean removeSession(String appCode, String token) {
        return sessionCache.delete(genSessionKey(appCode, token));
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.session.core.SessionServiceBase#getSession(java.lang.String, java.lang.String)
     */
    @Override
    protected SsoUser getSession(String appCode, String token) throws SessionException {
        // TODO 异常
        CacheRespone<Object> cresp = sessionCache.get(genSessionKey(appCode, token));
        if (!cresp.isSuccess()) {
            throw new SessionException();
        }
        return (SsoUser) cresp.get();
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.session.core.SessionServiceBase#touchMutexInfo(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    protected boolean touchMutexInfo(String appCode, String mutexId, String token) {
        return sessionCache.replace(genMutexInfoKey(appCode, mutexId), token, mutexExpired);
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.session.core.SessionServiceBase#touchSession(java.lang.String, java.lang.String, java.lang.Object)
     */
    @Override
    protected boolean touchSession(String appCode, String token, SsoUser session) {
        return sessionCache.replace(genSessionKey(appCode, token), session, sessionExpired);
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.session.core.SessionServiceBase#getSessionExt(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected Map<String, String> getSessionExt(String appCode, String token) {
        //TODO 异常
        return (Map<String, String>) sessionCache.get(genSessionExtKey(appCode, token)).get();
    }

    /**
     * @param appCode
     * @param token
     * @return
     */
    private String genSessionExtKey(String appCode, String token) {
        return NAMESPACE_SESSION_EXT + appCode + token;
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.session.core.SessionServiceBase#setSessionExt(java.lang.String, java.lang.String, java.util.Map)
     */
    @Override
    protected boolean setSessionExt(String appCode, String token, Map<String, String> ext) {
        return sessionCache.set(genSessionExtKey(appCode, token), ext, extExpired);
    }
}
