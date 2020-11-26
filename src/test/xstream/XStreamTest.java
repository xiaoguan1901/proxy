package test.xstream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.routing.ALink;
import com.routing.Form;
import com.routing.Route;
import com.thoughtworks.xstream.XStream;
 
/**
 * <b>function:</b>Java对象和XML字符串的相互转换
 * jar-lib-version: xstream-1.3.1
 * @author hoojo
 * @createDate Nov 27, 2010 12:15:15 PM
 * @file XStreamTest.java
 * @package com.hoo.test
 * @project WebHttpUtils
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class XStreamTest {
    
    private XStream xstream = null;
    private ObjectOutputStream  out = null;
    private ObjectInputStream in = null;
    
    public static void main(String[] args) {
    	XStreamTest test = new XStreamTest();
    	test.init();
//    	test.writeMap2XMLTwo();
    	test.readXml2Object2();
//    	test.writeMap2XML();
//    	test.readXml2Object();
	}
    /**
     * <b>function:</b>初始化资源准备
     * @author hoojo
     * @createDate Nov 27, 2010 12:16:28 PM
     */
    public void init() {
        try {
            xstream = new XStream();
//            xstream = new XStream(new DomDriver()); // 需要xpp3 jar
        } catch (Exception e) {
            e.printStackTrace();
        }
//        bean = new Route();
    }

    
    /**
     * <b>function:</b>Java Map集合转XML
     * @author hoojo
     * @createDate Nov 27, 2010 1:13:26 PM
     */
    public void writeMap2XML() {
        try {
            Route bean = null;
            failRed("---------Map --> XML---------");
            Map<String, Route> map = new HashMap<String, Route>();
            bean = new Route();
            bean.setId(1);
            bean.setUri("/oa");
            List<Form> formlist = new ArrayList<Form>();
            //修改元素名称
            //form
        	Form form = new Form();
            form.setId("1");
            form.setName("myform");
            form.setType("post");
            form.setAction("/form");
            formlist.add(form);
            
            form = new Form();
            form.setId("2");
            form.setName("myform2");
            form.setType("post");
            form.setAction("/form2");
            formlist.add(form);
            bean.setFormList(formlist);
            List<ALink> alinklist = new ArrayList<ALink>();
            //alink
            ALink a = new ALink();
            a.setId("1");
            a.setName("A1");
            a.setHref("http://localhost");
            a.setTarget("_self");
            
            alinklist.add(a);
            a = new ALink();
            a.setId("2");
            a.setName("A2");
            a.setHref("http://localhost");
            a.setTarget("_self");
            alinklist.add(a);
            bean.setaLinkList(alinklist);
            bean.setClassName("com.ss");
            map.put("No.1", bean);//put
            
            xstream.alias("route", Route.class);
            xstream.alias("key", String.class);
            xstream.alias("form", Form.class);
            xstream.alias("alink", ALink.class);
            xstream.useAttributeFor(Route.class, "id");
            xstream.useAttributeFor(Form.class, "id");
            xstream.useAttributeFor(ALink.class, "id");
            fail(xstream.toXML(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * <b>function:</b>Java Map集合转XML
     * @author hoojo
     * @createDate Nov 27, 2010 1:13:26 PM
     */
    public void writeMap2XMLTwo() {
        try {
             Map<String, String> map = new HashMap<String, String>();
             map.put("/uri1", "/oa1");
             map.put("/uri2", "/oa2");
//             xstream.alias("key", String.class);
             fail(xstream.toXML(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readXml2Object2() {
        try {
            failRed("-----------Xml >>> Bean--------------");
            
            File file = new File("E:/workspace/Jumping/src/resource/proxy.xml");
            InputStream in = new FileInputStream(file);
            byte[] buffer = new byte[Long.valueOf(file.length()).intValue()];
            in.read(buffer, 0, buffer.length);
            Map<String, Route> map = (Map<String, Route>) xstream.fromXML(new String(buffer));
            int i = 0 ;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * <b>function:</b>释放对象资源
     * @author hoojo
     * @createDate Nov 27, 2010 12:16:38 PM
     */
    public void destory() {
        xstream = null;
        try {
            if (out != null) {
                out.flush();
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.gc();
    }
    
    public final void fail(String string) {
        System.out.println(string);
    }
    
    public final void failRed(String string) {
        System.err.println(string);
    }
    /**
     * <b>function:</b>将XML字符串转换成Java对象
     * @author hoojo
     * @createDate Nov 27, 2010 2:39:06 PM
     */
    public void readXml2Object() {
        try {
            failRed("-----------Xml >>> Bean--------------");
            
            File file = new File("E:/workspace/Jumping/src/resource/proxylist.xml");
            InputStream in = new FileInputStream(file);
            byte[] buffer = new byte[Long.valueOf(file.length()).intValue()];
            in.read(buffer, 0, buffer.length);
            
            xstream.alias("route", Route.class);
            xstream.alias("key", String.class);
            xstream.alias("form", Form.class);
            xstream.alias("alink", ALink.class);
            xstream.useAttributeFor(Route.class, "id");
            xstream.useAttributeFor(Form.class, "id");
            xstream.useAttributeFor(ALink.class, "id");
            Map<String, Route> map = (Map<String, Route>) xstream.fromXML(new String(buffer));
            int i = 0 ;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}