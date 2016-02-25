/**
 * 
 */
package com.netfinworks.vfsso.cas.exception;

import com.netfinworks.vfsso.cas.domain.ResourceResponse;
import com.netfinworks.vfsso.cas.domain.ResultKind;

/**
 * @author knico
 * @since Oct 24, 2012
 * 
 */
public class AuthException extends Exception {
	private static final long serialVersionUID = 8239086978063494216L;
	private ResultKind result = ResultKind.DENIED;

	public AuthException() {
		super();
	}

	public AuthException(String message) {
		super(message);
	}

	public AuthException(ResultKind result, String message) {
		super(message);
		this.result = result;
	}

	/**
	 * @return the result
	 */
	public ResultKind getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(ResultKind result) {
		this.result = result;
	}

	public void fillResponse(ResourceResponse resp) {
		if (resp != null) {
			if (this.result != null) {
				resp.setResultKind(result);
			}
			if (this.getMessage() != null) {
				resp.setMsg(getMessage());
			}
		}
	}
}
