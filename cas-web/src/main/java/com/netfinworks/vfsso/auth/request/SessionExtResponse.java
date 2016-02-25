/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.auth.request;

import java.util.Map;

import com.netfinworks.vfsso.cas.domain.ResourceResponse;

/**
 * <p>获取扩展信息回应</p>
 * @author huipeng
 * @version $Id: SessionExtResponse.java, v 0.1 Jun 11, 2014 6:03:02 PM knico Exp $
 */
public class SessionExtResponse extends ResourceResponse {
    private Map<String, String> data;

    /**
     * @return the data
     */
    public Map<String, String> getData() {
        return data;
    }

    /**
     * @param data
     */
    public void setData(Map<String, String> data) {
        
        this.data = data;
    }
}
