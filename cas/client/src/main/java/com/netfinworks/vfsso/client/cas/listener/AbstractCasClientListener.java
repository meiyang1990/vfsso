/**
 * 
 */
package com.netfinworks.vfsso.client.cas.listener;

import com.netfinworks.vfsso.client.common.CasEvent;
import com.netfinworks.vfsso.client.core.IVfSsoCasListener;

/**
 * @author bigknife
 *
 */
public abstract class AbstractCasClientListener implements
		IVfSsoCasListener {
	@Override
	public void onEvent(CasEvent event) {
		switch(event.getType()){
		case AUTH_EXCEPTION:
			handleExceptionEvent(event);
			break;
		case AUTH_FORBIDDEN:
			handleForbiddenEvent(event);
			break;
		case AUTH_NOT_LOGIN:
			handleNotLoginEvent(event);
			break;
		}
	}

	protected abstract void handleExceptionEvent(CasEvent event);
	protected abstract void handleForbiddenEvent(CasEvent event);
	protected abstract void handleNotLoginEvent(CasEvent event);
	
}
