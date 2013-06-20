import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itv.spider.Proxy;
import com.itv.spider.bean.MovieBean;
import com.itv.spider.dao.BaseDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:ItvSpider.xml" })
public class Test {
	@Resource
	Proxy proxy;
	@Resource
	BaseDao<MovieBean> empDao;

	@org.junit.Test
	public void test() {
		System.out.println(proxy.getProxyHost());
	}
	@org.junit.Test
	public void insert(){
		MovieBean mb=new MovieBean();
		mb.setId("1234");
		empDao.insert("com.itv.sprider.movie.insertMovie",mb);
	}
	@org.junit.Test
	public void insertList(){
		MovieBean mb=new MovieBean();
		mb.setId("1234");
		MovieBean mb2=new MovieBean();
		mb2.setId("12345");
		List<MovieBean> movieList=new ArrayList<MovieBean>();
		movieList.add(mb);movieList.add(mb2);
		empDao.insert("com.itv.sprider.movie.insertMovieList",movieList);
	}
	@org.junit.Test
	public void ss(){
		try {
			String keyWord = URLDecoder.decode("\u4e50\u89c6 http:\\/\\/www.letv.com\\/ptv\\/pplay\\/44407\\/1.html", "utf-8");
			System.out.println(keyWord);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
