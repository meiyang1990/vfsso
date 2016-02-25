/**
 * 
 */
package com.netfinworks.vfsso.client.cas;

/**
 * <p>鉴权结果枚举</p>
 * @author huipeng
 * @version $Id: AuthResult.java, v 0.1 Jun 12, 2014 3:04:27 PM knico Exp $
 */
public enum AuthResult {
    AUTH_NOT_LOGIN("expired", "未登录"), //
    AUTH_RESOURCE_FORBIDDEN("denied", "资源禁止访问"), //
    AUTH_EXCEPTION("exception", "鉴权服务异常"), //
    AUTH_OK("pass", "鉴权通过"), //
    AUTH_DIRECT_PASS("direct_pass", "直接放行"), //
    AUTH_PENDING("pending", "等待认证"), //
    AUTH_KICKED("kicked", "被踢出"), //
    AUTH_SESSION_ERR("session_excp", "会话服务异常"), //
    ;

    private String code, messgae;

    private AuthResult(String code, String messgae) {
        this.code = code;
        this.messgae = messgae;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return messgae;
    }

    public static AuthResult getByCode(String code) {
        AuthResult ret = null;
        for (AuthResult item : values()) {
            if (item.equals(code)) {
                ret = item;
                break;
            }
        }
        return ret;
    }

    public boolean equals(String code) {
        return getCode().equals((Object) code);
    }
}
