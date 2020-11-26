package com.entiry;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.handler.DealLinkUtil;
import com.handler.Handler;
import com.handler.ProxyHandler;

public class HttpEntiryOption {
	private final String uri;
	public static Map<String, String> map = new HashMap<String, String>();
	private HttpServletResponse resp;
	private Handler handler = new ProxyHandler();;

	static {
		//Parser parser = new HandlerParser("C://Users//zheng//Desktop//Jumping1.1//Jumping//src//resource//proxy.xml");
		//map = (Map<String, String>) parser.xmlToOjbect();
	}

	public HttpEntiryOption(CloseableHttpResponse response, String uri,HttpServletResponse resp) {
		this.uri = uri;
		this.resp = resp;
	}

	public void hadler(CloseableHttpResponse response) throws IOException {
		headerHandler(response);
		bodayHandler(response);
	}
	
	public void headerHandler(CloseableHttpResponse response) {
		//String className = map.get(uri);
		Header[] headers = response.getAllHeaders();
		//if (className == null) {
			
		for (Header header : headers) {
			resp.addHeader(header.getName(), header.getValue());
		}
//		} else {
//			for (Header header : headers) {
//				if ("text/html; charset=utf-8".equals(header.getValue())) {
//					resp.addHeader(header.getName(), header.getValue());
//				}
//				if ("text/html".equals(header.getValue())) {
//					resp.addHeader(header.getName(), header.getValue());
//				}
//				if ("text/plain; charset=utf-8".equals(header.getValue())) {
//					resp.addHeader(header.getName(), header.getValue());
//				}
//			}
//		}
	}

	public void bodayHandler(CloseableHttpResponse response) throws IOException {
			
			if (uri.isEmpty() || uri.equals("/") || uri.endsWith(".jpg") || uri.endsWith(".png") || uri.endsWith(".ico") || uri.endsWith(".gif")) {
				HttpEntity entity = response.getEntity();
				entity.writeTo(resp.getOutputStream());
				EntityUtils.consume(entity);
			} else {
				HttpEntity entity = response.getEntity();
				EntityBuilder entityBuilder = EntityBuilder.create();
				DealLinkUtil util = new DealLinkUtil();
				String htmlText = EntityUtils.toString(entity,Charset.forName("UTF-8"));
				//ÐÞ¸ÄÍ¼Æ¬
				htmlText = util.modifyLink(htmlText);
				entityBuilder.setBinary(htmlText.getBytes(Charset.forName("UTF-8")));
				entityBuilder.build().writeTo(resp.getOutputStream());
				
//				EntityBuilder entityBuilder = EntityBuilder.create();
//				String htmlText = EntityUtils.toString(entity,
//						Charset.defaultCharset());
//				String className = map.get(uri);
//					entityBuilder.setBinary(handler.handle(htmlText, className)
//							.getBytes(entity.getContentEncoding().getValue()));
//
//				entityBuilder.build().writeTo(resp.getOutputStream());
			}
	}

	public void display() throws IOException {
		resp.getOutputStream().close();
	}
}
