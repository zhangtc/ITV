package com.itv.spider.s360.movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.itv.spider.AbstractSpider;
import com.itv.spider.bean.MovieBean;
import com.itv.spider.bean.MovieFocusMap;
import com.itv.spider.util.FileReadUtil;
import com.itv.spider.util.RandomId;
import com.itv.spider.util.URLUtil;

/**
 * 使用正则解析页面内容 电影类型
 * @author xiajun
 *
 */
public class MovieRegex {
	private final static Logger log=Logger.getLogger(MovieRegex.class);
	private static Map<String, String> regex_map=null;
	static{
		regex_map=AbstractSpider.getRegexMap();
	}
	/**
	 * 获取电影首页的视频分类的url 如：大陆，喜剧
	 * @param pageInfo 页面内容
	 * @param url 此页面的url
	 * @return
	 */
	public static Map<String,String> getMovieType(String pageInfo,String url){
		if(pageInfo==null||"".equals(pageInfo)){
			return null;
		}
		Map<String,String> mList=null;
		String regex_div=regex_map.get("movie_index_type_div");
		long st=System.nanoTime();
		Pattern p=Pattern.compile(regex_div);
		Matcher m= p.matcher(pageInfo);
		if(m.find()){
			String div=m.group();
			if(div!=null&&!"".equals(div)){
				String regex_a=regex_map.get("movie_index_type_a");
				mList=new HashMap<String,String>();
				url=url.lastIndexOf('/')==url.length()-1?url:(url+"/");
				p=Pattern.compile(regex_a);
				m= p.matcher(div);
				while(m.find()){
					String a=m.group(1);
					String typeName=m.group(2);
					if(a.indexOf("http")<0){
						mList.put(url+a,typeName);
					}
				}
			}
		}
		log.debug("正则时间："+(System.nanoTime()-st)/1000000+" 毫秒.");
		return mList;
	}
	/**
	 * 获取单个视频页面的url
	 * @param pageInfo 页面内容
	 * @param url  页面url
	 * @return
	 */
	public static List<String> getMovieListUrl(String pageInfo,String url){
		if(pageInfo==null){
			return null;
		}
		List<String> list=new ArrayList<String>();
		String regex_a=regex_map.get("movie_page_list_url");
		Pattern p=Pattern.compile(regex_a);
		Matcher m= p.matcher(pageInfo);
		while(m.find()){
			String m_url=m.group(1);
			list.add(url+m_url);
		}
		return list;
	}
	/**
	 * 从当前页面获取下一页面的url
	 * @return
	 */
	public static String getMoviePageDownUrl(String pageInfo){
		if(pageInfo==null){
			return null;
		}
		String regex_a=regex_map.get("movie_page_down_url");
		Pattern p=Pattern.compile(regex_a);
		Matcher m=p.matcher(pageInfo);
		while(m.find()){
			String url=m.group(1);
			return url.replaceAll("amp;", "");
		}
		return null;
	}
	/**
	 * 获取视频详细信息
	 * @param pageInfo
	 */
	public static MovieBean getMovieInfo(String pageInfo,String url,String typeName){
		if(pageInfo==null){
			return null;
		}
		String regex_=regex_map.get("movie_info");
		long st=System.nanoTime();
		Pattern p=Pattern.compile(regex_);
		Matcher m=p.matcher(pageInfo);
		MovieBean mb=new MovieBean();
		if(m.find()){
			mb.setImgUrl(m.group(1));
			String name=m.group(2);
			mb.setName(name.length()>32?name.substring(0,32):name);
			mb.setDirector(m.group(3));
			mb.setActor(m.group(4));
			mb.setArea(m.group(5));
			mb.setYear(m.group(6));
			mb.setDuration(m.group(7));
			mb.setValue(Float.valueOf(m.group(8)+m.group(9)));
			int ra=0;
			try{
			   ra=Integer.valueOf(m.group(10));
			}catch(Exception e){}
			mb.setRatingCount(ra);
			mb.setLess(m.group(11));
		}else{
			regex_=regex_map.get("movie_info2");
			p=Pattern.compile(regex_);
			m=p.matcher(pageInfo);
			if(m.find()){
				mb.setImgUrl(m.group(1));
				mb.setName(m.group(2));
				mb.setYear(m.group(3));
				mb.setDirector(m.group(4));
				mb.setArea(m.group(5));
				mb.setDuration(m.group(6));
				mb.setActor(m.group(7));
				mb.setLess(m.group(8));
				mb.setValue(Float.valueOf(m.group(9)+m.group(10)));
				int ra=0;
				try {
					ra=Integer.valueOf(m.group(11));
				} catch (Exception e) {
				}
				mb.setRatingCount(ra);
			}else{
				regex_=regex_map.get("movie_info3");
				p=Pattern.compile(regex_);
				m=p.matcher(pageInfo);
				if(m.find()){
					mb.setImgUrl(m.group(1));
					mb.setName(m.group(2));
					mb.setYear(m.group(3));
					mb.setDirector(m.group(4));
					mb.setLanguage(m.group(5));
					mb.setArea(m.group(6));
					mb.setDuration(m.group(7));
					mb.setActor(m.group(8));
					mb.setLess(m.group(9));
					mb.setValue(Float.valueOf(m.group(10)+m.group(11)));
					int ra=0;
					try {
						ra=Integer.valueOf(m.group(12));
					} catch (Exception e) {
					}
					mb.setRatingCount(ra);
				}
			}
		}
		mb.setActor(getActor(mb.getActor()));
		mb.setSupplies(getSupplies(pageInfo,mb));
		mb.setSupplierUrl(url);
		mb.setTypeName(typeName);
		log.debug("正则时间："+(System.nanoTime()-st)/1000000+" 毫秒.");
		return mb;
	}
	/**
	 * 获取主演名单
	 * @param info
	 * @return
	 */
	private static String getActor(String info){
		if(info==null){
			return null;
		}
		String regex_=regex_map.get("movie_get_actor");
		Pattern p=Pattern.compile(regex_);
		Matcher m=p.matcher(info);
		StringBuffer actor=new StringBuffer();
		while(m.find()){
			actor.append(m.group(1));
			actor.append(" ");
		}
		return actor.toString().trim();
	}
	/**
	 * 获取第3方播放地址
	 * @param info
	 * @return json串
	 */
	private static String getSupplies(String info,MovieBean mb){
		if(info==null){
			return null;
		}
		String regex_=regex_map.get("movie_get_supplies1");
		Pattern p=Pattern.compile(regex_);
		Matcher m=p.matcher(info);
		if(m.find()){
			int count=0;
			String str= m.group(1);
			regex_=regex_map.get("movie_get_supplies_info");
			p=Pattern.compile(regex_);
			m=p.matcher(str);
			StringBuffer sb=new StringBuffer("[");
			while(m.find()){
				sb.append("{\"name\":\"");
				sb.append(URLUtil.decodeUnicode(m.group(1)));
				sb.append("\",\"link\":\"");
				sb.append(m.group(2));
				sb.append("\"},");
				count++;
			}
			sb.replace(sb.length()-1, sb.length(), "");
			sb.append("]");
			mb.setSuppliesCountl(count);
			return sb.toString().replaceAll("\\\\", "");
		}else{
			regex_=regex_map.get("movie_get_supplies2");
			p=Pattern.compile(regex_);
			m=p.matcher(info);
			while(m.find()){
				mb.setSuppliesCountl(1);
				return "{\"name\":\""+m.group(2)+"\",\"link\":\""+m.group(1)+"\"}";
			}
		}
		return null;
	}
	
	/**
	 * 获取电影首页的焦点图对象
	 * @param info 网页内容
	 * @param url 网页的地址
	 * @return
	 */
	public static List<MovieFocusMap> getMovieFocusMapBigImg(String info,String url){
		if(info==null){
			return null;
		}
		String regex_=regex_map.get("movie_focus_map_bigimg");
		Pattern p=Pattern.compile(regex_);
		Matcher m=p.matcher(info);
		List<MovieFocusMap> list=new ArrayList<MovieFocusMap>();
		int i=0;
		while(m.find()){
			String videoUrl=m.group(1);
			if(videoUrl.indexOf("http:")<0){
				videoUrl=url+videoUrl;
			}
			MovieFocusMap mfm=new MovieFocusMap();
			String img=m.group(2);
			mfm.setSupplierUrl(videoUrl);
			mfm.setBigImgUrl(img);
			list.add(mfm);
			i++;
			if(i>=8){
				break;
			}
		}
		return list;
	}
	/**
	 * 获取焦点图的文字介绍
	 * @param info
	 * @return
	 */
	public static List<MovieFocusMap> getMovieFocusMapText(String info){
		if(info==null){
			return null;
		}
		String regex_=regex_map.get("movie_focus_map_text_div");
		Pattern p=Pattern.compile(regex_);
		Matcher m=p.matcher(info);
		List<MovieFocusMap> list=new ArrayList<MovieFocusMap>();
		if(m.find()){
			String div=m.group(1);
			regex_=regex_map.get("movie_focus_map_text");
			p=Pattern.compile(regex_);
			m=p.matcher(div);
			while(m.find()){
				MovieFocusMap mfm=new MovieFocusMap();
				String name=m.group(1);
				String text=m.group(2);
				mfm.setName(name);
				mfm.setText(text.trim());
				list.add(mfm);
			}
		}
		return list;
	}
	/**
	 * 获取影视首页焦点图小图片地址
	 * @param info
	 * @return
	 */
	public static List<MovieFocusMap> getMovieFocusMapMiniImg(String info){
		if(info==null){
			return null;
		}
		String regex_=regex_map.get("movie_focus_map_miniimg_div");
		Pattern p=Pattern.compile(regex_);
		Matcher m=p.matcher(info);
		List<MovieFocusMap> list=new ArrayList<MovieFocusMap>();
		if(m.find()){
			String div=m.group(1);
			regex_=regex_map.get("movie_focus_map_miniimg");
			p=Pattern.compile(regex_);
			m=p.matcher(div);
			while(m.find()){
				MovieFocusMap mfm=new MovieFocusMap();
				String img=m.group(1);
				mfm.setMiniImgUrl(img);
				list.add(mfm);
			}
		}
		return list;
	}
	/**
	 * 解析热播剧
	 * @param info 网页内容
	 * @param url 网页url
	 * @return
	 */
	public static List<MovieFocusMap> getHotPlay(String info,String url){
		String regex_=regex_map.get("movie_hot_play_div");
		Pattern p=Pattern.compile(regex_);
		Matcher m=p.matcher(info);
		List<MovieFocusMap> list=new ArrayList<MovieFocusMap>();
		if(m.find()){
			String div=m.group(1);
			regex_=regex_map.get("movie_hot_play");
			p=Pattern.compile(regex_);
			m=p.matcher(div);
			while(m.find()){
				MovieFocusMap mfm=new MovieFocusMap();
				String url_=m.group(1);
				url_=url_.indexOf("http:")<0?url+url_:url;
				mfm.setSupplierUrl(url_);
				mfm.setMiniImgUrl(m.group(2));
				mfm.setName(m.group(3));
				mfm.setText(m.group(4));
				mfm.setType(1);
				mfm.setId(RandomId.getRandomId());
				list.add(mfm);
			}
		}
		return list;
	}
	/**
	 * 网络首播
	 * @param info
	 * @param url
	 * @return
	 */
	public static List<MovieFocusMap> getFirstPlay(String info,String url){
		String regex_=regex_map.get("movie_first_play_div");
		Pattern p=Pattern.compile(regex_);
		Matcher m=p.matcher(info);
		List<MovieFocusMap> list=new ArrayList<MovieFocusMap>();
		if(m.find()){
			String div=m.group(1);
			regex_=regex_map.get("movie_first_play");
			p=Pattern.compile(regex_);
			m=p.matcher(div);
			while(m.find()){
				MovieFocusMap mfm=new MovieFocusMap();
				String url_=m.group(1);
				url_=url_.indexOf("http:")<0?url+url_:url_;
				mfm.setSupplierUrl(url_);
				mfm.setMiniImgUrl(m.group(2));
				mfm.setName(m.group(3));
				mfm.setText(m.group(4));
				mfm.setType(2);
				mfm.setId(RandomId.getRandomId());
				list.add(mfm);
			}
		}
		return list;
	}
	/**
	 * 预告片
	 * @param info
	 * @param url
	 * @return
	 */
	public static List<MovieFocusMap> getPreviewPlay(String info,String url){
		String regex_=regex_map.get("movie_preview_play_div");
		Pattern p=Pattern.compile(regex_);
		Matcher m=p.matcher(info);
		List<MovieFocusMap> list=new ArrayList<MovieFocusMap>();
		if(m.find()){
			String div=m.group(1);
			regex_=regex_map.get("movie_preview_play");
			p=Pattern.compile(regex_);
			m=p.matcher(div);
			while(m.find()){
				MovieFocusMap mfm=new MovieFocusMap();
				String url_=m.group(1);
				if(url_.length()>60){
					continue;
				}
				if(url_.indexOf("/")!=0&&url_.indexOf("http:")!=0){
					url_="/"+url_;
				}
				url_=url_.indexOf("http:")<0?url+url_:url_;
				mfm.setSupplierUrl(url_);
				mfm.setMiniImgUrl(m.group(2));
				mfm.setName(m.group(3));
				mfm.setType(3);
				mfm.setId(RandomId.getRandomId());
				list.add(mfm);
			}
		}
		return list;
	}
	/**
	 * 获取轻松剧场数据
	 * @param info
	 * @param url
	 * @return
	 */
	public static List<MovieFocusMap> getEasyPlay(String info,String url){
		String regex_=regex_map.get("movie_easy_play_div");
		Pattern p=Pattern.compile(regex_);
		Matcher m=p.matcher(info);
		List<MovieFocusMap> list=new ArrayList<MovieFocusMap>();
		if(m.find()){
			String div=m.group(1);
			regex_=regex_map.get("movie_easy_play");
			p=Pattern.compile(regex_);
			m=p.matcher(div);
			while(m.find()){
				MovieFocusMap mfm=new MovieFocusMap();
				mfm.setMiniImgUrl(m.group(1));
				String surl=m.group(2);
				surl=surl.indexOf("http:")<0?url+surl:surl;
				mfm.setSupplierUrl(surl);
				mfm.setName(m.group(3));
				mfm.setText(m.group(4));
				mfm.setId(RandomId.getRandomId());
				mfm.setType(4);
				list.add(mfm);
			}
		}
		return list;
	}
	/**
	 * 激情剧场
	 * @param info
	 * @param url
	 * @return
	 */
	public static List<MovieFocusMap> getPassionPlay(String info,String url){
		String regex_=regex_map.get("movie_passion_play_div");
		Pattern p=Pattern.compile(regex_);
		Matcher m=p.matcher(info);
		List<MovieFocusMap> list=new ArrayList<MovieFocusMap>();
		if(m.find()){
			String div=m.group(1);
			regex_=regex_map.get("movie_passion_play");
			p=Pattern.compile(regex_);
			m=p.matcher(div);
			while(m.find()){
				MovieFocusMap mfm=new MovieFocusMap();
				mfm.setMiniImgUrl(m.group(1));
				String surl=m.group(2);
				surl=surl.indexOf("http:")<0?url+surl:surl;
				mfm.setSupplierUrl(surl);
				mfm.setName(m.group(3));
				mfm.setText(m.group(4));
				mfm.setId(RandomId.getRandomId());
				mfm.setType(5);
				list.add(mfm);
			}
		}
		return list;
	}
	/**
	 * 热播排行榜
	 * @param info
	 * @param url
	 * @return
	 */
	public static List<MovieFocusMap> getHotTop(String info,String url){
		String regex_=regex_map.get("movie_hot_top_div");
		Pattern p=Pattern.compile(regex_);
		Matcher m=p.matcher(info);
		List<MovieFocusMap> list=new ArrayList<MovieFocusMap>();
		if(m.find()){
			String div=m.group(1);
			regex_=regex_map.get("movie_hot_top");
			p=Pattern.compile(regex_);
			m=p.matcher(div);
			while(m.find()){
				MovieFocusMap mfm=new MovieFocusMap();
				String url_=m.group(1);
				url_=url_.indexOf("http:")<0?url+url_:url_;
				mfm.setSupplierUrl(url_);
				mfm.setName(m.group(2));
				mfm.setType(6);
				mfm.setId(RandomId.getRandomId());
				list.add(mfm);
			}
		}
		return list;
	}
	/**
	 * 首播榜 其实是特色榜
	 * @param info
	 * @param url
	 * @return
	 */
	public static List<MovieFocusMap> getFirstTop(String info,String url){
		return null;
	}
	/**
	 * 休闲剧场
	 * @param info
	 * @param url
	 * @return
	 */
	public static List<MovieFocusMap> getEasyTop(String info,String url){
		String regex_=regex_map.get("movie_easy_top_div");
		Pattern p=Pattern.compile(regex_);
		Matcher m=p.matcher(info);
		List<MovieFocusMap> list=new ArrayList<MovieFocusMap>();
		if(m.find()){
			String div=m.group(1);
			regex_=regex_map.get("movie_easy_top");
			//System.out.println(div);
			p=Pattern.compile(regex_);
			m=p.matcher(div);
			while(m.find()){
				MovieFocusMap mfm=new MovieFocusMap();
				String url_=m.group(1);
				url_=url_.indexOf("http:")<0?url+url_:url_;
				mfm.setSupplierUrl(url_);
				mfm.setName(m.group(2));
				mfm.setType(8);
				mfm.setId(RandomId.getRandomId());
				list.add(mfm);
			}
		}
		return list;
	}
	/**
	 * 激情影院排行榜
	 * @param info
	 * @param url
	 * @return
	 */
	public static List<MovieFocusMap> getPassionTop(String info,String url){
		String regex_=regex_map.get("movie_passion_top_div");
		Pattern p=Pattern.compile(regex_);
		Matcher m=p.matcher(info);
		List<MovieFocusMap> list=new ArrayList<MovieFocusMap>();
		if(m.find()){
			String div=m.group(1);
			regex_=regex_map.get("movie_passion_top");
			p=Pattern.compile(regex_);
			m=p.matcher(div);
			while(m.find()){
				MovieFocusMap mfm=new MovieFocusMap();
				String url_=m.group(1);
				url_=url_.indexOf("http:")<0?url+url_:url_;
				mfm.setSupplierUrl(url_);
				mfm.setName(m.group(2));
				mfm.setType(9);
				mfm.setId(RandomId.getRandomId());
				list.add(mfm);
			}
		}
		return list;
	}
}
