package com.itv.spider;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.itv.spider.bean.MovieBean;
import com.itv.spider.exception.ReuseUrlException;
import com.itv.spider.httpclient.PageStream;
import com.itv.spider.s360.movie.MovieThreadFactory;
import com.itv.spider.xml.RegexXml;

/**
 * 线程池
 * @author xiajun
 *
 */
public abstract class AbstractSpider implements Runnable{
	private final static Logger log=Logger.getLogger(AbstractSpider.class);
	/**
	 * 阻塞队列用于存放movie对象便于批量插入数据库
	 */
	protected static final BlockingQueue<MovieBean> movieQueue=new LinkedBlockingQueue<MovieBean>(20480);
	/**
	 * 提供爬取页面的线程池
	 */
	public final static ExecutorService spiderPool = Executors.newFixedThreadPool(20,new MovieThreadFactory());
	private static Map<String, String> regex_map_360;
	protected static Proxy proxy=null;//http 代理类
	
	/**
	 * 获取正则表达式
	 */
	protected static void init(){
		regex_map_360=RegexXml.getRegexMap("360/regex_360.xml");
	}
	/**
	 * 获取正则表达式存放的map
	 * @return
	 */
	public static synchronized Map<String, String> getRegexMap(){
		if(regex_map_360==null){
			init();
		}
		return regex_map_360;
	}
	/**
	 * 获取指定url的网页内容
	 * @param url 需要获取内容的网址
	 * @return 网址的字符串内容
	 */
	protected String getPageInfo(String url){
		String page_info=null;
		try {
			page_info = PageStream.getPageString(url, null, proxy);
		} catch (ReuseUrlException e) {
			log.error("",e);
		}
		return page_info;
	}
	/**
	 * 设置代理对象
	 * @param proxy
	 */
	public static void setProxy(Proxy proxy){
		AbstractSpider.proxy=proxy;
	}
	public static BlockingQueue getQueue(){
		return movieQueue;
	}
}
