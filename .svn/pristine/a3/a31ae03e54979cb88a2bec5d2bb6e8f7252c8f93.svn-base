/**
 * 
 */
package com.netfinworks.vfsso.client.cas.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.netfinworks.vfsso.client.cas.CasResult;

/**
 * 审计信息
 * @author bigknife
 *
 */
public class Audit {
	private String url;
	private String systemCode;
	private String method;
	private String token;
	private CasResult authResult;
	private Date requestTime;
	private Date responseTime;
	private List<AuditInfo> infoList;
	
	public void addInfo(String info){
		if(infoList == null){
			infoList = new ArrayList<AuditInfo>();
		}
		infoList.add(new AuditInfo(info));
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the systemCode
	 */
	public String getSystemCode() {
		return systemCode;
	}
	
	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * @param systemCode the systemCode to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	/**
	 * @return the authResult
	 */
	public CasResult getAuthResult() {
		return authResult;
	}
	/**
	 * @param authResult the authResult to set
	 */
	public void setAuthResult(CasResult authResult) {
		this.authResult = authResult;
	}
	/**
	 * @return the requestTime
	 */
	public Date getRequestTime() {
		return requestTime;
	}
	/**
	 * @param requestTime the requestTime to set
	 */
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	/**
	 * @return the responseTime
	 */
	public Date getResponseTime() {
		return responseTime;
	}
	/**
	 * @param responseTime the responseTime to set
	 */
	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}
	/**
	 * @return the infoList
	 */
	public List<AuditInfo> getInfoList() {
		return infoList;
	}
	
	public static class AuditInfo{
		private String info;
		private Date infoTime;
		public AuditInfo(String info){
			this.info = info;
			this.infoTime = new Date();
		}
		/**
		 * @return the info
		 */
		public String getInfo() {
			return info;
		}
		/**
		 * @param info the info to set
		 */
		public void setInfo(String info) {
			this.info = info;
		}
		/**
		 * @return the infoTime
		 */
		public Date getInfoTime() {
			return infoTime;
		}
		/**
		 * @param infoTime the infoTime to set
		 */
		public void setInfoTime(Date infoTime) {
			this.infoTime = infoTime;
		}
		
		
	}
	
}
