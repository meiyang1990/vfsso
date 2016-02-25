/**
 * 
 */
package com.netfinworks.vfsso.ulogin.common;

import java.util.HashMap;
import java.util.Map;

public class ResourceResponse<T> {
	private String code;
	private String msg;
	private T data;
	private Map<String, Object> ext;

	public static <T> ResourceResponse<T> successful() {
		return successful(null);
	}

	public static <T> ResourceResponse<T> successful(T data) {
		ResourceResponse<T> resp = new ResourceResponse<T>();
		resp.setCode(ResultKind.SUCCESS.getCode());
		resp.setMsg(ResultKind.SUCCESS.getMessage());
		if (data != null) {
			resp.setData(data);
		}
		return resp;
	}

	public static <T> ResourceResponse<T> newInstance(String code,
			String message) {
		ResourceResponse<T> resp = new ResourceResponse<T>();
		resp.setCode(code);
		resp.setMsg(message);
		return resp;
	}

	public static <T> ResourceResponse<T> newInstance(ResultKind resultKind) {
		ResourceResponse<T> resp = new ResourceResponse<T>();
		resp.setCode(resultKind.getCode());
		resp.setMsg(resultKind.getMessage());
		return resp;
	}

	/**
	 * 读取业务响应参数
	 * 
	 * @param key
	 * @return
	 */
	public Object getExt(String key) {
		if (ext != null) {
			return ext.get(key);
		}
		return null;
	}

	/**
	 * @return the responseParam
	 */
	public Map<String, Object> getExt() {
		return ext;
	}

	/**
	 * @return the resultCode
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param resultCode
	 *            the resultCode to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the resultMsg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param resultMsg
	 *            the resultMsg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 
	 * @param result
	 */
	public void setResultKind(ResultKind result) {
		if (result != null) {
			this.setCode(result.getCode());
			this.setMsg(result.getMessage());
		}
	}

	/**
	 * @param ex
	 */
	public void setResultException(Exception ex) {
		this.setResultKind(ResultKind.EXCEPTION);
		if (ex != null) {
			this.msg += ":" + ex.getMessage();
		}
	}

	/**
	 * 
	 * @return
	 */
	public final T getData() {
		return data;
	}

	/**
	 * 
	 * @param data
	 */
	public final void setData(T data) {
		this.data = data;
	}

	/**
	 * @param key
	 * @param value
	 */
	public void addExt(String key, Object value) {
		if (this.ext == null) {
			this.ext = new HashMap<String, Object>();
		}
		this.ext.put(key, value);
	}
}
