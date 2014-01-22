package com.itv.spider.s360.movie;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.itv.spider.AbstractSpider;
import com.itv.spider.bean.MovieFocusMap;
import com.itv.spider.httpclient.PageStream;
import com.itv.spider.service.MovieFocusMapService;
import com.itv.spider.util.RandomId;


/**
 * 解析电影首页
 * 
 * @author xiajun
 * 
 */
public class MovieIndexSpider extends AbstractSpider {
	private final static Logger log = Logger.getLogger(MovieIndexSpider.class);
	private MovieFocusMapService<MovieFocusMap> movieFocusMapService;
	private String url;// 页面需要访问的url
	private String page_info;// url对应的内容
	private String shortUrl=null;//截取后的url
	public MovieIndexSpider(String url) {
		if (null == url) {
			throw new NullPointerException("参数url不能为： null");
		}
		this.url = url;
	}

	/**
	 * 重写父类方法,原因是此类只解析首页因此当内容获取到后将一直使用
	 * 获取页面内容
	 * @param url 网址
	 * @return 参数网址的内容
	 */
	protected String getPageInfo(String url) {
		if (null == page_info || "".equals(page_info)) {
			try {
				page_info = PageStream.getPageString(url, null, proxy);
			} catch (Exception e) {
				log.error("", e);
			}
		}
		return page_info;
	}

	/**
	 * 将获得的电影分类 大陆，香港，搞笑...并让线程继续解析其页面并获得其页面的电影列表 由于360使用相对url所以要将本页面的url传到下一级
	 * v.360.cn/dianying
	 */
	public void run() {
		this.focusMap();
		this.hotPlay();
		this.firstPlay();
		this.previewPlay();
		this.easyPlay();
		this.passionPlay();
		this.hotTop();
		this.easyTop();
		this.passionTop();
		Map<String,String> map = MovieRegex.getMovieType(getPageInfo(this.url), url.substring(0, url.lastIndexOf('/')));
		if (map != null) {
			for (Entry<String, String> type_url : map.entrySet()) {
				if(type_url.getKey().indexOf("cat=")!=-1){
					spiderPool.execute(new MoviePageSpider(type_url.getKey(),type_url.getValue()));
				}else{
					spiderPool.execute(new MoviePageSpider(type_url.getKey()));
				}
				
			}
		}
	}
	/**
	 * 解析焦点图
	 */
	public void focusMap(){
		List<MovieFocusMap> list1=MovieRegex.getMovieFocusMapBigImg(getPageInfo(this.url),getShortUrl());
		List<MovieFocusMap> list2=MovieRegex.getMovieFocusMapText(this.page_info);
		List<MovieFocusMap> list3=MovieRegex.getMovieFocusMapMiniImg(this.page_info);
		for (int i=0;i<list1.size();i++) {
			MovieFocusMap mb=list1.get(i);
			mb.setName(list2.get(i).getName());
			mb.setText(list2.get(i).getText());
			mb.setMiniImgUrl(list3.get(i).getMiniImgUrl());
			mb.setId(RandomId.getRandomId());
			mb.setType(0);
		}
		this.seriesUrl(list1);
		this.movieFocusMapService.insertMovieFocusMap(list1);
	}
	/**
	 * 热播剧
	 */
	public void hotPlay(){
		List<MovieFocusMap> list=MovieRegex.getHotPlay(getPageInfo(this.url), getShortUrl());
		this.seriesUrl(list);
		this.movieFocusMapService.insertMovieFocusMap(list);
	}
	/**
	 * 首播
	 */
	public void firstPlay(){
		List<MovieFocusMap> list=MovieRegex.getFirstPlay(getPageInfo(this.url), getShortUrl());
		this.seriesUrl(list);
		this.movieFocusMapService.insertMovieFocusMap(list);
	}
	/**
	 * 预告片
	 */
	public void previewPlay(){
		List<MovieFocusMap> list=MovieRegex.getPreviewPlay(getPageInfo(this.url), getShortUrl());
		this.seriesUrl(list);
		this.movieFocusMapService.insertMovieFocusMap(list);
	}
	/**
	 * 轻松时刻
	 */
	public void easyPlay(){
		List<MovieFocusMap> list=MovieRegex.getEasyPlay(getPageInfo(this.url), getShortUrl());
		this.seriesUrl(list);
		this.movieFocusMapService.insertMovieFocusMap(list);
	}
	/**
	 * 激情剧场
	 */
	public void passionPlay(){
		List<MovieFocusMap> list=MovieRegex.getPassionPlay(getPageInfo(this.url), getShortUrl());
		this.seriesUrl(list);
		this.movieFocusMapService.insertMovieFocusMap(list);
	}
	/**
	 * 热播排行榜
	 */
	public void hotTop(){
		List<MovieFocusMap> list=MovieRegex.getHotTop(getPageInfo(this.url), getShortUrl());
		this.seriesUrl(list);
		this.movieFocusMapService.insertMovieFocusMap(list);
	}
	/**
	 * 首播榜
	 */
	public void firstTop(){
		List<MovieFocusMap> list=MovieRegex.getFirstTop(getPageInfo(this.url), getShortUrl());
		this.seriesUrl(list);
		this.movieFocusMapService.insertMovieFocusMap(list);
	}
	/**
	 * 轻松榜
	 */
	public void easyTop(){
		List<MovieFocusMap> list=MovieRegex.getEasyTop(getPageInfo(this.url), getShortUrl());
		this.seriesUrl(list);
		this.movieFocusMapService.insertMovieFocusMap(list);
	}
	/**
	 * 激情榜
	 */
	public void passionTop(){
		List<MovieFocusMap> list=MovieRegex.getPassionTop(getPageInfo(this.url), getShortUrl());
		this.seriesUrl(list);
		this.movieFocusMapService.insertMovieFocusMap(list);
	}
	/**
	 * 解析首页中的各个位置url 如：热播
	 */
	private void seriesUrl(List<MovieFocusMap> list){
		if(list!=null){
			for (int i = 0; i < list.size(); i++) {
				spiderPool.execute(new MovieInfoSpider(list.get(i).getSupplierUrl(),null));
			}
		}
	}
	/**
	 * 返回短url
	 * @return
	 */
	public String getShortUrl(){
		if(this.shortUrl!=null){
			return this.shortUrl;
		}
		int last1=this.url.lastIndexOf("/");
		String url_=this.url.substring(0,last1);
		int last2=url_.lastIndexOf("/");
		url_=url_.substring(0, last2);
		return url_;
	}
	
	public MovieFocusMapService<MovieFocusMap> getMovieFocusMapService() {
		return movieFocusMapService;
	}

	public void setMovieFocusMapService(MovieFocusMapService<MovieFocusMap> movieFocusMapService) {
		this.movieFocusMapService = movieFocusMapService;
	}
}
