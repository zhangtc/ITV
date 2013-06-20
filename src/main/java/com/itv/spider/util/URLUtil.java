package com.itv.spider.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

/**
 * 存放已经爬取过的url，防止url回环
 * 
 * @author xiajun
 * 
 */
public class URLUtil {
	//private final static Logger log = Logger.getLogger(URLUtil.class);
	/**
	 * 存放已经访问过的url
	 */
	public final static List<String> staleURL = new CopyOnWriteArrayList<String>();
	/**
	 * url 白名单
	 */
	public final static List<String> blackList = new ArrayList<String>();
	/**
	 * url黑名单
	 */
	public final static List<String> whiteList = new ArrayList<String>();

	static {
		URLUtil.staleURL.add(URLUtil.completionUrl("http://v.360.cn/m/hKbiZkj6R0r7Tx.html"));
		URLUtil.staleURL.add(URLUtil.completionUrl("http://v.360.cn/m/faTiZUUvRHT3Sx.html"));
		URLUtil.staleURL.add(URLUtil.completionUrl("http://v.360.cn/m/g6XjZ0X7R0L4SR.html"));//

	}

	/**
	 * 检查url是否以/结束，并转换成小写
	 * 
	 * @param url
	 * @return
	 */
	public final static String completionUrl(String url) {
		if (url == null) {
			throw new NullPointerException("参数url不能为空.");
		}
		int last = url.lastIndexOf("/");
		if (last != url.length() - 1) {
			url = url + "/";
		}
		return url.toLowerCase();
	}

	/**
	 * 替换特殊字符
	 * 
	 * @return
	 */
	public final static String specialCharURL(String url) {
		return url.replaceAll("\\|", "%7C");
	}

	/**
	 * unicode 转中文
	 * 
	 * @param dataStr
	 * @return
	 */
	public static String decodeUnicode(String dataStr) {
		if (dataStr.equals("")||dataStr.indexOf("\\u") < 0) {
			return dataStr;
		}
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		int i=dataStr.indexOf("\\u");
		if(i > 0){
			buffer.append(dataStr.substring(0,i));
			dataStr=dataStr.substring(i);
		}
		while (start > -1) {
			end = dataStr.indexOf("\\u", start + 2);
			String charStr = "";
			if (end == -1) {
				charStr = dataStr.substring(start + 2, start + 2 + 4);
			} else {
				charStr = dataStr.substring(start + 2, end);
			}
			char letter = (char) Integer.parseInt(charStr, 16);
			buffer.append(new Character(letter).toString());
			start = end;
		}
		return buffer.toString();
	}
}
