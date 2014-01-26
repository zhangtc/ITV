package com.itv.spider.dao;

import com.itv.spider.bean.MovieType;
import org.apache.log4j.Logger;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

/**
 * 数据库的基础操作
 * @author xiajun
 * @param <T>
 */
public class BaseDao<T> extends SqlSessionDaoSupport implements IBaseDao{
	private final static Logger log=Logger.getLogger(BaseDao.class);
	/**
	 * 插入数据
	 * @param sql_name
	 * @param obj
	 * @return
	 */
	public int insert(String sql_name,Object obj){
		try {
			return getSqlSession().insert(sql_name, obj);
		} catch (Exception e) {
			log.error("",e);
		}
		return 0;
	}

    /**
     * 查询movieType表中的电影类型
     * @return
     */
	public List<MovieType> findMovieType(){
		return getSqlSession().selectList("com.itv.sprider.movie.findMovieType");
	}
}
