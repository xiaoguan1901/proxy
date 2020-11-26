package com.proxy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.jsoup.helper.StringUtil;

import com.entiry.HttpEntiryOption;
import com.session.SessionManager;
import com.thread.CacheManager;

import common.Contracts;

@SuppressWarnings("serial")
public class Proxy extends HttpServlet {
	//private SessionManager session;

	@Override
	public void init() {
		//session = new SessionManager(Contracts.address);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		execute(req, resp, "get");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		execute(req, resp, "post");
	}

	private void execute(HttpServletRequest req, HttpServletResponse resp,
			String type) throws ServletException, IOException {
		String reqURI = req.getRequestURI().trim();
		CloseableHttpResponse response = null;
		HttpEntiryOption option = new HttpEntiryOption(response, reqURI,resp);
		try {
			String url = (String)Contracts.uriMap.get(reqURI);
			SessionManager session = null;
			if(StringUtil.isBlank(url)) {
			   session = new SessionManager(Contracts.address);
			} else {
			   session = new SessionManager(url);
			}
			session.sendSession(type, option, reqURI, req.getParameterMap());
			option.display();
			new CacheManager(3600000L,new Date().getTime());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
