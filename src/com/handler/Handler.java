package com.handler;


public abstract class Handler {

	protected Handler nextHandler;
	
	public abstract String handle(String htmlText,String className);
	
}
