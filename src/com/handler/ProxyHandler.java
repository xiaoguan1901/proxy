package com.handler;

import java.util.concurrent.ConcurrentHashMap;

import com.modify.Modifiy;

//import resourcencodeUtil.IdentifyCode;

public class ProxyHandler extends Handler {
	
	@Override
	public String handle(String htmlText,String className) {
		try {
			Class<Modifiy> object = (Class<Modifiy>) Class.forName(className);
			Modifiy modifiy = object.newInstance();
			return modifiy.changehtml(htmlText);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return htmlText;
	}
	
}
