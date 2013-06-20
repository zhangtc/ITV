package com.itv.spider.xml;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 解析配置正则的xml文件
 * 
 * @author xiajun
 * 
 */
public class RegexXml {
	private final static Logger log=Logger.getLogger(RegexXml.class);
	/**
	 * 获取document
	 * @param xml 配置文件地址
	 * @return
	 * @throws DocumentException
	 */
	static Document getDoc(String xml) throws DocumentException {
		Document doc = null;
		SAXReader saxReader = new SAXReader();
		doc = saxReader.read(new File(xml));
		return doc;
	}
	/**
	 * 获取regex节点的属性
	 * @param xml
	 * @return
	 */
	public static Map<String, String> getRegexMap(String xml){
		Document doc=null;
		Map<String,String> map=null;
		try {
			String url=RegexXml.class.getClassLoader().getResource(xml).getPath();
			doc=getDoc(url);
		} catch (DocumentException e) {
			log.error("",e);
			return null;
		}
		Element root=doc.getRootElement();
		List<Element> el_list= root.elements();
		if(el_list!=null){
			map=new HashMap<String, String>();
			for (Element el:el_list) {
				String name=el.attribute("name").getValue();
				String value=el.getText();
				map.put(name, value);
			}
		}
		return map;
	}
	
	public static void main(String[] args) {
		String url=RegexXml.class.getClassLoader().getResource("360/regex_360.xml").getPath();
		Map m=RegexXml.getRegexMap(url);
		System.out.println(m.get("index"));
	}
}
