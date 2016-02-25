/**
 *
 */
package com.netfinworks.vfsso.client.authapi.service;

import java.util.List;

import com.netfinworks.vfsso.client.authapi.domain.User;

/**
 * @author knico
 * @since Nov 1, 2012
 *
 */
public class UsersResult {
	private String code;
	private String msg;
	private List<User> data;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the data
	 */
	public List<User> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<User> data) {
		this.data = data;
	}

	/**
	 * @return
	 */
	public boolean isSuccess() {
		return "success".equals(code);
	}

	/**
	 * @return
	 */
	public boolean isNoResource() {
		return "no_resource".equals(code);
	}
}
