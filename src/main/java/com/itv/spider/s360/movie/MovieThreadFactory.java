package com.itv.spider.s360.movie;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * 线程池线程创建工厂类
 * @author xiajun
 *
 */
public class MovieThreadFactory implements ThreadFactory{
	AtomicInteger count=new AtomicInteger();
	/**
	 * 创建守护进程
	 */
	public Thread newThread(Runnable r) {
		Thread t=new Thread(r);
		t.setDaemon(true);
		t.setName("movieThread_"+count.incrementAndGet());
		return t;
	}

}
