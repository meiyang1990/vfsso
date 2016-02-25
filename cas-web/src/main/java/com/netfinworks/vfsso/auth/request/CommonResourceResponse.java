/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.auth.request;

import com.netfinworks.vfsso.cas.domain.ResourceResponse;

/**
 * <p>通用回应</p>
 * @author huipeng
 * @version $Id: CommonResourceResponse.java, v 0.1 Jun 12, 2014 3:12:28 PM knico Exp $
 */
public class CommonResourceResponse<TData> extends ResourceResponse {
    private TData data;

    /**
     * @return the data
     */
    public TData getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(TData data) {
        this.data = data;
    }
}
