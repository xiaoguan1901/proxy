package com.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.routing.ALink;
import com.routing.Form;
import com.routing.Route;

public class AlterListParser extends Parser {

    protected String path;
    
    public AlterListParser(String path) {
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
            xstream.alias("route", Route.class);
            xstream.alias("key", String.class);
            xstream.alias("form", Form.class);
            xstream.alias("alink", ALink.class);
            xstream.useAttributeFor(Route.class, "id");
            xstream.useAttributeFor(Form.class, "id");
            xstream.useAttributeFor(ALink.class, "id");
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
