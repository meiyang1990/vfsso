/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.session.enums;

/**
 * <p>Session的主要状态</p>
 * @author huipeng
 * @version $Id: SessionStatusKind.java, v 0.1 Jun 9, 2014 5:02:20 PM knico Exp $
 */
public enum SessionStatusKind {
    /**
     * 已登录
     */
    login,
    /**
     * 已验证，但登录未完成，比如只允许登录一人，第二人需要踢掉第一人才能正式登录
     */
    pending,
    /**
     * 有限制的登录，如游客
     */
    limit,
    /**
     * 被踢出
     */
    kicked,
    /**
     * 登出
     */
    logout
}
