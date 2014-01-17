package com.itv.spider.httpclient;

import java.nio.charset.Charset;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import com.itv.spider.Proxy;
import com.itv.spider.exception.ReuseUrlException;
import com.itv.spider.util.EntityCharsetUtils;
import com.itv.spider.util.FileReadUtil;
import com.itv.spider.util.URLUtil;
/**
 * 获取网页的内容
 * @author xiajun
 *
 */
public final class PageStream {
	private final static Logger log=Logger.getLogger(PageStream.class);
	/**
	 * 获取网页的内容并转成string类型 
	 * @param url 网页的url
	 * @param proxy 代理对象 不需要代理参数可无空
	 * @return String 网页内容
	 * @throws ReuseUrlException 当url被再次使用时会抛出此异常
	 */
	public final static String getPageString(String url,Charset charset,Proxy proxy) throws ReuseUrlException {
		if(checkUrlEmploy(url)){
			//throw new ReuseUrlException(url);
			log.error(url+ "重复使用.");
			return null;
		}
		log.debug("开始请求url: "+url);
		URLUtil.staleURL.add(URLUtil.completionUrl(url));
		HttpGet httpget = new HttpGet(url);
		httpget.setHeaders(getHeader());
		HttpClient httpClient = HttpClientPool.getHttpClient(proxy);
		HttpResponse response = null;
		String info_=null;
		try {
			response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String info = EntityCharsetUtils.toString(entity,charset);
				info_ = info.replaceAll("\n", "");
			}
		} catch (Exception e) {
			log.error("获取网页内容时出现异常."+url,e);
			URLUtil.staleURL.remove(URLUtil.completionUrl(url));
		}
		return info_;
	}
	/**
	 * 检查url是否已经使用过
	 * @param url 
	 * @return boolean
	 */
	private static boolean checkUrlEmploy(String url){
		return URLUtil.staleURL.contains(URLUtil.completionUrl(url));
	}
	/**
	 * 返回请求头信息
	 */
	public static Header[] getHeader() {
		Header h1 = new BasicHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; rv:21.0) Gecko/20100101 Firefox/21.0");
		Header h2 = new BasicHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		// Header h3 = new
		// BasicHeader("Accept","	text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		Header h4 = new BasicHeader("Connection", "keep-alive");
		Header header[] = { h1, h2, h4 };
		return header;
	}
public static void main(String[] args) throws Exception {
	String in=PageStream.getPageString("http://tv.youku.com/search?ccat40178[a]=%E5%A4%A7%E9%99%86&m40177[cc-ms-q]=a%7Carea%3A%E5%A4%A7%E9%99%86", null,null);
	FileReadUtil.writeFile(in, "d:\\data\\sdf.txt");
}
}
