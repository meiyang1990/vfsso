/**
 * 
 */
package com.netfinworks.vfsso.client.common;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;

/**
 * 统一HttpClient工厂
 * @author bigknife
 *
 */
public class HttpClientFactory {
	/**
	 * 根据配置信息生成一个HttpClient实例
	 * @param conf
	 * @return
	 */
	public static HttpClient factory(VfSsoClientConfig conf){
		ClientConnectionManager connectionManager = new PoolingClientConnectionManager();
		HttpClient httpClient = new DefaultHttpClient(connectionManager);
		httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT,
				conf.getHttpClientSoTimeout());
		httpClient.getParams().setIntParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT,
				conf.getHttpClientConnectionTimeout());
		
		return httpClient;
	}
}
