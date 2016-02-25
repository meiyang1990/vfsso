/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.client.authapi.domain;

import java.util.HashMap;

/**
 * <p>会话扩展信息</p>
 * @author huipeng
 * @version $Id: SeExt.java, v 0.1 Jun 12, 2014 6:01:46 PM knico Exp $
 */
public class SeExt extends HashMap<String, Object> {

    private static final long serialVersionUID = -4443696068498338200L;

    private boolean           modified;

    /**
     * @return the modified
     */
    public boolean isModified() {
        return modified;
    }

    /**
     * @param modified the modified to set
     */
    public void markModified() {
        this.modified = true;
    }
}
