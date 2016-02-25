/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.session.core;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.vfsso.common.util.TokenUtil;
import com.netfinworks.vfsso.session.IVfSsoSession;
import com.netfinworks.vfsso.session.enums.SessionStatusKind;
import com.netfinworks.vfsso.session.exceptions.SessionException;
import com.netfinworks.vfsso.session.spec.ISession;
import com.netfinworks.vfsso.session.spec.VfSessionConfig;

/**
 * <p>Session基础类</p>
 * @author huipeng
 * @version $Id: SessionServiceBase.java, v 0.1 Jun 5, 2014 10:42:43 AM knico Exp $
 */
public abstract class SessionServiceBase<TSession extends ISession> implements
                                                                    IVfSsoSession<TSession> {
    private Logger  logger = LoggerFactory.getLogger(getClass());
    private String  appCode;
    private boolean loginMutex;

    /**
     * @return the logger
     */
    protected Logger getLogger() {
        return logger;
    }

    /**
     * @return the appCode
     */
    public String getAppCode() {
        String specApp = VfSessionConfig.getSysCode();
        if (specApp != null && specApp.trim().length() > 0) {
            return specApp;
        }
        return appCode;
    }

    /**
     * @param appCode the appCode to set
     */
    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    /**
     * @return the loginMutex
     */
    public boolean isLoginMutex() {
        return loginMutex;
    }

    /**
     * @param loginMutex the loginMutex to set
     */
    public void setLoginMutex(boolean loginMutex) {
        this.loginMutex = loginMutex;
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.session.IVfSsoSession#create(java.lang.Object, boolean)
     */
    @Override
    public final String create(TSession session) throws SessionException {
        String token = TokenUtil.genUUID();
        if (isLoginMutex()) {
            if (addSessionMutex(session, token)) {
                doAddSession(token, session, SessionStatusKind.login);
            } else {
                String oldToken = getOldToken(session);
                TSession elder = get(oldToken);
                if (elder != null && SessionStatusKind.login.equals(elder.getSessionStatus())) {
                    doAddSession(token, session, SessionStatusKind.pending);
                } else {
                    if (getLogger().isInfoEnabled()) {
                        getLogger().info("no validate session({}) to be mutex! {}", oldToken,
                            getMutexId(session));
                    }
                    touchMutexInfo(getAppCode(), getMutexId(session), token);
                    doAddSession(token, session, SessionStatusKind.login);
                }
            }
        } else {
            doAddSession(token, session, SessionStatusKind.login);
        }
        return token;
    }

    /**
     * @param session
     * @param token
     * @param status
     * @throws SessionException
     */
    private final void doAddSession(String token, TSession session, SessionStatusKind status)
                                                                                             throws SessionException {
        if (status != null) {
            session.setSessionStatus(status);
            session.setSessionTimestamp(new Date());
        }
        if (getLogger().isDebugEnabled()) {
            getLogger().debug("add new session({}):{}", token, session);
        }
        if (!addToken(getAppCode(), token, session)) {
            throw new SessionException("Session " + token + "添加失败");
        }
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.session.IVfSsoSession#forceIn(java.lang.String, com.netfinworks.vfsso.session.spec.ISession)
     */
    @Override
    public boolean forceIn(String token, TSession session) throws SessionException {
        boolean ret = false;
        if (isLoginMutex()) {
            String oldToken = getOldToken(session);
            if (oldToken != null) {
                TSession elder = get(oldToken);
                if (elder != null && SessionStatusKind.login.equals(elder.getSessionStatus())) {
                    ret = doTouchSession(oldToken, elder, SessionStatusKind.kicked);
                    if (getLogger().isInfoEnabled()) {
                        getLogger().info("old session({}) be kick out:{}", oldToken,
                            getMutexId(elder));
                    } else if (getLogger().isDebugEnabled()) {
                        getLogger().debug("old session({}) be kick out:{}", oldToken, elder);
                    }
                }
            } else if (getLogger().isInfoEnabled()) {
                getLogger().info("old session({}) not found, force in directly", oldToken);
            }
            ret = doTouchSession(token, session, SessionStatusKind.login);
            if (ret) {
                if (!addMutexInfo(getAppCode(), getMutexId(session), token)) {
                    touchMutexInfo(getAppCode(), getMutexId(session), token);
                }
            } else if (getLogger().isInfoEnabled()) {
                getLogger().info("session({},{}) force in failed.", token, getMutexId(session));
            }
        } else {
            getLogger().warn("No login mutex not necessary to force in, token{}, {}", token,
                getMutexId(session));
            // 不处理
            ret = true;
        }
        return ret;
    }

    /**
     * @param token
     * @param session
     * @param status
     * @return
     */
    private boolean doTouchSession(String token, TSession session, SessionStatusKind status) {
        if (status != null) {
            session.setSessionStatus(status);
            session.setSessionTimestamp(new Date());
        }
        return touchSession(getAppCode(), token, session);
    }

    /**
     * @param session
     * @return
     */
    protected abstract String getMutexId(TSession session);

    /**
     * @param session
     * @param token
     * @return
     */
    private boolean addSessionMutex(TSession session, String token) {
        return addMutexInfo(getAppCode(), getMutexId(session), token);
    }

    /**
     * @param mutexId
     * @param token
     * @param token2 
     */
    protected abstract boolean addMutexInfo(String appCode, String mutexId, String token);

    /**
     * 获取同Session相关的其他token
     * @param session
     * @return
     */
    private String getOldToken(TSession session) {
        return getMutexInfo(getAppCode(), getMutexId(session));
    }

    /**
     * @param mutexId
     * @return
     */
    protected abstract String getMutexInfo(String appCode, String mutexId);

    /**
     * @param token
     * @param session
     * @return
     */
    protected abstract boolean addToken(String appCode, String token, TSession session);

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.session.IVfSsoSession#remove(java.lang.String)
     */
    @Override
    public final boolean remove(String token) throws SessionException {
        getLogger().debug("Remove session:{}", token);
        boolean ret = false;
        if (isLoginMutex()) {
            TSession old = getSession(getAppCode(), token);
            if (old != null) {
                if (SessionStatusKind.login.equals(old.getSessionStatus())) {
                    if (getLogger().isInfoEnabled()) {
                        getLogger().info("Login session({},{}) removed.", token, getMutexId(old));
                    }
                    if (!removeMutexInfo(getAppCode(), getMutexId(old))) {
                        getLogger().warn("Remove session({},{}) mutex info failed!", token,
                            getMutexId(old));
                    }
                } else {
                    if (getLogger().isInfoEnabled()) {
                        getLogger().info("Pending session({},{}) removed.", token, getMutexId(old));
                    }
                }
            } else {
                if (getLogger().isInfoEnabled()) {
                    getLogger().info("No session({}) found.", token);
                }
            }
        }
        ret = removeSession(getAppCode(), token);
        return ret;
    }

    /**
     * 移除冲突信息
     * @param appCode
     * @param mutexId
     * @return 
     */
    protected abstract boolean removeMutexInfo(String appCode, String mutexId);

    /**
     * 移除Session
     * @param appCode
     * @param token
     * @return
     */
    protected abstract boolean removeSession(String appCode, String token);

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.session.IVfSsoSession#get(java.lang.String)
     */
    @Override
    public final TSession get(String token) throws SessionException {
        TSession ret = getSession(getAppCode(), token);
        if (getLogger().isDebugEnabled()) {
            getLogger().debug("get session({}) result:{}", token, ret);
        }
        return ret;
    }

    /**
     * 获取Session
     * @param appCode
     * @param token
     * @return
     * @throws SessionException 
     */
    protected abstract TSession getSession(String appCode, String token) throws SessionException;

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.session.IVfSsoSession#touch(java.lang.String, java.lang.Object)
     */
    @Override
    public final boolean touch(String token, TSession session) {
        if (isLoginMutex()) {
            touchMutexInfo(getAppCode(), getMutexId(session), token);
        }
        if (getLogger().isDebugEnabled()) {
            getLogger().debug("touch session({}):{}", token, session);
        }
        return touchSession(getAppCode(), token, session);
    }

    /**
     * 保持互斥信息活动
     * @param appCode
     * @param mutexId
     * @param token
     */
    protected abstract boolean touchMutexInfo(String appCode, String mutexId, String token);

    /**
     * 保持Session活动
     * @param appCode
     * @param token
     * @param session
     * @return
     */
    protected abstract boolean touchSession(String appCode, String token, TSession session);

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.session.IVfSsoSession#getExt(java.lang.String)
     */
    @Override
    public final Map<String, String> getExt(String token) {
        return getSessionExt(getAppCode(), token);
    }

    /**
     * 获取Session的扩展数据
     * @param appCode
     * @param token
     * @return
     */
    protected abstract Map<String, String> getSessionExt(String appCode, String token);

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.session.IVfSsoSession#setExt(java.lang.String, java.util.Map)
     */
    @Override
    public final boolean setExt(String token, Map<String, String> ext) {
        return setSessionExt(getAppCode(), token, ext);
    }

    /**
     * 设置Session的扩展信息
     * @param appCode
     * @param token
     * @param ext
     * @return
     */
    protected abstract boolean setSessionExt(String appCode, String token, Map<String, String> ext);

}
