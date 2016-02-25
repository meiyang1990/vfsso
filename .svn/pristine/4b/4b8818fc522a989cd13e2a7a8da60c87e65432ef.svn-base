/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.client.core.ctx;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netfinworks.vfsso.client.core.IVfSsoCasContext;

/**
 * <p>VFSSO CAS context for Servlet </p>
 * @author huipeng
 * @version $Id: ServletCasContext.java, v 0.1 Jun 4, 2014 1:41:28 PM knico Exp $
 */
public class ServletCasContext implements IVfSsoCasContext {

    private String              url;
    private HttpServletRequest  req;
    private HttpServletResponse resp;

    /**
     * @return the req
     */
    public HttpServletRequest getHttpRequest() {
        return req;
    }

    /**
     * @return the resp
     */
    public HttpServletResponse getHttpResponse() {
        return resp;
    }

    /**
     * @param hsr
     * @param response 
     */
    public ServletCasContext(HttpServletRequest hsr, HttpServletResponse response) {
        super();
        this.req = hsr;
        this.resp = response;
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.client.core.IVfSsoCasContext#getUrl()
     */
    @Override
    public String getUrl() {
        if (url == null) {
            url = req.getRequestURI().substring(req.getContextPath().length());
            if (url.startsWith("/") && url.length() > 1) {
                url = url.substring(1);
            }
        }
        return url;
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.client.core.IVfSsoCasContext#getToken()
     */
    @Override
    public String getToken() {
        // TODO Auto-generated method stub
        return null;
    }

}
