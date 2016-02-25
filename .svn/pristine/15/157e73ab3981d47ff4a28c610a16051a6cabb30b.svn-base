/**
 *
 */
package com.netfinworks.vfsso.client.authapi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;

import com.netfinworks.vfsso.client.authapi.domain.SeExt;
import com.netfinworks.vfsso.client.authapi.domain.User;
import com.netfinworks.vfsso.client.common.HttpClientFactory;
import com.netfinworks.vfsso.client.common.JsonConverter;
import com.netfinworks.vfsso.client.common.VfSsoClientConfig;
import com.netfinworks.vfsso.client.common.VfSsoClientLogger;
import com.netfinworks.vfsso.client.exception.CasServiceException;

/**
 * 用户工具类
 *
 * @author bigknife
 *
 */
public class VfSsoUser {
    private static Logger              logger       = VfSsoClientLogger.getLogger();

    private static HttpClient          httpClient;
    private static ThreadLocal<User>   currentUser  = new ThreadLocal<User>();
    private static ThreadLocal<String> currentToken = new ThreadLocal<String>();
    private static ThreadLocal<SeExt>  userExt      = new ThreadLocal<SeExt>();
    private static ThreadLocal<String> currentApp   = new ThreadLocal<String>();

    public static void setCurrentToken(String token) {
        currentToken.set(token);
    }

    public static void setCurrentUser(User user) {
        if (currentUser.get() != null) {
            logger.warn("Want to overide exist user object!");
            throw new RuntimeException("Unexpect user set!");
        } else {
            currentUser.set(user);
        }
    }

    /**
     * 获取当前用户的Token
     *
     * @return
     */
    public static String getCurrentToken() {
        String token = currentToken.get();

        return token;
    }

    /**
     * @param config 
     * @return the currentApp
     */
    public static String getCurrentApp(VfSsoClientConfig config) {
        String ret = currentApp.get();
        if (ret == null) {
            currentApp.set(ret = config != null ? config.getAppId() : VfSsoClientConfig
                .getDefaultConfig().getAppId());
        }
        return ret;
    }

    /**
     * @param currentApp the currentApp to set
     */
    public static void setCurrentApp(String appCode) {
        currentApp.set(appCode);
    }

    public static void clear() {
        currentToken.remove();
        currentUser.remove();
        currentApp.remove();
        userExt.remove();
    }

    /**
     * @param userMap
     * @return
     */
    public static User converterUser(Map<String, Object> userMap) {
        User user = null;
        if (userMap != null) {
            user = new User();
            user.setGroup((String) userMap.get("group"));
            user.setId((String) userMap.get("id"));
            user.setLoginName((String) userMap.get("loginName"));
            user.setName((String) userMap.get("name"));
            user.setOpName((String) userMap.get("opName"));
            user.setSessionStatus((String) userMap.get("sessionStatus"));
            user.setUserType((String) userMap.get("userType"));
        }
        return user;
    }

    /**
     * 获取当前登录用户
     *
     * @return
     * @throws CasServiceException 
     */
    @SuppressWarnings("unchecked")
    public synchronized static User get() throws CasServiceException {

        String token = currentToken.get();
        if (token == null || token.trim().length() == 0) {
            return null;
        }

        User user = currentUser.get();
        if (user == null) {
            VfSsoClientConfig conf = VfSsoClientConfig.getDefaultConfig();
            if (httpClient == null) {
                httpClient = HttpClientFactory.factory(conf);
            }

            //从session服务中获取
            StringBuffer bufUrl = new StringBuffer();
            bufUrl.append(conf.getApiConfig().buildSessionUrl(token));
            bufUrl.append("?sysCode=").append(getCurrentApp(conf));
            bufUrl.append("&timestamp=").append(new Date().getTime());

            HttpGet get = new HttpGet(bufUrl.toString());

            if (logger.isDebugEnabled()) {
                logger.debug("调用SessionService,url = {}", bufUrl.toString());
            }

            try {
                String resp = httpClient.execute(get, new BasicResponseHandler());
                if (logger.isDebugEnabled()) {
                    logger.debug("session 服务返回：{}", resp);
                }
                Map<String, Object> respMap = JsonConverter.me().readValue(resp, Map.class);
                String code = (String) respMap.get("code");

                if ("success".equals(code)) {
                    Map<String, Object> userMap = (Map<String, Object>) respMap.get("data");

                    user = converterUser(userMap);

                    currentUser.set(user);
                } else {
                    Object msg = respMap.get("msg");
                    logger.error("获取用户服务端异常：code = {}, msg = {}", new Object[] { code, msg });
                    throw new CasServiceException("SSO服务异常"
                                                  + (logger.isDebugEnabled() ? ",getExt:" + msg
                                                      : ""));
                }
            } catch (ClientProtocolException e) {
                logger.error("api:session", e);
                throw new CasServiceException("SSO服务异常"
                                              + (logger.isDebugEnabled() ? ",getExt:"
                                                                           + e.getMessage() : ""));
            } catch (IOException e) {
                logger.error("api:session", e);
                throw new CasServiceException("SSO服务异常"
                                              + (logger.isDebugEnabled() ? ",getExt:"
                                                                           + e.getMessage() : ""));
            }
        }
        return user;
    }

    @SuppressWarnings("unchecked")
    public synchronized static SeExt getExt() throws CasServiceException {

        String token = currentToken.get();
        if (token == null || token.trim().length() == 0) {
            return null;
        }

        SeExt ext = userExt.get();
        if (ext == null) {
            VfSsoClientConfig conf = VfSsoClientConfig.getDefaultConfig();
            if (httpClient == null) {
                httpClient = HttpClientFactory.factory(conf);
            }

            //从session服务中获取
            StringBuffer bufUrl = new StringBuffer();
            bufUrl.append(conf.getApiConfig().buildSessionExtUrl()).append(token);
            bufUrl.append("?sysCode=").append(getCurrentApp(conf));
            bufUrl.append("&timestamp=").append(new Date().getTime());

            HttpGet get = new HttpGet(bufUrl.toString());

            if (logger.isDebugEnabled()) {
                logger.debug("调用SessionExt,url = {}", bufUrl.toString());
            }

            try {
                String resp = httpClient.execute(get, new BasicResponseHandler());
                if (logger.isDebugEnabled()) {
                    logger.debug("session 服务返回：{}", resp);
                }
                Map<String, Object> respMap = JsonConverter.me().readValue(resp, Map.class);
                String code = (String) respMap.get("code");

                if ("success".equals(code)) {
                    Map<String, Object> extMap = (Map<String, Object>) respMap.get("data");
                    ext = new SeExt();
                    ext.putAll(extMap);
                    userExt.set(ext);
                } else {
                    Object msg = respMap.get("msg");
                    logger.error("获取会话扩展服务端异常：code = {}, msg = {}", new Object[] { code, msg });
                    throw new CasServiceException("SSO服务异常"
                                                  + (logger.isDebugEnabled() ? ",getExt:" + msg
                                                      : ""));
                }
            } catch (ClientProtocolException e) {
                logger.error("api:session ext", e);
                throw new CasServiceException("SSO服务异常"
                                              + (logger.isDebugEnabled() ? ",getExt:"
                                                                           + e.getMessage() : ""));
            } catch (IOException e) {
                logger.error("api:session ext", e);
                throw new CasServiceException("SSO服务异常"
                                              + (logger.isDebugEnabled() ? ",getExt:"
                                                                           + e.getMessage() : ""));
            }
        }
        return ext;
    }

    /**
     * 向服务器更新扩展信息
     * @return
     * @throws CasServiceException 
     */
    @SuppressWarnings("unchecked")
    public synchronized static boolean updateExt() throws CasServiceException {

        String token = currentToken.get();
        if (token == null || token.trim().length() == 0) {
            logger.warn("api:update session ext, no token.");
            return false;
        }

        SeExt ext = userExt.get();
        if (ext.isModified()) {
            VfSsoClientConfig conf = VfSsoClientConfig.getDefaultConfig();
            if (httpClient == null) {
                httpClient = HttpClientFactory.factory(conf);
            }

            //从session服务中获取
            StringBuffer bufUrl = new StringBuffer();
            bufUrl.append(conf.getApiConfig().buildSessionExtUrl()).append(token);

            HttpPut put = new HttpPut(bufUrl.toString());

            if (logger.isDebugEnabled()) {
                logger.debug("Upd调用SessionExt,url = {}", bufUrl.toString());
            }

            try {
                List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
                // 设置参数
                parameters.add(new BasicNameValuePair("sysCode", VfSsoUser.getCurrentApp(conf)));
                parameters.add(new BasicNameValuePair("ext", JsonConverter.me().writeValueAsString(
                    ext)));

                UrlEncodedFormEntity entity;
                entity = new UrlEncodedFormEntity(parameters);
                entity.setContentEncoding("utf-8");
                put.setEntity(entity);
                String resp = httpClient.execute(put, new BasicResponseHandler());
                if (logger.isDebugEnabled()) {
                    logger.debug("upd session ext服务返回：{}", resp);
                }
                Map<String, Object> respMap = JsonConverter.me().readValue(resp, Map.class);
                String code = (String) respMap.get("code");

                if (!"success".equals(code)) {
                    Object msg = respMap.get("msg");
                    logger.error("更新会话扩展服务端异常：code = {}, msg = {}", new Object[] { code, msg });
                    throw new CasServiceException("SSO服务异常"
                                                  + (logger.isDebugEnabled() ? ",updExt:" + msg
                                                      : ""));
                }
                return true;
            } catch (UnsupportedEncodingException e) {
                //ignore
            } catch (ClientProtocolException e) {
                logger.error("api:upd session ext", e);
                throw new CasServiceException("SSO服务异常"
                                              + (logger.isDebugEnabled() ? ",updExt:"
                                                                           + e.getMessage() : ""));
            } catch (IOException e) {
                logger.error("api:upd session ext", e);
                throw new CasServiceException("SSO服务异常"
                                              + (logger.isDebugEnabled() ? ",updExt:"
                                                                           + e.getMessage() : ""));
            }
        }
        return false;
    }
}
