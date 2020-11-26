package com.handler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import common.Contracts;

public class DealLinkUtil {

	/**
	 * 替换掉内部的所有连接
	 * @param html
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public String modifyLink(String html) throws MalformedURLException, IOException{
		Document doc=Jsoup.parse(html);
		
		Elements elements=doc.select("a[href!=#]");
		adsoluteAHref(elements);
		Elements jsElements=doc.select("script[src]");
		absoluteScriptSrc(jsElements);
		Elements imgElements=doc.select("img[src]");
		absoluteImagSrc(imgElements);
		Elements formElements=doc.select("form[action]");
		absoluteFormAction(formElements);
		
		Elements selfElements=doc.select("a[data-landurl]");
//		IteratorSelf(selfElements);
		
		Elements linkElements=doc.select("link[href]");
		absoluteLinkHref(linkElements);
		
		//修改样式
//		Elements  css=doc.select("[style]");//获取所有含有style属性的元素
//		IteratorElements(css);
//		Elements styleCss=doc.select("style");//获得html中的样式标签如<style type="text/css"></style>
//		IteratorStyle(styleCss);
		
		Elements scriptJs=doc.select("script");//获得<script type="text/javascript"></script>
		IteratorStyle(scriptJs);
		
		Element  base=doc.select("base").first();
		if(base!=null){
			base.attr("href", Contracts.prefix);
		}else{
			Element head=doc.select("head").first();
			head.append("<base href=\""+Contracts.prefix+"\">");
		}
		
		return doc.toString();
	}
	
	/**
	 * 解析自定义连接
	 * @param elements
	 */
	public void IteratorSelf(Elements elements) {
		Iterator<Element> iterator = elements.iterator();
		Element element = iterator.next();
		String src=element.attr("abs:data-landurl");//将所有的相对地址换为绝对地址;
		int index = getIndex(src);
		if(!StringUtil.isBlank(src) && index != -1) {
			String prefix = src.substring(0,getIndex(src)).trim();
			String endfix = src.substring(getIndex(src),src.length());
			element.attr("data-landurl",Contracts.prefix+endfix);
			element.attr("href",Contracts.prefix+endfix);
			Contracts.uriMap.put(endfix, prefix);
		}
	}
	
//	public void modifyCssurl(Elements elements) throws IOException{
////		URL url=new URL("http://localhost:8080/novel/User/register.jsp");
////		URL url=new URL("http://www.baidu.com/");
////		Document doc=Jsoup.parse(url.openStream(), IdentifyCode.getFileCode(url), "http://sdfsdfsddfBYSJ/");
//		Elements  css=doc.select("[style]");//获取所有含有style属性的元素
//		IteratorElements(css);
//		
//		Elements styleCss=doc.select("style");//获得html中的样式标签如<style type="text/css"></style>
//		IteratorStyle(styleCss);
//		
//		Elements scriptJs=doc.select("script");//获得<script type="text/javascript"></script>
//		IteratorStyle(scriptJs);
//	}
	
	//处理<script type="text/javascript">脚本
	public void IteratorStyle(Elements elements){
		Iterator<Element> iterator=elements.iterator();
		while(iterator.hasNext()){
			Element element=iterator.next();
			//System.out.println(element.data());//获得<script type="text/javascript">中的值
											   //<style type="text/css"></style>中的值
		}
	}
	
	//得到元素中内嵌样式style中的值如<div style="width:100px;">
	public void IteratorElements(Elements elements){
		Iterator<Element> iterator=elements.iterator();
		while(iterator.hasNext()){
			Element element=iterator.next();
			String style=element.attr("style");
			//System.out.println(style);
			getURL(style);
		}
	}
	//从style中获得 url的值
	public void getURL(String url){
		Pattern p = Pattern.compile("url\\((.*)\\)");//匹配  url(任何)
		Matcher m = p.matcher(url);
		if(m.find()){
			//System.out.println(m.group(1));//获取括号中的地址
		}
	}
	//将Form action 装换为绝对的url
	public void absoluteFormAction(Elements formElements){
		Iterator<Element> iterator=formElements.iterator();
		while(iterator.hasNext()){
			Element element=iterator.next();
			String action=element.attr("abs:action");//将所有的相对地址换为绝对地址;
			//添加隐藏域，用来传替url。
			element.append("<input type='hidden' name='action' value='"+action+"'/>");
			int index = getIndex(action);
			if(!StringUtil.isBlank(action)&& index != -1) {
				String prefix = action.substring(0,getIndex(action)).trim();
				String endfix = action.substring(getIndex(action),action.length());
				element.attr("href",Contracts.prefix+endfix);
				Contracts.uriMap.put(endfix, prefix);
			}
//			element.attr("action",Contracts.prefix+ "Actionjsp");//装换为
			
		}
	}
	
	//将<script src>转换为绝对地址 
	public void absoluteScriptSrc(Elements jsElements) throws MalformedURLException{
		Iterator<Element> iterator=jsElements.iterator();
		while(iterator.hasNext()){
			Element element=iterator.next();
			String src=element.attr("abs:src");//将所有的相对地址换为绝对地址;
			int index = getIndex(src);
			if(!StringUtil.isBlank(src) && index != -1) {
				String prefix = src.substring(0,getIndex(src)).trim();
				String endfix = src.substring(getIndex(src),src.length());
				element.attr("href",Contracts.prefix+ endfix);
				Contracts.uriMap.put(endfix, prefix);
			}
//			element.attr("src",Contracts.prefix+"Jsjsp?src="+src);//装换为
	//		element.attr("charset",IdentifyCode.getFileCode(src));
		}
	}
	
	//将Img src 装换为绝对的url
	public void absoluteImagSrc(Elements imagElements) throws MalformedURLException{
		Iterator<Element> iterator=imagElements.iterator();
		while(iterator.hasNext()){
			Element element=iterator.next();
			String src=element.attr("abs:src");//将所有的相对地址换为绝对地址;
			int index = getIndex(src);
			if(!StringUtil.isBlank(src) && index != -1) {
				String prefix = src.substring(0,getIndex(src)).trim();
				String endfix = src.substring(getIndex(src),src.length());
				element.attr("href",Contracts.prefix+endfix);
				Contracts.uriMap.put(endfix, prefix);
			}

//			element.attr("src",Contracts.prefix+"Imgjsp?src="+src);//装换为
			
		}
	}
	
	//将Link href 装换为绝对的url
		public void absoluteLinkHref(Elements linkElements) throws MalformedURLException{
			Iterator<Element> iterator=linkElements.iterator();
			while(iterator.hasNext()){
				Element element=iterator.next();

				String src=element.attr("abs:href");//将所有的相对地址换为绝对地址;
				int index = getIndex(src);
				if(!StringUtil.isBlank(src) && index != -1) {
					String prefix = src.substring(0,getIndex(src)).trim();
					String endfix = src.substring(getIndex(src),src.length());
					element.attr("href",Contracts.prefix+endfix);
					Contracts.uriMap.put(endfix, prefix);
				}
//				element.attr("href",Contracts.prefix+ "Linkjsp?href="+src);//装换为
	//			element.attr("charset",IdentifyCode.getFileCode(src));//设置外部文件的编码
			}
		}
	//将所有的的<a href>转换为绝对地址
	public void adsoluteAHref(Elements AElements){
		Iterator<Element> iterator = AElements.iterator();
		while(iterator.hasNext()){
			Element element=iterator.next();
			String href = element.attr("abs:href");//将所有的相对地址换为绝对地址;
			int index = getIndex(href);
			if(!StringUtil.isBlank(href)&& index != -1) {
				String prefix = href.substring(0,getIndex(href));
				String endfix = href.substring(getIndex(href),href.length());
				element.attr("href",Contracts.prefix+endfix);
				Contracts.uriMap.put(endfix, prefix);
			}
		}
	}
	
	public int getIndex(String href) {
		if(href.indexOf(".com") != -1) {
			return href.indexOf(".com") +4;
		}
		if(href.indexOf(".cn") != -1) {
			return href.indexOf(".cn") + 3;
		}
		if(href.indexOf(".cc") != -1) {
			return href.indexOf(".cc") + 3;
		}
		if(href.indexOf(".com.cn") != -1) {
			return href.indexOf(".com.cn")+ 7;
		}
		if(href.indexOf(".vip") != -1) {
			return href.indexOf(".vip") + 4;
		}
		if(href.indexOf(".tt") != -1) {
			return href.indexOf(".tt")  +3;
		}
		if(href.indexOf(".org") != -1) {
			return href.indexOf(".org") + 4;
		}
		return -1;
	}
	
}
