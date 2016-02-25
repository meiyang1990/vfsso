/**
 * 
 */
package com.netfinworks.vfsso.ulogin.render;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.netfinworks.rest.filter.RawHttpHolder;
import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.filter.Response;
import com.netfinworks.rest.render.JsonRender;
import com.netfinworks.vfsso.domain.SsoUser;
import com.netfinworks.vfsso.ulogin.common.FailTimeCookie;
import com.netfinworks.vfsso.ulogin.common.VfSsoCookie;
import com.netfinworks.vfsso.ulogin.common.LoginResponse;
import com.netfinworks.vfsso.ulogin.common.ResourceResponse;
import com.netfinworks.vfsso.ulogin.common.ResultKind;

/**
 * @author bigknife
 * 
 */
public class LoginRender extends JsonRender {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private List<String> crossDomainList;
	private static final String split = "[,]";

	/**
	 * 设置跨域列表
	 * 
	 * @param crossDomains
	 */
	public void setCrossDomainList(String crossDomains) {
		if (StringUtils.hasText(crossDomains)) {
			String[] domains = crossDomains.split(split);
			crossDomainList = new ArrayList<String>();
			for (String domain : domains) {
				crossDomainList.add(domain.trim());
			}

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Response render(Object resultObject, Request request) {
		Response resp = super.render(resultObject, request);

		ResourceResponse<LoginResponse> rr = (ResourceResponse<LoginResponse>) resultObject;
		if (ResultKind.SUCCESS.getCode().equals(rr.getCode())) {
			LoginResponse loginResponse = rr.getData();
			SsoUser user = loginResponse.getUser();
			String token = loginResponse.getToken();
			// 写入cookie
			Cookie cookie = VfSsoCookie.makeCookie(token);
            RawHttpHolder.setCookie(cookie);
			FailTimeCookie.clearFailCount();
			logger.info("{}/{} 已经登录。", new Object[] { user.getLoginName(), user.getName() });
		} else {
			FailTimeCookie.increaseFailCount();
		}

		return resp;
	}
}
