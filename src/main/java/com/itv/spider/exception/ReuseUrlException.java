package com.itv.spider.exception;
/**
 * 如果url已经被使用过，当再次使用时会抛出此异常
 * @author xiajun
 *
 */
public class ReuseUrlException extends Exception {
	private final static String info=" url重复使用.";
	public ReuseUrlException(String url){
		super((url+info));
	}
	public ReuseUrlException(){
		super();
	}
	public static void main(String[] args) throws ReuseUrlException {
		throw new ReuseUrlException("http:////ss");
	}
}
