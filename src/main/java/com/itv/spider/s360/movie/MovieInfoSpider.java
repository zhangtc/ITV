package com.itv.spider.s360.movie;

import org.apache.log4j.Logger;

import com.itv.spider.AbstractSpider;
import com.itv.spider.bean.MovieBean;

/**
 * 电影视频页面详细
 * 解析出导演，等信息
 * @author xiajun
 *
 */
public class MovieInfoSpider extends AbstractSpider{
	private final static Logger log=Logger.getLogger(MovieInfoSpider.class);
	private String url;
	public MovieInfoSpider(String url){
		this.url=url;
	}

	public void run() {
		MovieBean mb= MovieRegex.getMovieInfo(getPageInfo(this.url),this.url);
		if(mb!=null){
			try {
				movieQueue.put(mb);//将获取的对象放入阻塞队列
			} catch (InterruptedException e) {
				log.error("",e);
			}
		}
	}
}
