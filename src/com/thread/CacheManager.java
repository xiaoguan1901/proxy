package com.thread;

import java.util.Date;

import common.Contracts;


/**
 * 
 * @author zheng
 *
 */
public class CacheManager implements Runnable {
	
	private final Long overtime;
	private long timeStart;
	
	public CacheManager(long overtime,long timeStart) {
		this.overtime = overtime;
		this.timeStart = timeStart;
	}
	
	public void run() {
		
		while(true) {
			try {
				long difftime = timeStart - new Date().getTime();//��ǰʱ��;
				Thread.sleep(10000);
				if(difftime > overtime) {
					Contracts.uriMap.clear();
					timeStart = new Date().getTime();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
