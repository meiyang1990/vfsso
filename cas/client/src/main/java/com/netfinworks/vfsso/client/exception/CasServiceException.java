/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.client.exception;

/**
 * <p>Cas服务异常</p>
 * @author huipeng
 * @version $Id: CasServiceException.java, v 0.1 Jun 19, 2014 6:04:08 PM knico Exp $
 */
public class CasServiceException extends Exception {

    private static final long serialVersionUID = -8948195319634727151L;

    /**
     * 
     */
    public CasServiceException() {
        super();
    }

    /**
     * @param message
     */
    public CasServiceException(String message) {
        super(message);
    }

}
