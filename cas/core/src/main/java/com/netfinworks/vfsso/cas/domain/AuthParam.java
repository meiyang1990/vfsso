/**
 * 
 */
package com.netfinworks.vfsso.cas.domain;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author knico
 * @since Oct 16, 2012
 * 
 */
public class AuthParam {
    /**
     * 系统代码,应该由各自系统自己定义
     */
    private String app;

    /**
     * 用户令牌,token 可从cookie中取到
     */
    private String token;

    /**
     * 系统风格，rpc/rest
     */
    private String style;

    /**
     * 方法的名称<br>
     * rest：无效<br>
     * rpc：method参数的key名称
     */
    private String methodName;

    /**
     * 调用方法<br>
     * rest:GET/POST/PUT/DELETE<br>
     * rpc:个系统自己定义
     */
    private String method;

    /**
     * 调用的完整URL，？以后的无效，可以不传
     */
    private String url;

    /**
     * @return the app
     */
    public String getApp() {
        return app;
    }

    /**
     * @param app the app to set
     */
    public void setApp(String app) {
        this.app = app;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the style
     */
    public String getStyle() {
        return style;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * @return the methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @param methodName the methodName to set
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public String toString() {
        try {
            Map props = BeanUtils.describe(this);
            Iterator iProps = props.keySet().iterator();
            StringBuffer retBuffer = new StringBuffer();
            retBuffer.append(this.getClass().getSimpleName()).append("@").append(this.hashCode())
                .append(":");
            while (iProps.hasNext()) {
                String key = (String) iProps.next();
                // skip false property "class"
                if ("class".equals(key)) {
                    continue;
                }
                retBuffer.append(key).append("=[").append(props.get(key)).append("]");
                if (iProps.hasNext()) {
                    retBuffer.append(", ");
                }
            }
            return retBuffer.toString();
        } catch (Exception e) {
            return super.toString();
        }
    }
}
