package com.parser;

import com.thoughtworks.xstream.XStream;

public abstract class Parser {

    protected XStream xstream = null;

    public Parser() {
    	init();
    }
    /**
     * <b>function:</b>��ʼ����Դ׼��
     * @author hoojo
     * @createDate Nov 27, 2010 12:16:28 PM
     */
    public void init() {
        try {
            xstream = new XStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public abstract Object xmlToOjbect();
}
