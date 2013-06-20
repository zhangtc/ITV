package com.itv.spider.bean;

/**
 * 
 * @author xiajun
 * 
 */
public class MovieFocusMap {
	private String id;
	private String name;// 影片名称
	private String supplierUrl;// 视频所在供应商地址
	private String bigImgUrl;// 大图地址
	private String miniImgUrl;// 小图地址
	private String text;// 文本介绍
	/**
	 * 0：焦点图
	 * 1：热播
	 * 2：首播
	 * 3：热映
	 * 4：轻松
	 * 5：激情
	 * 6：热搜榜
	 * 7：首播榜
	 * 8：休闲榜
	 * 9：激情榜
	 */
	private int type;//类型 如 焦点图，热播  
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSupplierUrl() {
		return supplierUrl;
	}

	public void setSupplierUrl(String supplierUrl) {
		this.supplierUrl = supplierUrl;
	}

	public String getBigImgUrl() {
		return bigImgUrl;
	}

	public void setBigImgUrl(String bigImgUrl) {
		this.bigImgUrl = bigImgUrl;
	}

	public String getMiniImgUrl() {
		return miniImgUrl;
	}

	public void setMiniImgUrl(String miniImgUrl) {
		this.miniImgUrl = miniImgUrl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
