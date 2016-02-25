/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.session;

import java.util.Map;

import com.netfinworks.vfsso.session.exceptions.SessionException;
import com.netfinworks.vfsso.session.spec.ISession;

/**
 * <p>Session接口声明</p>
 * @author huipeng
 * @version $Id: IVfSsoSession.java, v 0.1 Jun 4, 2014 4:24:16 PM knico Exp $
 */
public interface IVfSsoSession<TSession extends ISession> {
    String create(TSession session) throws SessionException;

    boolean forceIn(String token, TSession session) throws SessionException;

    boolean remove(String token) throws SessionException;

    TSession get(String token) throws SessionException;

    boolean touch(String token, TSession session);

    Map<String, String> getExt(String token) throws SessionException;

    boolean setExt(String token, Map<String, String> ext) throws SessionException;
}
