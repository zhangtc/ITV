package com.itv.spider.s360.movie;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MovieThreadFactory implements ThreadFactory{
	AtomicInteger count=new AtomicInteger();
	public Thread newThread(Runnable r) {
		Thread t=new Thread(r);
		t.setDaemon(true);
		t.setName("movieThread_"+count.incrementAndGet());
		return t;
	}

}
