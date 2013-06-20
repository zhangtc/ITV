package com.itv.spider.httpclient;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

import com.itv.spider.Proxy;

public class HttpClientPool {
	private static PoolingClientConnectionManager cm=null;
	static{
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		 schemeRegistry.register(
		          new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		 schemeRegistry.register(
		          new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

		 cm = new PoolingClientConnectionManager(schemeRegistry);
		 cm.setMaxTotal(200);
		 cm.setDefaultMaxPerRoute(20);
		 HttpHost localhost = new HttpHost("locahost", 80);
		 cm.setMaxPerRoute(new HttpRoute(localhost), 50);
	}
	/**
	 * 获取httpclient实例
	 * @param proxyConf 判断是否需要代理 以及代理的端口和地址
	 * @return
	 */
	public static HttpClient getHttpClient(Proxy proxyConf){
		HttpClient httpClient = new DefaultHttpClient(cm);
		if(proxyConf!=null&&proxyConf.isProxy()){
			HttpHost proxy = new HttpHost(proxyConf.getProxyHost(), proxyConf.getProxyPort());
			httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		return httpClient;
	}
}
