/**
 * 
 */
package com.netfinworks.vfsso.cas.exception;

import com.netfinworks.vfsso.cas.domain.ResultKind;

/**
 * @author knico
 * @since Oct 24, 2012
 * 
 */
public class AuthFailException extends AuthException {
    private static final long serialVersionUID = -7360711797944208398L;

    public AuthFailException() {
        super();
    }

    public AuthFailException(String message) {
        super(message);
    }

    public AuthFailException(ResultKind result, String message) {
        super(result, message);
    }
}
