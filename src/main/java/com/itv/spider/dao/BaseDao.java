package com.itv.spider.dao;

import org.apache.log4j.Logger;
import org.mybatis.spring.support.SqlSessionDaoSupport;
/**
 * 数据库的基础操作
 * @author xiajun
 *
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
	public T find(){
		
		return null;
	}
}
