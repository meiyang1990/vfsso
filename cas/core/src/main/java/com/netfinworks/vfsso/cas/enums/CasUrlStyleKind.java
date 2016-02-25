/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.cas.enums;

/**
 * <p>CAS鉴权模式</p>
 * @author huipeng
 * @version $Id: CasAuthModeKind.java, v 0.1 Jun 25, 2014 1:49:19 PM knico Exp $
 */
public enum CasUrlStyleKind {
    restful("REST"), //
    rpc("RPC"), //
    ;
    private String code;

    private CasUrlStyleKind(String code) {
        this.code = code;
    }

    /**
     * @return the description
     */
    public String getCode() {
        return code;
    }

    public static CasUrlStyleKind getByCode(String code) {
        CasUrlStyleKind ret = null;
        for (CasUrlStyleKind item : CasUrlStyleKind.values()) {
            if (item.equals(code)) {
                ret = item;
            }
        }
        return ret;
    }

    public boolean equals(String code) {
        return this.code.equalsIgnoreCase(code);
    }
}
