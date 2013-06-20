package com.itv.spider.service;

import com.itv.spider.dao.IBaseDao;

/**
 * 焦点图写入数据库服务
 * @author xiajun
 *
 */
public class MovieFocusMapService<T> {
	private IBaseDao<T> baseDao;
	public MovieFocusMapService(IBaseDao<T> baseDao){
		this.baseDao=baseDao;
	}
	/**
	 * 插入焦点图
	 * @param obj
	 * @return
	 */
	public int insertMovieFocusMap(Object obj){
		return this.baseDao.insert("com.itv.sprider.movie.insertFocusMapList", obj);
	}
}
