/**
 * 
 */
package com.netfinworks.vfsso.cas.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;



public class ResourceResponse {
	private String code;
	private String msg;

	/**
	 * @return the resultCode
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param resultCode the resultCode to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the resultMsg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param resultMsg the resultMsg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 
	 * @param result
	 */
	public void setResultKind(ResultKind result) {
		if (result != null) {
			this.setCode(result.getCode());
			this.setMsg(result.getMessage());
		}
	}

	/**
	 * @param thr
	 */
	public void setResultException(Throwable thr) {
		this.setResultKind(ResultKind.EXCEPTION);
		if (thr != null) {
			this.msg += ":" + thr.getMessage();
		}
	}

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
