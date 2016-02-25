/**
 * 
 */
package com.netfinworks.vfsso.common.util;

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
    private int               soTimeoutMs, connectionTimeoutMs;
    private static HttpClient httpClient;

    private HttpClientFactory() {
    }

    /**
     * @return the soTimeoutMs
     */
    public int getSoTimeoutMs() {
        return soTimeoutMs;
    }

    /**
     * @param soTimeoutMs the soTimeoutMs to set
     */
    public void setSoTimeoutMs(int soTimeoutMs) {
        this.soTimeoutMs = soTimeoutMs;
    }

    /**
     * @return the connectionTimeoutMs
     */
    public int getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    /**
     * @param connectionTimeoutMs the connectionTimeoutMs to set
     */
    public void setConnectionTimeoutMs(int connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public HttpClient factory() {
        ClientConnectionManager connectionManager = new PoolingClientConnectionManager();
        httpClient = new DefaultHttpClient(connectionManager);
        httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeoutMs);
        httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
            connectionTimeoutMs);

        return httpClient;
    }

}
