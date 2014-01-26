package com.itv.spider.dao;

import com.itv.spider.bean.MovieType;

import java.util.List;

/**
 * 数据库操作接口
 * @author xiajun
 *
 */
public interface IBaseDao<T> {
	/**
	 * 插入数据到数据库
	 * @param sql_name 调用的sql语句完整名称
	 * @param obj 存储的数据对象
	 * @return 成功的条数
	 */
	public int insert(String sql_name,Object obj);
    /**
     * 查询movieType表中的电影类型
     * @return
     */
    public List<MovieType> findMovieType();
}
