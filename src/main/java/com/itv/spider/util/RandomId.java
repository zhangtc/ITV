package com.itv.spider.util;

import java.util.Random;
/**
 * 根据系统时间和随机数生成随机id
 * @author xiajun
 *
 */
public class RandomId {
	private static Random r = new Random();
	public static String getRandomId() {
		int r_1=0;
		long time=0;
		int r_2=0;
		synchronized (r) {
			time = System.currentTimeMillis();
			r_1 = r.nextInt(1000);
			r_2=r.nextInt(100);
		}
		String ss = r_2+""+time + "" + r_1;
		return Long.toHexString(Long.valueOf(ss));
	}
}
