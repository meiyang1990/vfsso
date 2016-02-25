/**
 * 
 */
package com.netfinworks.vfsso.client.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.servlet.FilterConfig;

import org.slf4j.Logger;

import com.netfinworks.vfsso.client.common.VfSsoClientConfig.ResourceConfig;

/**
 * <p>CAS Client 配置解析</p>
 * @author huipeng
 * @version $Id: SSOFilterConfigParser.java, v 0.1 Jun 4, 2014 9:28:55 AM knico Exp $
 */
public class VfSsoClientConfigParser {
    private static final String APP_ID_NAME                 = "appId";
    private static final String NOT_AUTHENTICATED_NAME      = "notAuthencated";
    private static final String NOT_AUDIT_NAME              = "notAudit";
    private static final String APPLICATION_LOGIN_URL_NAME  = "loginUrl";
    private static final String APPLICATION_LOGOUT_URL_NAME = "logoutUrl";
    private static final String VFSSO_CAS_URL_NAME          = "casUrl";
    private static final String DEFAULT_STYLE_NAME          = "defaultStyle";
    private static final String DEFAULT_METHOD_NAME         = "defaultMethodParamName";
    private static final String DEFAULT_METHOD              = "defaultMethod";
    private static final String CONN_TIMEOUT_NAME           = "httpClientConnectionTimeout";
    private static final String SO_TIMEOUT_NAME             = "httpClientSoTimeout";
    private static final String CAS_CLIENT_LISTENERS        = "listeners";
    private static final String CAS_AUDIT_LISTENERS         = "auditListeners";

    private static final String OVERRIDE_CONF_NAME          = "overrideConf";

    private static final String SPLIT                       = "[,]";

    private static Logger       logger                      = VfSsoClientLogger.getLogger();

    public static VfSsoClientConfig parse(FilterConfig filterConfig) {
        VfSsoClientConfig config = new VfSsoClientConfig();

        logger.info("VF SSO Filter 初始化...");

        String appId = filterConfig.getInitParameter(APP_ID_NAME);
        logger.info("当前应用编码初始化为：{}", appId);
        config.setAppId(appId);

        String _notAuthencated = filterConfig.getInitParameter(NOT_AUTHENTICATED_NAME);

        if (_notAuthencated != null) {
            String[] tmp = _notAuthencated.split(SPLIT);
            for (String url : tmp) {
                String _url = url.trim();
                //				config.addNotAuthencated(_url);
                logger.info("初始无需鉴权URL：{}", _url);
                Pattern pattern = Pattern.compile(_url);
                config.addNotAuthencatedPattern(pattern);
            }
        }

        String _notAudit = filterConfig.getInitParameter(NOT_AUDIT_NAME);

        if (_notAudit != null) {
            String[] tmp = _notAudit.split(SPLIT);
            for (String url : tmp) {
                String _url = url.trim();
                if (_url.length() > 0) {
                    //					config.addNotAudit(_url);
                    logger.info("初始无需审计URL：{}", _url);
                    Pattern pattern = Pattern.compile(_url);
                    config.addNotAuditPattern(pattern);
                }
            }
        }

        String loginUrl = filterConfig.getInitParameter(APPLICATION_LOGIN_URL_NAME);
        logger.info("登录页面URL初始化为：{}", loginUrl);
        config.setLoginUrl(loginUrl);

        String logoutUrl = filterConfig.getInitParameter(APPLICATION_LOGOUT_URL_NAME);
        logger.info("登出页面URL初始化为：{}", logoutUrl);
        config.setLogoutUrl(logoutUrl);

        String casUrl = filterConfig.getInitParameter(VFSSO_CAS_URL_NAME);
        logger.info("CAS服务 URL初始化为：{}", casUrl);
        config.setCasUrl(casUrl);

        String defaultStyle = filterConfig.getInitParameter(DEFAULT_STYLE_NAME);
        logger.info("系统默认风格设置为：{}", defaultStyle);
        config.setDefaultStyle(defaultStyle);

        String defaultMethodParamName = filterConfig.getInitParameter(DEFAULT_METHOD_NAME);
        logger.info("系统资源默认方法参数设置为：{}", defaultMethodParamName);
        config.setDefaultMethodParamName(defaultMethodParamName);

        String defaultMethod = filterConfig.getInitParameter(DEFAULT_METHOD);
        logger.info("系统资源默认方法设置为：{}", defaultMethod);
        config.setDefaultMethod(defaultMethod);

        String listeners = filterConfig.getInitParameter(CAS_CLIENT_LISTENERS);
        if (listeners != null && listeners.length() > 0) {
            logger.info("VFSSO CAS Filter Listener class 设置为：{}", listeners);
            String[] arrListener = listeners.split(SPLIT);
            for (String listener : arrListener) {
                config.addCasClientListener(listener);
            }
        }

        String alisteners = filterConfig.getInitParameter(CAS_AUDIT_LISTENERS);
        if (listeners != null && alisteners.length() > 0) {
            logger.info("VFSSO CAS Audit Listener class 设置为：{}", listeners);
            String[] arrListener = alisteners.split(SPLIT);
            for (String listener : arrListener) {
                config.addCasAuditListener(listener);
            }
        }

        String httpClientConnectionTimeout = filterConfig.getInitParameter(CONN_TIMEOUT_NAME);
        if (httpClientConnectionTimeout != null) {
            logger.info("HttpClient 连接超时时间设置为：{}", httpClientConnectionTimeout);
            config.setHttpClientConnectionTimeout(Integer.valueOf(httpClientConnectionTimeout));
        }

        String httpClientSoTimeout = filterConfig.getInitParameter(SO_TIMEOUT_NAME);
        if (httpClientSoTimeout != null) {
            logger.info("HttpClient SO 超时时间设置为：{}", httpClientSoTimeout);
            config.setHttpClientSoTimeout(Integer.valueOf(httpClientSoTimeout));
        }

        // 加载override 配置
        String overrideConf = filterConfig.getInitParameter(OVERRIDE_CONF_NAME);
        if (overrideConf != null && overrideConf.trim().length() > 0) {
            overrideConfig(overrideConf, config);
        }

        logger.info("VFSSO CAS SSO Filter 初始化完成.");

        return config;
    }

    public static void overrideConfig(String overrideConf, VfSsoClientConfig config) {
        logger.info("加载重载配置文件：{}", overrideConf);
        Properties p = new Properties();
        try {
            String path = overrideConf.trim();
            p.load(path.startsWith("classpath:") ? VfSsoClientConfigParser.class
                .getResourceAsStream(path.substring(path.indexOf(":") + 1)) : new FileInputStream(
                path));

            String overridedAppId = p.getProperty(APP_ID_NAME);
            if (overridedAppId != null) {
                logger.info("重载{} from {} to {}", new Object[] { APP_ID_NAME, config.getAppId(),
                        overridedAppId });
                config.setAppId(overridedAppId);
            }

            String overrideNotAuthencated = p.getProperty(NOT_AUTHENTICATED_NAME);
            if (overrideNotAuthencated != null) {
                String[] tmp = overrideNotAuthencated.split(SPLIT);
                if (config.getNotAuthencatedPattern() != null) {
                    config.getNotAuthencatedPattern().clear();
                }
                for (String url : tmp) {
                    String _url = url.trim();
                    logger.info("重载无需鉴权URL：{}", _url);
                    Pattern pattern = Pattern.compile(_url);
                    config.addNotAuthencatedPattern(pattern);
                }
            }

            String overrideNotAudit = p.getProperty(NOT_AUDIT_NAME);
            if (overrideNotAudit != null) {
                String[] tmp = overrideNotAudit.split(SPLIT);
                if (config.getNotAuditPattern() != null) {
                    config.getNotAuditPattern().clear();
                }
                for (String url : tmp) {
                    String _url = url.trim();
                    logger.info("重载无需审计URL：{}", _url);
                    Pattern pattern = Pattern.compile(_url);
                    config.addNotAuditPattern(pattern);
                }
            }

            String overrideLoginUrl = p.getProperty(APPLICATION_LOGIN_URL_NAME);
            if (overrideLoginUrl != null) {
                logger.info("重载{} from {} to {}", new Object[] { APPLICATION_LOGIN_URL_NAME,
                        config.getApiConfig().getLoginUrl(), overrideLoginUrl });
                config.setLoginUrl(overrideLoginUrl);
            }

            String overrideLogoutUrl = p.getProperty(APPLICATION_LOGOUT_URL_NAME);
            if (overrideLoginUrl != null) {
                logger.info("重载{} from {} to {}", new Object[] { APPLICATION_LOGOUT_URL_NAME,
                        config.getApiConfig().getLogoutUrl(), overrideLogoutUrl });
                config.setLogoutUrl(overrideLogoutUrl);
            }

            String overrideCasUrl = p.getProperty(VFSSO_CAS_URL_NAME);
            if (overrideCasUrl != null) {
                logger.info("重载{} from {} to {}",
                    new Object[] { VFSSO_CAS_URL_NAME, config.getCasUrl(), overrideCasUrl });
                config.setCasUrl(overrideCasUrl);
            }

            String defaultStyle = p.getProperty(DEFAULT_STYLE_NAME);
            if (defaultStyle != null) {
                logger.info("重载{} from {} to {}",
                    new Object[] { DEFAULT_STYLE_NAME, config.getDefaultStyle(), defaultStyle });
                config.setDefaultStyle(defaultStyle);
            }

            String defaultMethodParamName = p.getProperty(DEFAULT_METHOD_NAME);
            if (defaultMethodParamName != null) {
                logger.info("重载{} from {} to {}",
                    new Object[] { DEFAULT_METHOD_NAME, config.getDefaultMethodParamName(),
                            defaultMethodParamName });
                config.setDefaultMethodParamName(defaultMethodParamName);
            }

            String defaultMethod = p.getProperty(DEFAULT_METHOD);
            if (defaultMethod != null) {
                logger.info("重载{} from {} to {}",
                    new Object[] { DEFAULT_METHOD, config.getDefaultMethod(), defaultMethod });
                config.setDefaultMethod(defaultMethod);
            }

            String listeners = p.getProperty(CAS_CLIENT_LISTENERS);
            if (listeners != null && listeners.length() > 0) {
                logger.info("VFSSO CAS Filter Listener class 重置为：{}", listeners);
                String[] arrListener = listeners.split(SPLIT);
                if (config.getCasClientListeners() != null) {
                    config.getCasClientListeners().clear();
                }
                for (String listener : arrListener) {
                    config.addCasClientListener(listener);
                }
            }

            String alisteners = p.getProperty(CAS_AUDIT_LISTENERS);
            if (alisteners != null && alisteners.length() > 0) {
                logger.info("VFSSO CAS Audit Listener class 重置为：{}", listeners);
                String[] arrListener = alisteners.split(SPLIT);
                if (config.getCasAuditListeners() != null) {
                    config.getCasAuditListeners().clear();
                }
                for (String listener : arrListener) {
                    config.addCasAuditListener(listener);
                }
            }

            String httpClientConnectionTimeout = p.getProperty(CONN_TIMEOUT_NAME);
            if (httpClientConnectionTimeout != null) {
                logger.info("重载{} from {} to {}",
                    new Object[] { CONN_TIMEOUT_NAME, config.getHttpClientConnectionTimeout(),
                            httpClientConnectionTimeout });
                config.setHttpClientConnectionTimeout(Integer.valueOf(httpClientConnectionTimeout));
            }

            String httpClientSoTimeout = p.getProperty(SO_TIMEOUT_NAME);
            if (httpClientSoTimeout != null) {
                logger.info("重载{} from {} to {}",
                    new Object[] { SO_TIMEOUT_NAME, config.getHttpClientSoTimeout(),
                            httpClientSoTimeout });
                config.setHttpClientSoTimeout(Integer.valueOf(httpClientSoTimeout));
            }

            // special url config
            // spec.<name>.style =
            // spec.<name>.url =
            // spec.<name>.methodParamName =
            for (Entry<Object, Object> entry : p.entrySet()) {
                String key = (String) entry.getKey();
                if (key.startsWith("spec.")) {
                    String[] specs = key.split("[.]");
                    if (specs.length == 3) {

                        ResourceConfig rc = config.makeOrGetResourceConfig(specs[1]);

                        if ("style".equals(specs[2])) {
                            rc.setStyle((String) entry.getValue());
                        }
                        if ("url".equals(specs[2])) {
                            rc.setUrl((String) entry.getValue());
                        }
                        if ("methodParamName".equals(specs[2])) {
                            rc.setMethodParamName((String) entry.getValue());
                        }
                        if ("defaultMethod".equals(specs[2])) {
                            rc.setDefaultMethod((String) entry.getValue());
                        }

                        config.addResourceConfig(rc);
                        logger.info(
                            "设置特殊资源：name = {}, style = {}, url = {}, methodParamName = {}",
                            new Object[] { rc.getName(), rc.getStyle(), rc.getUrl(),
                                    rc.getMethodParamName() });
                    }
                }
            }

        } catch (FileNotFoundException e) {
            logger.warn("重载配置文件不存在:{}", overrideConf.trim());
        } catch (IOException e) {
            logger.warn("IO异常，无法读取重载配置文件：{}", overrideConf.trim());
            logger.error("IOException", e);
        }
    }
}
