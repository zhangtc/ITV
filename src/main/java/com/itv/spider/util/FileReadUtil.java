package com.itv.spider.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class FileReadUtil {
	public static String readRegex(File file){
		FileReader fr=null;
		BufferedReader bis=null;
		String regex=null;
		try{
			fr=new FileReader(file);
			bis=new BufferedReader(fr);
			regex=bis.readLine();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				fr.close();
				bis.close();
			} catch (Exception e2) {
			}
		}
		return regex;
	}
	public static void writeFile(String info,String path) throws Exception{
		File f=new File(path);
		OutputStreamWriter osw=null;
		try {
			OutputStream out=new FileOutputStream(f);
			osw=new OutputStreamWriter(out,"utf-8");
			osw.write(info);
		} catch (Exception e) {
			throw e;
		}finally{
			try {
				osw.close();
			} catch (IOException e) {
			}
		}
	}
	public static String readFile(String path) throws IOException{
		File f=new File(path);
		InputStream is=new FileInputStream(f);
		InputStreamReader isr=new InputStreamReader(is);
		BufferedReader buff=null;
		String str=null;
		StringBuffer sb=null;
		try {
			buff=new BufferedReader(isr);
			sb=new StringBuffer();
			while((str=buff.readLine())!=null){
				sb.append(str);
			}
		} catch (Exception e) {
			
		}finally{
			buff.close();
			isr.close();
			is.close();
		}
		return sb.toString();
	}
}
