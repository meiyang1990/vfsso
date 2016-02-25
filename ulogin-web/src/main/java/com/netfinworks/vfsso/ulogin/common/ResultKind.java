/**
 * 
 */
package com.netfinworks.vfsso.ulogin.common;

/**
 * @author knico
 * @since Sep 21, 2012
 * 
 */
public enum ResultKind {
	SUCCESS("success", "操作成功"), FAILED("failed", "操作失败"), EXCEPTION("exception","有异常发生");

	private String code;
	private String msg;

	private ResultKind(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.msg;
	}
}
