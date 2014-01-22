package com.itv.spider.s360.movie;

import java.util.List;

import org.apache.log4j.Logger;

import com.itv.spider.AbstractSpider;

/**
 * 爬取电影列表页面
 * 
 * @author xiajun
 * 
 */
public class MoviePageSpider extends AbstractSpider {
	private final static Logger log=Logger.getLogger(MoviePageSpider.class);
	private String url;
	private String typeName;//电影类别 喜剧
	/**
	 * 构造函数
	 * @param url 需要解析的url地址
	 */
	public MoviePageSpider(String url) {
		this.url = url;
	}
	/**
	 * 构造函数
	 * @param url 需要解析的url地址
	 * @param typeName 视频类型 此页面上的视频都是该类型
	 */
	public MoviePageSpider(String url,String typeName){
		this(url);
		this.typeName=typeName;
	}

	public void run() {
		int i = url.lastIndexOf('/');
		i = url.substring(0, i).lastIndexOf('/');
		String downPage = null;
		String page_info = getPageInfo(this.url);
		do {
			List<String> list = MovieRegex.getMovieListUrl(page_info, url.substring(0, i));
			if (list != null) {
				for (String mv_url : list) {
					spiderPool.execute(new MovieInfoSpider(mv_url,typeName));
					/*if(typeName!=null){
						log.info("update movie set typeName=CONCAT_WS('|','"+typeName+"',typeName)  where supplierUrl='"+mv_url+"';");
					}*/
				}
			}
			downPage = MovieRegex.getMoviePageDownUrl(page_info);
			if (downPage != null) {
				page_info = getPageInfo(downPage);
			}
		} while (downPage != null);
	}
}
