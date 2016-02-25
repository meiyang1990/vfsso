/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.testapp.resources;

import org.springframework.stereotype.Component;

import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.rest.util.UrlMatchKind;
import com.netfinworks.restx.resp.CommonPage;

/**
 * <p>注释</p>
 * @author huipeng
 * @version $Id: IndexRes.java, v 0.1 Jun 18, 2014 1:28:38 PM knico Exp $
 */
@WebResource(url = "/", matchKind = UrlMatchKind.Cautious)
@Render(renderRef = "blankRender")
@Component
public class IndexRes {

    @Verb(HttpVerb.GET)
    public CommonPage<String> get() {
        CommonPage<String> ret = new CommonPage<String>();
        return ret;
    }
}
