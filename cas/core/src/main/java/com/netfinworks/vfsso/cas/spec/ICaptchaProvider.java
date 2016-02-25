/**
 * 
 */
package com.netfinworks.vfsso.cas.spec;

/**
 * <p>验证码提供服务接口</p>
 * @author huipeng
 * @version $Id: ICaptchaProvider.java, v 0.1 Jun 9, 2014 5:20:51 PM knico Exp $
 */
public interface ICaptchaProvider {

    /**
     * 生成验证码URL
     * @param token
     * @return
     */
    public String genCaptchaUrl(String token);

    /**
     * 验证验证码
     * @param token
     * @param answer
     * @return 
     */
    public boolean validateCaptcha(String token, String answer);
}
