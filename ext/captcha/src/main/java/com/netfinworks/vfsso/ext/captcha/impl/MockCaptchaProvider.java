/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.ext.captcha.impl;

import com.netfinworks.vfsso.ext.captcha.VfSsoCaptchaBase;

/**
 * <p>模拟captcha</p>
 * @author huipeng
 * @version $Id: MockCaptchaProvider.java, v 0.1 Jun 10, 2014 6:02:05 PM knico Exp $
 */
public class MockCaptchaProvider extends VfSsoCaptchaBase {

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.cas.spec.ICaptchaProvider#genCaptchaUrl(java.lang.String)
     */
    @Override
    public String genCaptchaUrl(String token) {
        return "http://mock.captcha.vfsso/?token=" + token;
    }

    /* (non-Javadoc)
     * @see com.netfinworks.vfsso.cas.spec.ICaptchaProvider#validateCaptcha(java.lang.String, java.lang.String)
     */
    @Override
    public boolean validateCaptcha(String token, String answer) {
        getLogger().debug("Mock captcha service validate:{},{}", token, answer);
        return true;
    }

}
