/**
 * 
 */
package com.netfinworks.vfsso.client.authapi.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bigknife
 * 
 */
public class Menu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7452387935350203993L;
	private String id;
	private String code;
	private String name;
	private String url;
	private String icon;
	private String method;
	private String methodName;
	private int order;
	private boolean leaf;

	private List<Menu> subMenus;

	public Menu() {
	}

	public Menu(String name, String url, String icon, boolean leaf) {
		super();
		this.name = name;
		this.url = url;
		this.icon = icon;
		this.leaf = leaf;
	}

	/**
	 * @return the leaf
	 */
	public boolean isLeaf() {
		return leaf;
	}

	/**
	 * @param leaf the leaf to set
	 */
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void addSubMenu(Menu menu) {
		if (subMenus == null) {
			this.subMenus = new ArrayList<Menu>();
		}
		this.subMenus.add(menu);
	}

	public void removeSubMenu(Menu menu) {
		if (subMenus != null) {
			subMenus.remove(menu);
		}
	}

	/**
	 * @return the subMenus
	 */
	public List<Menu> getSubMenus() {
		return subMenus;
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
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
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
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}
}
