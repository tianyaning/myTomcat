package com.study.tomcat.v3.util;

import com.study.tomcat.v3.model.WebXml;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;

public class ProjectUtil {
    //加载所有项目
    public static Map<String, WebXml> load() throws Exception {
        Map<String, WebXml> configInfo = new HashMap<String, WebXml>();

        //自己tomcat的项目部署路径
        String webapps = "/Users/tianyaning/myTomcat/webapps";

        // 0,war包自动解压

        //1,webapps子文件夹算是一个项目
        File[] projects = new File(webapps).listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        for (File projectFile: projects) {
            //2, 查找每个项目中到底有哪些servlet，读取web.xml文件
            WebXml webXml = new WebXmlUtil().loadXml(projectFile.getPath());

            //3, 加载，把部署项目代码的class文件加载到jvm中
            webXml.loadServlet();
            configInfo.put(projectFile.getName(), webXml);
        }

        return configInfo;
    }

//    public static void main(String[] args) {
//        WebXml webXml = new WebXmlUtil().loadXml("/Users/xiongjiyuan/tianyaning/own_project/myTomcat/tomcatstudy/src/main/java/com/study/tomcat/v3/test/test.xml");
//        System.out.println(webXml.toString());
//    }
}


