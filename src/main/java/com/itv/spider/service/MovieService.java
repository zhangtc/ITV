package com.itv.spider.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.itv.spider.bean.Movie2MovieType;
import com.itv.spider.bean.MovieType;
import org.apache.log4j.Logger;

import com.itv.spider.AbstractSpider;
import com.itv.spider.bean.MovieBean;
import com.itv.spider.dao.IBaseDao;

/**
 * 通过阻塞队列处理爬虫爬取的视频信息
 *
 * @author xiajun
 */
public class MovieService extends AbstractSpider {
    private IBaseDao<MovieBean> baseDao;

    public MovieService(IBaseDao<MovieBean> baseDao) {
        this.baseDao = baseDao;
    }

    private final static Logger log = Logger.getLogger(MovieService.class);
    private List<MovieBean> movieList = new ArrayList<MovieBean>(256);//存放视频信息对象
    private List<Movie2MovieType> movie2movieType = new ArrayList<Movie2MovieType>(256);

    public void run() {
        while (true) {
            try {
                MovieBean mb = movieQueue.poll(20, TimeUnit.SECONDS);
                if (mb != null) {
                    movieList.add(mb);
                    if(mb.getTypeName()!=null){
                        Movie2MovieType m2=new Movie2MovieType();
                        m2.setMid(mb.getId());
                        m2.setTid(mb.getTypeName().get(0));
                        movie2movieType.add(m2);
                    }

                    if (movieList.size() >= 256) {//每256个对象批量处理
                        baseDao.insert("com.itv.sprider.movie.insertMovieList", movieList);
                        movieList.clear();
                        baseDao.insert("com.itv.sprider.movie.insertMovie2Type",movie2movieType);
                        movie2movieType.clear();
                    }
                } else {
                    if (movieList.size() > 0) {
                        baseDao.insert("com.itv.sprider.movie.insertMovieList", movieList);
                        movieList.clear();
                        baseDao.insert("com.itv.sprider.movie.insertMovie2Type",movie2movieType);
                        movie2movieType.clear();
                    } else {
                        int size = ((ThreadPoolExecutor) AbstractSpider.spiderPool).getQueue().size();
                        if (size == 0) {
                            log.info("数据库插入队列正常退出.");
                            break;
                        }
                    }
                }
            } catch (InterruptedException e) {
                log.error("数据库插入队列异常导致退出.", e);
                break;
            }
        }
    }

    /**
     * 获取movietype 表中的数据，形成以名称为key的对照表
     * @return
     */
    public Map<String, Integer> getMovieType() {
        Map<String,Integer> map=new HashMap<String, Integer>();
        List<MovieType> list = baseDao.findMovieType();
        for (int i = 0; i < list.size(); i++) {
            MovieType movieType = list.get(i);
            map.put(movieType.getName(),movieType.getId());
        }
        return map;
    }
    public void ins(List movie){
        baseDao.insert("com.itv.sprider.movie.insertMovieList", movie);
    }
}
