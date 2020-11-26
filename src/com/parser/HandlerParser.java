package com.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HandlerParser extends Parser {

    protected String path;
    
    public HandlerParser(String path) {
    	this.path = path;
    }

	@Override
	public Object xmlToOjbect() {
		 InputStream in = null;
		 byte[] buffer = null;
       try {
           File file = new File(path);
           in = new FileInputStream(file);
           buffer = new byte[Long.valueOf(file.length()).intValue()];
           in.read(buffer, 0, buffer.length);
           return xstream.fromXML(new String(buffer));
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
       	try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
       }
       return null;
	}

}
