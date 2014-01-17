package com.itv.spider.bean;

import com.itv.spider.util.RandomId;

/**
 * 电影信息
 * 
 * @author xiajun
 * 
 */
public class MovieBean {
	private String id;// 主键id
	private String name;// 影视名称
	private String typeName;//电影类型 喜剧
	private String director;// 导演
	private String actor;// 主演
	private String area;// 地区
	private String year;// 年代
	private String duration;// 片长
	private float value;// 评分
	private String less;// 简介
	//private String type;//电影类型 喜剧
	private int ratingCount;// 评价人数
	private String supplies;// 第三方影片资源json
	private String imgUrl;// 图片地址
	private String language;// 语言
	private int suppliesCount;//第三方资源数
	private String supplierUrl;//本网页的url
	private String downUrl;//下载地址
	public MovieBean() {
		this.id = RandomId.getRandomId();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		if (director.length() > 20) {
			this.director = director.substring(0, 20);
		} else {
			this.director = director;
		}
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area.length() > 16 ? area.substring(0, 16) : area;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		if (duration.length() > 16) {
			this.duration = duration.substring(0, 16);
		} else {
			this.duration = duration;
		}
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public String getLess() {
		return less;
	}

	public void setLess(String less) {
		this.less = less;
	}

	public int getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}

	public String getSupplies() {
		return supplies;
	}

	public void setSupplies(String supplies) {
		this.supplies = supplies;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSuppliesCount() {
		return suppliesCount;
	}

	public void setSuppliesCountl(int suppliesCountl) {
		this.suppliesCount = suppliesCountl;
	}

	public String getSupplierUrl() {
		return supplierUrl;
	}

	public void setSupplierUrl(String supplierUrl) {
		this.supplierUrl = supplierUrl;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("影视名称：");
		sb.append(name);
		sb.append(" 导演：");
		sb.append(director);
		sb.append(" 主演: ");
		sb.append(actor);
		sb.append(" 片长：");
		sb.append(duration);
		sb.append(" 评分：");
		sb.append(value);
		sb.append(" 资源：");
		sb.append(supplies);
		return sb.toString();
	}
}
