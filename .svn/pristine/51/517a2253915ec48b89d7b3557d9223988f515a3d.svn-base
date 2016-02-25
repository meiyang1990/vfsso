/**
 * 
 */
package com.netfinworks.vfsso.client.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Guardian 时间对象
 * @author bigknife
 *
 */
public class CasEvent {
    /**
     * 事件类型
     * @author bigknife
     *
     */
    public static enum CasEventType {
        /**鉴权异常事件*/
        AUTH_EXCEPTION,
        /**资源禁止访问*/
        AUTH_FORBIDDEN,
        /**未登录*/
        AUTH_NOT_LOGIN,
        /**需重试*/
        AUTH_MSG_FORWARD,
    }

    //事件类型
    private CasEventType        type;

    private HttpServletRequest  request;
    private HttpServletResponse response;

    private VfSsoClientConfig   config;

    public CasEvent() {
        super();
    }

    public CasEvent(CasEventType type, HttpServletRequest request, HttpServletResponse response,
                    VfSsoClientConfig config) {
        super();
        this.type = type;
        this.request = request;
        this.response = response;
        this.config = config;
    }

    /**
     * @return the config
     */
    public VfSsoClientConfig getConfig() {
        return config;
    }

    /**
     * @param config the config to set
     */
    public void setConfig(VfSsoClientConfig config) {
        this.config = config;
    }

    /**
     * @return the type
     */
    public CasEventType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(CasEventType type) {
        this.type = type;
    }

    /**
     * @return the request
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * @return the response
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

}
