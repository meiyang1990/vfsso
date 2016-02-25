/**
 * 
 */
package com.netfinworks.vfsso.auth.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.netfinworks.basis.inf.ucs.enhanced.EUCache;
import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.vfsso.cas.domain.ResourceResponse;
import com.netfinworks.vfsso.cas.domain.ResultKind;

/**
 * 
 * <p>权限缓存清除</p>
 * @author huipeng
 * @version $Id: CacheInvalidate.java, v 0.1 Jun 12, 2014 1:54:37 PM knico Exp $
 */
//@Component
@WebResource(url = "/cache/all")
@Render(renderRef = "jsonRender")
public class CacheInvalidate {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private EUCache<?> rolesCache;

	@Verb(HttpVerb.DELETE)
	public ResourceResponse flushCache() {
		ResourceResponse ret = new ResourceResponse();
		try {
			logger.info("Start clean roles' cache.");
			this.rolesCache.flush();
			logger.info("Success clean roles' cache.");
			ret.setResultKind(ResultKind.SUCCESS);
		} catch (Exception e) {
			logger.error("remove failed.", e);
			ret.setResultException(e.getMessage() == null ? e.getCause() : e);
		}
		return ret;
	}
}
