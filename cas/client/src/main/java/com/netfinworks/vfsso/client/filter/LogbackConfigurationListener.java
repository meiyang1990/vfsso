/**
 * 
 */
package com.netfinworks.vfsso.client.filter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Logback initial listener</p>
 * @author huipeng
 * @version $Id: LogbackConfigurationListener.java, v 0.1 Jun 4, 2014 1:32:04 PM knico Exp $
 */
public class LogbackConfigurationListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String logbackConf = sce.getServletContext().getInitParameter("logbackConfiguration");
		System.setProperty("logback.configurationFile", logbackConf);
		Logger logger = LoggerFactory.getLogger(getClass());
		logger.info("logback配置文件指定为：{}",new String[]{logbackConf} );
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
