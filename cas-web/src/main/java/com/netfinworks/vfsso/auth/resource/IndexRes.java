/**
 * 
 */
package com.netfinworks.vfsso.auth.resource;

import org.springframework.stereotype.Component;

import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.enums.HttpVerb;

/**
 * @author knico
 * 
 */
@Component
@WebResource(url = "/")
@Render(renderRef = "jsonRender")
public class IndexRes {
	@Verb(HttpVerb.GET)
	public String index() {
		return "VFSSO-CAS Server is Running.";
	}
}
