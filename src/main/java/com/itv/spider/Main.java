package com.itv.spider;

import com.itv.spider.s360.movie.MovieIndexSpider;
import com.itv.spider.service.MovieService;
import com.itv.spider.util.MovieUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动类
 * @author xiajun	
 *
 */
public class Main {
	private static ApplicationContext ac=null;
	private static MovieService movieService=null;
	static{
		ac = new ClassPathXmlApplicationContext("classpath:ItvSpider.xml");
		movieService=(MovieService) ac.getBean("movieService");
	}
	public static void main(String[] args) {
        MovieUtil.getInstance().setMovieService(movieService);
		MovieIndexSpider movieIndexSpider=(MovieIndexSpider)ac.getBean("movieIndexSpider");
		AbstractSpider.spiderPool.execute(movieIndexSpider);
		movieService.run();

	}
}
