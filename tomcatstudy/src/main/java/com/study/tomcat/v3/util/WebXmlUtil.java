package com.study.tomcat.v3.util;

import java.io.*;
import java.util.*;

import com.study.tomcat.v3.model.WebXml;
import org.dom4j.*;
import org.dom4j.io.*;

public class WebXmlUtil {
    public static final String WEBINFPATH = "/WEB-INF/web.xml";
    public WebXml loadXml(String path) {
        WebXml webXml = new WebXml();
        Map<String, Object> servlets = new HashMap<String, Object>();
        Map<String, String> servletMapping = new HashMap<String, String>();

        try {
            File f = new File(path + WEBINFPATH);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(f);
            Element root = doc.getRootElement();
            Element foo;
            for (Iterator i = root.elementIterator("servlet"); i.hasNext();) {
                foo = (Element) i.next();
                servlets.put(foo.elementText("servlet-name"), foo.elementText("servlet-class"));
            }
            for (Iterator i = root.elementIterator("servlet-mapping"); i.hasNext();) {
                foo = (Element) i.next();
                servletMapping.put(foo.elementText("servlet-name"), foo.elementText("url-pattern"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        webXml.setServletMapping(servletMapping);
        webXml.setServlets(servlets);
        webXml.setProjectPath(path);
        return webXml;
    }
}
