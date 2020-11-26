package com.session;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import com.entiry.HttpEntiryOption;

public class SessionManager {

	private final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
	private final CloseableHttpClient httpclient = HttpClients.custom()
            .setRedirectStrategy(new LaxRedirectStrategy())
            .setDefaultCookieStore(new BasicCookieStore())
    		.setConnectionManager(cm)
            .build();
    private final HttpContext context;
    private CloseableHttpResponse response;
    
	private String baseUrl;
	
	public SessionManager(String baseUrl) {
		this.baseUrl = baseUrl;
        this.context = HttpClientContext.create();
		init();
	}
	public void init() {
		    // 将最大连接数增加到200
		    cm.setMaxTotal(200);
		    // 将每个路由基础的连接增加到20
		    cm.setDefaultMaxPerRoute(20);
		    HttpHost localhost = new HttpHost(baseUrl, 80);
		    cm.setMaxPerRoute(new HttpRoute(localhost), 50);
	}
	
	public void sendSession(String type,HttpEntiryOption option,String uri,Map<String, String[]>parameterMap) throws URISyntaxException, ClientProtocolException, IOException {
		//URIBuilder uriBuilder = new URIBuilder().setScheme("http").setHost(baseUrl).setPort(80).setPath(uri);
		
		URIBuilder uriBuilder;
		if(baseUrl.startsWith("https")){
			uriBuilder = new URIBuilder(baseUrl).setPath(uri);
		} else {
			uriBuilder = new URIBuilder().setScheme("http").setPort(80).setHost(baseUrl).setPath(uri);
		}
		URI url = uriBuilder.build();
		if ("get".equals(type)) {
			for (Object pn : parameterMap.keySet()) {
				uriBuilder.setParameter((String) pn, (String) ((Object[]) parameterMap.get(pn))[0]);
			}
			HttpGet getSession = new HttpGet(uriBuilder.build());
			setGetHeader(getSession);
			response = httpclient.execute(getSession,context);
		} else {
			HttpPost post = new HttpPost(url);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (Object pn : parameterMap.keySet()) {
				// 提交两个参数及值
				nvps.add(new BasicNameValuePair((String) pn,
						(String) ((Object[]) parameterMap.get(pn))[0]));
			}
			// 设置表单提交编码为UTF-8
			post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.DEF_CONTENT_CHARSET));
			setPostHeader(post);
			response = httpclient.execute(post,context);
		}
		option.hadler(response);
//		return new HttpThread(response,option);
	}
	
	private void setGetHeader(HttpRequestBase session) {
		session.setHeader("Accept:",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		session.setHeader("Accept-Encoding", "gzip,deflate,sdch");
		session.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		session.setHeader("Cache-Control", "max-age=0");
		session.setHeader("Connection", "keep-alive");
		session.setHeader("Content-Type", "application/x-www-form-urlencoded");
		session.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
	}

	private void setPostHeader(HttpPost post){
		post.setHeader("Accept:",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		post.setHeader("Accept-Encoding", "gzip,deflate,sdch");
		post.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		post.setHeader("Cache-Control", "max-age=0");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		post.setHeader("Host", "home.cnaf.com");
		post.setHeader("Origin", "http://home.cnaf.com");
		post.setHeader("Referer",
				"http://home.cnaf.com/oa/_layouts/IMPFrameWebUI/index.aspx");
		post.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
	}

}
