/**
 * 
 */
package com.netfinworks.vfsso.cas.domain;

/**
 * @author knico
 * @since Sep 21, 2012
 * 
 */
public enum ResultKind {
    PASS("pass", "通过"), //
    DENIED("denied", "禁止"), //
    PENDING("pending", "等待认证"), //
    KICKED("kicked", "等待认证"), //
    EXPIRED("expired", "token过期"), //
    SESSION_EXCP("session_excp","会话服务异常"), //
    EXCEPTION("exception", "有异常发生"), //
    SUCCESS("success", "成功");

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
