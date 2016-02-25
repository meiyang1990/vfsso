/**
 * 
 */
package com.netfinworks.vfsso.ulogin.resource;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.netfinworks.rest.annotation.Body;
import com.netfinworks.rest.annotation.CookieParam;
import com.netfinworks.rest.annotation.QueryParam;
import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.rest.util.UrlMatchKind;
import com.netfinworks.vfsso.cas.VfSsoConstants;
import com.netfinworks.vfsso.cas.domain.LoginContext;
import com.netfinworks.vfsso.cas.exception.LoginAuthException;
import com.netfinworks.vfsso.cas.spec.ICaptchaProvider;
import com.netfinworks.vfsso.cas.spec.ILoginAuth;
import com.netfinworks.vfsso.common.util.TokenUtil;
import com.netfinworks.vfsso.domain.SsoUser;
import com.netfinworks.vfsso.session.IVfSsoSession;
import com.netfinworks.vfsso.session.enums.SessionStatusKind;
import com.netfinworks.vfsso.session.exceptions.SessionException;
import com.netfinworks.vfsso.ulogin.common.FailTimeCookie;
import com.netfinworks.vfsso.ulogin.common.LoginRequest;
import com.netfinworks.vfsso.ulogin.common.LoginResponse;
import com.netfinworks.vfsso.ulogin.common.ResBase;
import com.netfinworks.vfsso.ulogin.common.ResourceResponse;
import com.netfinworks.vfsso.ulogin.common.ResultKind;
import com.netfinworks.vfsso.ulogin.common.VfSsoCookie;

/**
 * 登录资源
 * 
 * @author bigknife
 * 
 */
@WebResource(url = "/login", matchKind = UrlMatchKind.Cautious)
@Component
public class LoginRes extends ResBase {
    private static final String    REGEX_VFSSO_TOKEN = "([?]|&)("
                                                       + VfSsoConstants.CAS_TOKEN_COOKIE_NAME
                                                       + "|timestamp)=[^&=]+$|("
                                                       + VfSsoConstants.CAS_TOKEN_COOKIE_NAME
                                                       + "|timestamp)=[^&=]+&";
    @Resource(name = "vfsso.login.auth")
    private ILoginAuth<SsoUser>    loginAuth;

    @Resource(name = "vfsso.login.captcha")
    private ICaptchaProvider       captchaProvider;

    @Resource(name = "vfsso.session.service")
    private IVfSsoSession<SsoUser> userSessionService;

    @Verb(HttpVerb.GET)
    @Render(renderRef = "loginVelocityRender")
    public ResourceResponse<LoginPage> init(@QueryParam(name = "returnUrl") String returnUrl,
                                            @CookieParam(name = VfSsoConstants.CAS_TOKEN_COOKIE_NAME) String token) {
        if (token != null) {
            SsoUser usr;
            try {
                usr = userSessionService.get(token);
            } catch (SessionException e) {
                return ResourceResponse.newInstance(ResultKind.EXCEPTION.getCode(), "会话服务异常，请重试");
            }
            if (usr != null) {
                boolean logined = SessionStatusKind.login.equals(usr.getSessionStatus());
                LoginPage pageResult = buildLoginedPage(token, returnUrl, logined);
                pageResult.setUser(usr);
                pageResult.setLogined(logined
                                      || SessionStatusKind.pending.equals(usr.getSessionStatus()));
                return ResourceResponse.successful(pageResult);
            } else {
                VfSsoCookie.removeCookie();
            }
        }
        ResourceResponse<LoginPage> resp = ResourceResponse.successful(buildLoginPage(returnUrl));
        return resp;
    }

    private LoginPage buildLoginedPage(String token, String returnUrl, boolean forward) {
        LoginPage ret = new LoginPage();
        if (returnUrl != null && returnUrl.length() > 10) {
            ret.setReturnUrl(getForwardUrl(removeUnuseParam(returnUrl),
                VfSsoConstants.CAS_TOKEN_COOKIE_NAME, token));
            ret.setForward(forward);
        }
        return ret;
    }

    private String removeUnuseParam(String returnUrl) {
        String ret = null;
        if (returnUrl != null) {
            ret = returnUrl.replaceAll(REGEX_VFSSO_TOKEN, "");
            ret = ret.replaceAll(REGEX_VFSSO_TOKEN, "");
        }
        return ret;
    }

    private LoginPage buildLoginPage(String returnUrl) {
        LoginPage page = new LoginPage();

        if (captchaProvider != null) {
            String requestId = TokenUtil.genUUID();
            page.setRequestId(requestId);
            page.setCaptchaUrl(captchaProvider.genCaptchaUrl(requestId));
        }

        page.setReturnUrl(removeUnuseParam(returnUrl));

        return page;
    }

    @Verb(HttpVerb.POST)
    @Render(renderRef = "loginRender")
    public ResourceResponse<LoginResponse> login(@Body LoginRequest loginRequest) {
        LoginResponse response = new LoginResponse();
        ResourceResponse<LoginResponse> rr = ResourceResponse.successful(response);

        try {
            // 0. 参数合法性判断
            if (!validLoginRequest(loginRequest, rr)) {
                return rr;
            }

            // 1. 验证码是否正确
            boolean captchaCorrect = validCaptcha(loginRequest, rr);
            if (!captchaCorrect) {
                return rr;
            }

            // 2. 登录
            boolean adLoginCorrect = validLogin(loginRequest, rr);
            if (!adLoginCorrect) {
                return rr;
            }
            //TODO: update user last login
            response.setReturnUrl(loginRequest.getReturnUrl());
        } finally {
            //TODO 记录日志
        }
        return rr;
    }

    /**
     * @param loginCallbackUrl
     * @param token
     * @return
     */
    private String getForwardUrl(String loginCallbackUrl, String tokenName, String token) {
        StringBuilder url = new StringBuilder();
        url.append(loginCallbackUrl);
        url.append(loginCallbackUrl.indexOf("?") >= 0 ? "&" : "?");
        url.append(tokenName);
        url.append("=");
        url.append(token);
        url.append("&timestamp=");
        url.append(new Date().getTime());
        return url.toString();
    }

    private boolean validLoginRequest(LoginRequest loginRequest, ResourceResponse<LoginResponse> rr) {
        if (!StringUtils.hasText(loginRequest.getLoginName())) {
            rr.setCode(ResultKind.FAILED.getCode());
            rr.setMsg("登录名不能为空");
            return false;
        }
        if (!StringUtils.hasText(loginRequest.getPassword())) {
            rr.setCode(ResultKind.FAILED.getCode());
            rr.setMsg("密码不能为空");
            return false;
        }
        return true;
    }

    /**
     * 验证登录
     * 
     * @param loginRequest
     * @param rr
     * @return
     */
    private boolean validLogin(LoginRequest loginRequest, ResourceResponse<LoginResponse> rr) {
        String user = loginRequest.getLoginName();
        String password = loginRequest.getPassword();

        if (getLogger().isDebugEnabled()) {
            getLogger().debug("{}/{} attempt to login in AD", new Object[] { user, password });
        }

        boolean result = false;
        try {
            LoginContext<SsoUser> ctx = new LoginContext<SsoUser>(user, password,
                loginRequest.getDynamicCode());
            result = loginAuth.auth(ctx);
            if (!result) {
                rr.setCode(ResultKind.FAILED.getCode());
                rr.setMsg("登录错误，请检查用户、密码或蜜宝是否正确！");
            } else {
                String token = userSessionService.create(ctx.getUser());
                if (token != null) {
                    rr.getData().setToken(token);
                    rr.getData().setUser(ctx.getUser());
                } else {
                    result = false;
                }
            }
        } catch (LoginAuthException ex) {
            if (ex.getCause() != null) {
                getLogger().error("登录验证异常", ex);
            }
            rr.setCode(ResultKind.FAILED.getCode());
            rr.setMsg(ex.getMessage());
        } catch (SessionException e) {
            if (e.getCause() != null) {
                getLogger().error("Session服务异常", e);
            }
            rr.setCode(ResultKind.FAILED.getCode());
            rr.setMsg(e.getMessage());
        } catch (Throwable ex) {
            getLogger().error("登录异常", ex);
            rr.setCode(ResultKind.EXCEPTION.getCode());
            rr.setMsg("登录异常，请再次重试或联系管理员！");
        }
        return result;
    }

    /**
     * 验证验证码
     * 
     * @param captcha
     * @param password
     * @return
     */
    private boolean validCaptcha(LoginRequest loginRequest, ResourceResponse<LoginResponse> rr) {
        if (FailTimeCookie.getFailCount() <= 3) {
            return true;
        }
        if (!StringUtils.hasText(loginRequest.getCaptcha())) {
            rr.setCode(ResultKind.FAILED.getCode());
            rr.setMsg("验证码不能为空");
            return false;
        }

        if (captchaProvider != null) {
            try {
                return captchaProvider.validateCaptcha(loginRequest.getRequestId(),
                    loginRequest.getCaptcha());
            } catch (Exception e) {
                getLogger().error("验证码服务出错", e);
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    public static class LoginPage {
        private String  captchaUrl;
        private String  requestId;
        private String  returnUrl;
        private String  sysCode;
        private boolean isLogined;
        private boolean isForward;
        private SsoUser user;

        /**
         * @return the sysCode
         */
        public String getSysCode() {
            return sysCode;
        }

        /**
         * @param sysCode the sysCode to set
         */
        public void setSysCode(String sysCode) {
            this.sysCode = sysCode;
        }

        /**
         * @return the returnUrl
         */
        public String getReturnUrl() {
            return returnUrl;
        }

        /**
         * @param returnUrl the returnUrl to set
         */
        public void setReturnUrl(String returnUrl) {
            this.returnUrl = returnUrl;
        }

        /**
         * @return the requestId
         */
        public String getRequestId() {
            return requestId;
        }

        /**
         * @param requestId the requestId to set
         */
        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        /**
         * @return the captchaUrl
         */
        public String getCaptchaUrl() {
            return captchaUrl;
        }

        /**
         * @param captchaUrl the captchaUrl to set
         */
        public void setCaptchaUrl(String captchaUrl) {
            this.captchaUrl = captchaUrl;
        }

        /**
         * @return the isLogined
         */
        public boolean isLogined() {
            return isLogined;
        }

        /**
         * @param isLogined the isLogined to set
         */
        public void setLogined(boolean isLogined) {
            this.isLogined = isLogined;
        }

        /**
         * @return the isForward
         */
        public boolean isForward() {
            return isForward;
        }

        /**
         * @param isForward the isForward to set
         */
        public void setForward(boolean isForward) {
            this.isForward = isForward;
        }

        /**
         * @return the user
         */
        public SsoUser getUser() {
            return user;
        }

        /**
         * @param user the user to set
         */
        public void setUser(SsoUser user) {
            this.user = user;
        }
    }
}
