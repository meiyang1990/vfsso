/**
 * 
 */
package com.netfinworks.vfsso.client.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.netfinworks.vfsso.client.core.IVfSsoCasAuditListener;
import com.netfinworks.vfsso.client.core.IVfSsoCasListener;

/**
 * 单点登录filter 配置信息
 * 
 * @author bigknife
 * 
 */
public class VfSsoClientConfig {
    /** 无需鉴权的url pattern */
    private List<Pattern>                notAuthencatedPattern = new ArrayList<Pattern>();
    /** 无需审计的url pattern */
    private List<Pattern>                notAuditPattern       = new ArrayList<Pattern>();
    /** 默认系统风格 */
    private String                       defaultStyle;
    /** 默认方法参数名称 */
    private String                       defaultMethodParamName;
    /** 默认方法，当RPC 风格时，如果通过methodName没有获得method，则使用defaultMethod */
    private String                       defaultMethod;
    /** httpclient socket 超时（毫秒数） */
    private int                          httpClientSoTimeout;
    /** httpclient connection 超时（毫秒数） */
    private int                          httpClientConnectionTimeout;
    /** 特殊资源配置 */
    private Map<String, ResourceConfig>  specialResourceConfig;
    /** CAS client 监听器 */
    private List<IVfSsoCasListener>      casClientListeners    = new ArrayList<IVfSsoCasListener>();
    private List<IVfSsoCasAuditListener> casAuditListeners     = new ArrayList<IVfSsoCasAuditListener>();

    private SsoApiConfig                 apiConfig             = new SsoApiConfig();

    private static VfSsoClientConfig     defaultConfig;

    public static void init(VfSsoClientConfig config) {
        defaultConfig = config;
    }

    public static VfSsoClientConfig getDefaultConfig() {
        return defaultConfig;
    }

    public static SsoApiConfig getDefaultApiConfig() {
        return defaultConfig != null ? defaultConfig.getApiConfig() : null;
    }

    public void addResourceConfig(ResourceConfig rc) {
        if (rc != null) {
            if (specialResourceConfig == null) {
                specialResourceConfig = new HashMap<String, VfSsoClientConfig.ResourceConfig>();
            }
            specialResourceConfig.put(rc.getName(), rc);
        }
    }

    public ResourceConfig makeOrGetResourceConfig(String name) {
        if (specialResourceConfig == null) {
            specialResourceConfig = new HashMap<String, VfSsoClientConfig.ResourceConfig>();
        }
        ResourceConfig rc = specialResourceConfig.get(name);
        if (rc == null) {
            rc = new ResourceConfig();
            rc.setName(name);
            specialResourceConfig.put(name, rc);
        }
        return rc;
    }

    public ResourceConfig getResourceConfigByUrl(String url) {
        if (specialResourceConfig != null && url != null) {
            for (Entry<String, ResourceConfig> entry : specialResourceConfig.entrySet()) {
                ResourceConfig rc = entry.getValue();
                if (url.equals(rc.getUrl())) {
                    return rc;
                }
            }
        }
        return null;
    }

    public void addCasClientListener(String className) {
        if (className != null && className.trim().length() > 0) {
            try {
                IVfSsoCasListener listener = (IVfSsoCasListener) Class.forName(className.trim())
                    .newInstance();
                if (this.casClientListeners == null) {
                    this.casClientListeners = new ArrayList<IVfSsoCasListener>();
                }
                this.casClientListeners.add(listener);
            } catch (Exception e) {
                throw new RuntimeException("添加Cas SSOFilter监听器出错", e);
            }
        }
    }

    public void addCasAuditListener(String className) {
        if (className != null && className.trim().length() > 0) {
            try {
                IVfSsoCasAuditListener listener = (IVfSsoCasAuditListener) Class.forName(
                    className.trim()).newInstance();
                if (this.casAuditListeners == null) {
                    this.casAuditListeners = new ArrayList<IVfSsoCasAuditListener>();
                }
                this.casAuditListeners.add(listener);
            } catch (Exception e) {
                throw new RuntimeException("添加Cas SSOFilter监听器出错", e);
            }
        }
    }

    /**
     * @return the casListeners
     */
    public List<IVfSsoCasListener> getCasClientListeners() {
        return casClientListeners;
    }

    /**
     * @param casClientListeners the casListeners to set
     */
    public void setCasClientListeners(List<IVfSsoCasListener> casClientListeners) {
        this.casClientListeners = casClientListeners;
    }

    /**
     * @return the casAuditListeners
     */
    public List<IVfSsoCasAuditListener> getCasAuditListeners() {
        return casAuditListeners;
    }

    /**
     * @param casAuditListeners the casAuditListeners to set
     */
    public void setCasAuditListeners(List<IVfSsoCasAuditListener> casAuditListeners) {
        this.casAuditListeners = casAuditListeners;
    }

    /**
     * @return the authUrl
     */
    public String getCasUrl() {
        return getApiConfig().getCasUrl();
    }

    /**
     * @return the defaultApiConfig
     */
    public SsoApiConfig getApiConfig() {
        return apiConfig;
    }

    /**
     * @param casUrl the casUrl to set
     */
    public void setCasUrl(String casUrl) {
        this.apiConfig.setCasUrl(casUrl);
    }

    /**
     * @return the httpClientSoTimeout
     */
    public int getHttpClientSoTimeout() {
        return httpClientSoTimeout;
    }

    /**
     * @param httpClientSoTimeout the httpClientSoTimeout to set
     */
    public void setHttpClientSoTimeout(int httpClientSoTimeout) {
        this.httpClientSoTimeout = httpClientSoTimeout;
    }

    /**
     * @return the httpClientConnectionTimeout
     */
    public int getHttpClientConnectionTimeout() {
        return httpClientConnectionTimeout;
    }

    /**
     * @param httpClientConnectionTimeout the httpClientConnectionTimeout to set
     */
    public void setHttpClientConnectionTimeout(int httpClientConnectionTimeout) {
        this.httpClientConnectionTimeout = httpClientConnectionTimeout;
    }

    /**
     * @return the systemCode
     */
    public String getAppId() {
        return this.getApiConfig().getAppId();
    }

    /**
     * @param systemCode the systemCode to set
     */
    public void setAppId(String appId) {
        this.getApiConfig().setAppId(appId);
    }

    /**
     * @return the defaultMethod
     */
    public String getDefaultMethod() {
        return defaultMethod;
    }

    /**
     * @param defaultMethod the defaultMethod to set
     */
    public void setDefaultMethod(String defaultMethod) {
        this.defaultMethod = defaultMethod;
    }

    /**
     * @param loginUrl the uniLoginUrl to set
     */
    public void setLoginUrl(String loginUrl) {
        this.apiConfig.setLoginUrl(loginUrl);
    }

    /**
     * @param loginUrl the uniLoginUrl to set
     */
    public void setLogoutUrl(String logoutUrl) {
        this.apiConfig.setLogoutUrl(logoutUrl);
    }

    /**
     * @return the defaultStyle
     */
    public String getDefaultStyle() {
        return defaultStyle;
    }

    /**
     * @param defaultStyle the defaultStyle to set
     */
    public void setDefaultStyle(String defaultStyle) {
        this.defaultStyle = defaultStyle;
    }

    /**
     * @return the defaultMethodParamName
     */
    public String getDefaultMethodParamName() {
        return defaultMethodParamName;
    }

    /**
     * @param defaultMethodParamName the defaultMethodParamName to set
     */
    public void setDefaultMethodParamName(String defaultMethodParamName) {
        this.defaultMethodParamName = defaultMethodParamName;
    }

    /**
     * @return the specialResourceConfig
     */
    public Map<String, ResourceConfig> getSpecialResourceConfig() {
        return specialResourceConfig;
    }

    /**
     * @param specialResourceConfig the specialResourceConfig to set
     */
    public void setSpecialResourceConfig(Map<String, ResourceConfig> specialResourceConfig) {
        this.specialResourceConfig = specialResourceConfig;
    }

    public static class ResourceConfig {
        /** 资源名称 */
        private String name;
        /** 资源风格 */
        private String style;
        /** 资源URL */
        private String url;
        /** 资源方法参数 */
        private String methodParamName;
        /** 默认的方法 */
        private String defaultMethod;

        /**
         * @return the defaultMethod
         */
        public String getDefaultMethod() {
            return defaultMethod;
        }

        /**
         * @param defaultMethod the defaultMethod to set
         */
        public void setDefaultMethod(String defaultMethod) {
            this.defaultMethod = defaultMethod;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
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

        /**
         * @return the methodParamName
         */
        public String getMethodParamName() {
            return methodParamName;
        }

        /**
         * @param methodParamName the methodParamName to set
         */
        public void setMethodParamName(String methodParamName) {
            this.methodParamName = methodParamName;
        }

    }

    public List<Pattern> getNotAuthencatedPattern() {
        return notAuthencatedPattern;
    }

    public void setNotAuthencatedPattern(List<Pattern> notAuthencatedPattern) {
        this.notAuthencatedPattern = notAuthencatedPattern;
    }

    public List<Pattern> getNotAuditPattern() {
        return notAuditPattern;
    }

    public void setNotAuditPattern(List<Pattern> notAuditPattern) {
        this.notAuditPattern = notAuditPattern;
    }

    public void addNotAuthencatedPattern(Pattern pattern) {
        if (this.notAuthencatedPattern == null) {
            this.notAuthencatedPattern = new ArrayList<Pattern>();
        }
        this.notAuthencatedPattern.add(pattern);
    }

    public void addNotAuditPattern(Pattern pattern) {
        if (this.notAuditPattern == null) {
            this.notAuditPattern = new ArrayList<Pattern>();
        }
        this.notAuditPattern.add(pattern);
    }
}
