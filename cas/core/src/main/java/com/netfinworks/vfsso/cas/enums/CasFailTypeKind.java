/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.cas.enums;

/**
 * <p>CAS 登录、修改密码等失败类型</p>
 * @author huipeng
 * @version $Id: CasFailTypeKind.java, v 0.1 Jun 10, 2014 10:38:54 AM knico Exp $
 */
public enum CasFailTypeKind {
    unsupported, //不支持
    failed, //失败
    exception, //异常
    noaccount, //没有该帐号
}
