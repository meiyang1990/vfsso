/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.vfsso.client.core;

import com.netfinworks.vfsso.client.common.CasEvent;

/**
 * <p>VF SSO CAS 过滤器事件</p>
 * @author huipeng
 * @version $Id: IVfSsoCasListener.java, v 0.1 Jun 4, 2014 2:13:16 PM knico Exp $
 */
public interface IVfSsoCasListener {
    void onEvent(CasEvent event);

}
