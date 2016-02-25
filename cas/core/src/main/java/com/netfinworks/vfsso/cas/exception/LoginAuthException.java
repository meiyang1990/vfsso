/**
 * 
 */
package com.netfinworks.vfsso.cas.exception;

import com.netfinworks.vfsso.cas.enums.CasFailTypeKind;

/**
 * @author knico
 * @since Nov 30, 2012
 * 
 */
public class LoginAuthException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -313346926709826744L;
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
    public LoginAuthException(CasFailTypeKind type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    /**
     * @param message
     */
    public LoginAuthException(CasFailTypeKind type, String message) {
        super(message);
        this.type = type;
    }
}
