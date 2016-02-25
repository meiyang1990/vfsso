/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.test.cas;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import com.netfinworks.vfsso.client.authapi.VfSsoUser;
import com.netfinworks.vfsso.client.authapi.domain.User;
import com.netfinworks.vfsso.client.cas.AuthService;
import com.netfinworks.vfsso.client.cas.CasResult;
import com.netfinworks.vfsso.client.cas.domain.Auth;
import com.netfinworks.vfsso.client.cas.domain.LoginResp;
import com.netfinworks.vfsso.client.common.HttpClientFactory;
import com.netfinworks.vfsso.client.common.JsonConverter;
import com.netfinworks.vfsso.client.common.VfSsoClientConfig;
import com.netfinworks.vfsso.client.common.VfSsoClientConfigParser;
import com.netfinworks.vfsso.client.exception.CasServiceException;

/**
 * <p>注释</p>
 * @author huipeng
 * @version $Id: AuthTest.java, v 0.1 Jun 26, 2014 4:39:32 PM knico Exp $
 */
@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AuthTest {
    private static Logger            logger = LoggerFactory.getLogger(AuthTest.class);
    private static HttpClient        httpClient;
    private static VfSsoClientConfig config;

    @BeforeClass
    public static void init() {
        config = new VfSsoClientConfig();
        VfSsoClientConfigParser.overrideConfig("classpath:/dev-sso.properties", config);
        VfSsoClientConfig.init(config);
        httpClient = HttpClientFactory.factory(config);
    }

    @Test
    public void testVfssoUser() throws CasServiceException {
        VfSsoUser.setCurrentToken("454e8fb069444f1aa7d310085bd2d9bd");
        System.out.println(VfSsoUser.get());
    }

    @Test
    public void testForceIn() {
        System.out.println(doForceIn(httpClient, config, "6bf762ce1e27425e92e5813793deed09"));
    }

    @Test
    public void testLogin() {
        User user = new User();
        user.setId("200006558979");
        user.setOpId("7000006605817");
        user.setName("test");
        user.setLoginName("gogo");
        System.out.println(doLogin(httpClient, config, user));
    }

    @Test
    public void testAuth() throws CasServiceException {
        VfSsoUser.setCurrentToken("bf335e5966834ffe9eac011610dc808d");
        Auth auth = new Auth();
        auth.setStyle("REST");
        auth.setToken(VfSsoUser.getCurrentToken());
        auth.setUrl("audit/encharge");
        CasResult result = AuthService.doAuth(httpClient, config, auth);
        System.out.println(result + ":" + VfSsoUser.get().getOpId());
    }

    public static boolean doForceIn(HttpClient httpClient, VfSsoClientConfig config, String token) {
        String url = config.getApiConfig().buildSessionUrl(token);
        HttpPut put = new HttpPut(url);
        try {
            String resp = httpClient.execute(put, new BasicResponseHandler());
            @SuppressWarnings("unchecked")
            Map<String, Object> respMap = JsonConverter.me().readValue(resp, Map.class);
            String code = (String) respMap.get("code");

            if ("success".equals(code)) {
                System.out.println("result:" + respMap.get("data"));
                return (Boolean) respMap.get("data");
            } else {
                logger.error("强制登录失败:{}", new Object[] { respMap.get("code"), respMap.get("msg") });
            }
        } catch (Exception e) {
            logger.error("登录服务调用异常; 无法登录。服务Url=" + url, e);
        }
        return false;
    }

    public static CasResult doLogin(HttpClient httpClient, VfSsoClientConfig config, User user) {
        String loginUrl = config.getApiConfig().buildCasLoginUrl();
        HttpPost post = new HttpPost(loginUrl);

        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        // 设置参数
        parameters.add(new BasicNameValuePair("id", user.getId()));
        parameters.add(new BasicNameValuePair("loginName", user.getLoginName()));
        parameters.add(new BasicNameValuePair("name", user.getName()));
        if (user.getOpId() != null) {
            parameters.add(new BasicNameValuePair("opId", user.getOpId()));
        }
        if (user.getGroup() != null) {
            parameters.add(new BasicNameValuePair("group", user.getGroup()));
        }
        if (user.getExt() != null) {
            parameters.add(new BasicNameValuePair("ext", user.getExt()));
        }

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
            LoginResp lr = JsonConverter.me().readValue(resp, LoginResp.class);
            ret = CasResult.getByCode(lr.getCode());
            if (ret == null) {
                throw new Exception("未知返回码：" + lr.getCode() + "," + lr.getMsg());
            }
            if (CasResult.SUCCESS.equals(ret)) {
                ret = CasResult.getByCode(lr.getData().getStatus());
                if (ret == null) {
                    throw new Exception("未知登录状态：" + lr.getData().getStatus());
                }
                System.out.println("login, token:" + lr.getData().getToken());
            } else {
                logger.error("登录失败:{}", new Object[] { lr.getCode(), lr.getMsg() });
            }
        } catch (Exception e) {
            logger.error("登录服务调用异常; 无法登录。服务Url=" + loginUrl, e);
            ret = CasResult.EXCEPTION;
        }
        return ret;
    }
}
