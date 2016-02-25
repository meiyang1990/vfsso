/**
 * 
 */
package com.netfinworks.vfsso.client.filter;

import com.netfinworks.vfsso.client.cas.domain.Audit;

/**
 * 审计上下文信息
 * @author bigknife
 *
 */
public class AuditContext {
	private static ThreadLocal<Audit> auditTl = new ThreadLocal<Audit>();
	
	static void setAudit(Audit audit){
		auditTl.set(audit);
	}
	
	static Audit getAudit(){
		return auditTl.get();
	}
	
	static void removeAudit(){
		auditTl.remove();
	}
	/**
	 * 添加审计业务信息
	 * @param info
	 */
	public static boolean addAuditInfo(String info){
	    if(getAudit() != null){
	        getAudit().addInfo(info);
	        return true;
	    }
	    return false;
	}
}
