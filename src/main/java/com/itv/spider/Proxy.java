package com.itv.spider;

/**
 * http代理
 * 
 * @author xiajun
 * 
 */
public class Proxy {
	private int proxyPort;
	private String proxyHost;
	private boolean isProxy;

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public boolean isProxy() {
		return isProxy;
	}

	public void setIsProxy(boolean isProxy) {
		this.isProxy = isProxy;
	}

}
