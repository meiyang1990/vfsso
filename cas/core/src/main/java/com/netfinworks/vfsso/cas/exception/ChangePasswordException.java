/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.cas.exception;

import com.netfinworks.vfsso.cas.enums.CasFailTypeKind;

/**
 * <p>注释</p>
 * @author huipeng
 * @version $Id: ChangePasswordException.java, v 0.1 Jun 9, 2014 6:52:15 PM knico Exp $
 */
public class ChangePasswordException extends Exception {

    private static final long serialVersionUID = -5264007731372721079L;

    private CasFailTypeKind   type;

    /**
     * @return the type
     */
    public CasFailTypeKind getType() {
        return type;
    }

    /**
     * @param message
     * @param cause
     */
    public ChangePasswordException(CasFailTypeKind type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    /**
     * @param message
     */
    public ChangePasswordException(CasFailTypeKind type, String message) {
        super(message);
        this.type = type;
    }
}
