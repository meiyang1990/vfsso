/**
 * 
 */
package com.netfinworks.vfsso.client.cas;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;

import com.netfinworks.vfsso.client.authapi.VfSsoUser;
import com.netfinworks.vfsso.client.cas.domain.Auth;
import com.netfinworks.vfsso.client.cas.domain.AuthResp;
import com.netfinworks.vfsso.client.common.JsonConverter;
import com.netfinworks.vfsso.client.common.VfSsoClientConfig;
import com.netfinworks.vfsso.client.common.VfSsoClientLogger;

/**
 * <p>鉴权服务调用</p>
 * @author huipeng
 * @version $Id: AuthService.java, v 0.1 Jun 12, 2014 3:04:10 PM knico Exp $
 */
public class AuthService {
    private static Logger logger = VfSsoClientLogger.getLogger();

    public static CasResult doAuth(HttpClient httpClient, VfSsoClientConfig config, Auth auth) {
        HttpPost post = new HttpPost(config.getApiConfig().buildAuthUrl());

        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        // 设置参数
        parameters.add(new BasicNameValuePair("app", VfSsoUser.getCurrentApp(config)));
        parameters.add(new BasicNameValuePair("token", auth.getToken()));
        parameters.add(new BasicNameValuePair("style", auth.getStyle()));
        parameters.add(new BasicNameValuePair("methodName", auth.getMethodName()));
        parameters.add(new BasicNameValuePair("method", auth.getMethod()));
        parameters.add(new BasicNameValuePair("url", auth.getUrl()));

        try {
            UrlEncodedFormEntity entity;
            entity = new UrlEncodedFormEntity(parameters);
            entity.setContentEncoding("utf-8");
            post.setEntity(entity);
        } catch (UnsupportedEncodingException e) {
            // ignore
        }

        CasResult ret;
        try {
            String resp = httpClient.execute(post, new BasicResponseHandler());
            AuthResp ar = JsonConverter.me().readValue(resp, AuthResp.class);
            ret = CasResult.getByCode(ar.getCode());
            if (ret == null) {
                throw new Exception("未知返回码：" + ar.getCode() + "," + ar.getMsg());
            }
            if (ar.getUser() != null) {
                VfSsoUser.setCurrentUser(ar.getUser());
            }
            if (CasResult.EXCEPTION.equals(ret)) {
                logger.error("鉴权服务内部异常:{}", new Object[] { ar.getMsg() });
            }
        } catch (Exception e) {
            logger.error("鉴权服务调用异常; 无法鉴权。服务Url=" + config.getApiConfig().buildAuthUrl(), e);
            ret = CasResult.EXCEPTION;
        }
        return ret;
    }
}
