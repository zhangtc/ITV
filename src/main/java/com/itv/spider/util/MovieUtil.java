package com.itv.spider.util;

import com.itv.spider.service.MovieService;

import java.util.Map;

/**
 * User: bjxiajun
 * Date: 14-1-26
 * Time: 上午10:14
 */
public class MovieUtil {
    private static MovieUtil mu = new MovieUtil();
    private MovieService movieService;
    private Map<String, Integer> movieTypeMap = null;

    private MovieUtil() {
    }

    /**
     * 设置movieService对象
     *
     * @param movieService
     */
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * 获取movie type的字典表
     *
     * @return
     */
    public synchronized Map<String, Integer> getMovieTypeMap() {
        if (movieTypeMap == null && movieService != null) {
            movieTypeMap = movieService.getMovieType();
        }
        return movieTypeMap;
    }

    /**
     * 获取本类的单例对象
     *
     * @return
     */
    public static MovieUtil getInstance() {
        return mu;
    }
}
