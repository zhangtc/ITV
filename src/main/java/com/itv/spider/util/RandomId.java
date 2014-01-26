package com.itv.spider.util;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 根据系统时间和随机数生成随机id
 *
 * @author xiajun
 */
public class RandomId {
    private static Random r = new Random();
    static AtomicInteger atomic = new AtomicInteger(1);

    public static String getRandomId() {
        long time = System.currentTimeMillis();
        String ss = time+""+atomic.incrementAndGet();
        return Long.toHexString(Long.valueOf(ss));
    }

    public static void main(String[] args) {
        System.out.println(RandomId.getRandomId());
    }
}
