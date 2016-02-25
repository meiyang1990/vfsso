/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.session.spec;

/**
 * <p>会话配置（线程安全）</p>
 * @author huipeng
 * @version $Id: SessionConfig.java, v 0.1 Jun 19, 2014 6:54:19 PM knico Exp $
 */
public class VfSessionConfig {
    private static ThreadLocal<String> sysCode = new ThreadLocal<String>();

    /**
     * @return the sysCode
     */
    public static String getSysCode() {
        return sysCode.get();
    }

    /**
     * @param sysCode the sysCode to set
     */
    public static void setSysCode(String sysCode) {
        VfSessionConfig.sysCode.set(sysCode);
    }

    public static void clear() {
        sysCode.remove();
    }
}
