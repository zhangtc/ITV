package com.itv.spider.bean;

/**
 * User: bjxiajun
 * Date: 14-1-26
 * Time: 上午11:05
 * 中间表
 */
public class Movie2MovieType {
    private int id;
    private String mid;
    private int tid;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
