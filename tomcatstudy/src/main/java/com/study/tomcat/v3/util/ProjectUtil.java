package com.study.tomcat.v3.util;

import com.study.tomcat.v3.model.WebXml;

import java.io.File;
import java.io.FileFilter;

public class ProjectUtil {
    public void load() {
        //TODO 路径需要自己补充
        String webapps = "";
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
        }
    }

//    public static void main(String[] args) {
//        WebXml webXml = new WebXmlUtil().loadXml("/Users/xiongjiyuan/tianyaning/own_project/myTomcat/tomcatstudy/src/main/java/com/study/tomcat/v3/test/test.xml");
//        System.out.println(webXml.toString());
//    }
}


