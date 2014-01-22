package com.itv.spider.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.itv.spider.AbstractSpider;
import com.itv.spider.bean.MovieBean;
import com.itv.spider.dao.IBaseDao;

public class MovieService extends AbstractSpider {
	private IBaseDao<MovieBean> baseDao;
	public MovieService(IBaseDao<MovieBean> baseDao){
		this.baseDao=baseDao;
	}
	private final static Logger log = Logger.getLogger(MovieService.class);
	private List<MovieBean> movieList=new ArrayList<MovieBean>(200);
	public void run() {
		while (true) {
			try {
				MovieBean mb= movieQueue.poll(20, TimeUnit.SECONDS);
				if(mb!=null){
					movieList.add(mb);
					if(movieList.size()>=200){
						baseDao.insert("com.itv.sprider.movie.insertMovieList", movieList);
						movieList.clear();
					}
				}else{
					if(movieList.size()>0){
						baseDao.insert("com.itv.sprider.movie.insertMovieList", movieList);
						movieList.clear();
					}else{
						if(AbstractSpider.spiderPool.isTerminated()){
							log.info("数据库插入队列正常退出.");
							break;
						}
					}
				}
			} catch (InterruptedException e) {
				log.error("数据库插入队列异常导致退出.",e);
				break;
			}
		}
	}

}
